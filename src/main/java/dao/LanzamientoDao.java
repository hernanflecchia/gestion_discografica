package dao;

import config.Conexion;
import enums.Genero;
import interfaces.Dao;
import modelo.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LanzamientoDao implements Dao<Lanzamiento> {
    private static final String SQL_BUSCAR_CANCIONES =
            "SELECT titulo, duracion_seg FROM cancion WHERE lanzamiento_id = ? ORDER BY id";
    private static final String SQL_SELECT = "SELECT id, artista_id, titulo, fecha, precio, " +
            "unidades_vendidas, tipo, genero FROM lanzamiento";
    private static final String SQL_BUSCAR_LANZAMIENTOS = SQL_SELECT + " ORDER BY titulo";
    private static final String SQL_BUSCAR_LANZAMIENTO_POR_ID = SQL_SELECT + " WHERE id = ?";
    private static final String SQL_INSERTAR_LANZAMIENTO =
            "INSERT INTO lanzamiento(artista_id, titulo, fecha, precio, unidades_vendidas, tipo, genero) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_INSERTAR_CANCION =
            "INSERT INTO cancion (lanzamiento_id, titulo, duracion_seg) VALUES (?, ?, ?)";
    private static final String SQL_ELIMINAR = "DELETE FROM lanzamiento WHERE id = ?";
    private static final String SQL_MODIFICAR =
            "UPDATE lanzamiento SET titulo = ?, fecha = ?, precio = ?, " +
                    "unidades_vendidas = ?, genero = ? WHERE id = ?";
    private static final String SQL_BORRAR_CANCIONES =
            "DELETE FROM cancion WHERE lanzamiento_id = ?";

    private final ArtistaDao artistaDao;

    public LanzamientoDao(ArtistaDao artistaDao) {
        this.artistaDao = artistaDao;
    }

    @Override
    public void agregar(Lanzamiento entidad) throws SQLException {
        try (Connection conn = Conexion.obtenerConexion()) {
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement ps = conn.prepareStatement(SQL_INSERTAR_LANZAMIENTO,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, entidad.getArtista().getId());
                    ps.setString(2, entidad.getTitulo());
                    ps.setDate(3, Date.valueOf(entidad.getFecha()));
                    ps.setDouble(4, entidad.getPrecio());
                    ps.setInt(5, entidad.getUnidadesVendidas());
                    ps.setString(6, entidad.getTipo().name());
                    ps.setString(7, entidad.getGenero().name());
                    ps.executeUpdate();
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            entidad.setId(rs.getInt(1));
                        }
                    }
                    insertarCanciones(conn, entidad);
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_ELIMINAR)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() == 0) {
                // Acá debe ir una excepción propia que capture esto porque no es una SQLException
                throw new SQLException("No existe el lanzamiento con el id: " + id);
            }
        }
    }

    @Override
    public void modificar(Lanzamiento entidad) throws SQLException {
        try (Connection conn = Conexion.obtenerConexion()) {
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement ps = conn.prepareStatement(SQL_MODIFICAR)) {
                    ps.setString(1, entidad.getTitulo());
                    ps.setDate(2, Date.valueOf(entidad.getFecha()));
                    ps.setDouble(3, entidad.getPrecio());
                    ps.setInt(4, entidad.getUnidadesVendidas());
                    ps.setString(5, entidad.getGenero().name());
                    ps.setInt(6, entidad.getId());
                    if (ps.executeUpdate() == 0) {
                        // Acá debe ir una excepción propia que capture esto porque no es una SQLException
                        throw new SQLException("No existe el lanzamiento con el id: " + entidad.getId());
                    }
                }
                try (PreparedStatement ps = conn.prepareStatement(SQL_BORRAR_CANCIONES)) {
                    ps.setInt(1, entidad.getId());
                    ps.executeUpdate();
                }
                insertarCanciones(conn, entidad);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    @Override
    public Lanzamiento buscar(int id) throws SQLException {
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_BUSCAR_LANZAMIENTO_POR_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return crearLanzamiento(rs, conn, artistaDao.buscar(rs.getInt("artista_id")));
                }
            }
        }
        // Acá debe ir una excepción propia que capture esto porque no es una SQLException
        throw new SQLException("No existe el lanzamiento con el id: " + id);
    }

    @Override
    public ArrayList<Lanzamiento> buscarTodos() throws SQLException {
        Map<Integer, Artista> artistas = new HashMap<>();
        for (Artista a : artistaDao.buscarTodos()) {
            artistas.put(a.getId(), a);
        }

        ArrayList<Lanzamiento> lista = new ArrayList<>();
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_BUSCAR_LANZAMIENTOS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Artista artista = artistas.get(rs.getInt("artista_id"));
                lista.add(crearLanzamiento(rs, conn, artista));
            }
        }
        return lista;
    }

    private Lanzamiento crearLanzamiento(ResultSet rs, Connection conn, Artista artista) throws SQLException {
        int id = rs.getInt("id");
        String titulo = rs.getString("titulo");
        LocalDate fecha = rs.getDate("fecha").toLocalDate();
        double precio = rs.getDouble("precio");
        Genero genero = Genero.valueOf(rs.getString("genero"));
        List<Cancion> canciones = buscarCanciones(conn, id);

        Lanzamiento l;
        if (rs.getString("tipo").equals("SINGLE")) {
            l = new Single(id, titulo, fecha, precio, genero, artista, canciones.getFirst());
        } else {
            Album album = new Album(id, titulo, fecha, precio, genero, artista);
            for (Cancion c : canciones) {
                album.agregarCancion(c);
            }
            l = album;
        }
        l.setUnidadesVendidas(rs.getInt("unidades_vendidas"));
        return l;
    }

    private List<Cancion> buscarCanciones(Connection conn, int lanzamientoId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(SQL_BUSCAR_CANCIONES)) {
            ps.setInt(1, lanzamientoId);
            ResultSet rs = ps.executeQuery();
            List<Cancion> lista = new ArrayList<>();
            while (rs.next()) {
                Cancion cancion = new Cancion(rs.getString("titulo"), rs.getInt("duracion_seg"));
                lista.add(cancion);
            }
            return lista;
        }
    }

    private void insertarCanciones(Connection conn, Lanzamiento l) throws SQLException {
        List<Cancion> canciones = new ArrayList<>();
        if (l instanceof Single s) {
            canciones.add(s.getCancion());
        } else if (l instanceof Album a) {
            canciones.addAll(a.getCanciones());
        }

        try (PreparedStatement ps = conn.prepareStatement(SQL_INSERTAR_CANCION)) {
            for (Cancion c : canciones) {
                ps.setInt(1, l.getId());
                ps.setString(2, c.getTitulo());
                ps.setInt(3, c.getDuracionSeg());
                ps.executeUpdate();
            }
        }
    }
}

package dao;

import config.Conexion;
import enums.EstadoArtista;
import enums.TipoArtista;
import interfaces.Dao;
import modelo.Artista;
import modelo.Banda;
import modelo.Solista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArtistaDao implements Dao<Artista> {
    private static final String SQL_BUSCAR_ARTISTAS =
            "SELECT id, tipo, nombre, pais, estado, nombre_real FROM artista ORDER BY nombre";
    private static final String SQL_BUSCAR_INTEGRANTES =
            "SELECT nombre FROM banda_integrante WHERE artista_id = ?";
    private static final String SQL_BUSCAR_ARTISTA_POR_ID =
            SQL_BUSCAR_ARTISTAS + " WHERE id = ?";
    private static final String SQL_INSERTAR_ARTISTA =
            "INSERT INTO artista(tipo, nombre, pais, estado, nombre_real) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_BAJA =
            "UPDATE artista SET estado = 'INACTIVO' WHERE id = ?";
    private static final String SQL_MODIFICAR =
            "UPDATE artista SET nombre = ?, pais = ?, estado = ?, nombre_real = ? WHERE id = ?";
    private static final String SQL_BORRAR_INTEGRANTES =
            "DELETE FROM banda_integrante WHERE artista_id = ?";
    private static final String SQL_INSERTAR_INTEGRANTE =
            "INSERT INTO banda_integrante (artista_id, nombre) VALUES (?, ?)";

    @Override
    public void agregar(Artista entidad) throws SQLException {
        try (Connection conn = Conexion.obtenerConexion()) {
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement ps = conn.prepareStatement(SQL_INSERTAR_ARTISTA,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, entidad.getTipo().name());
                    ps.setString(2, entidad.getNombre());
                    ps.setString(3, entidad.getPais());
                    ps.setString(4, entidad.getEstado().name());
                    if (entidad instanceof Solista) {
                        ps.setString(5, ((Solista) entidad).getNombreReal());
                    } else {
                        ps.setNull(5, java.sql.Types.VARCHAR);
                    }
                    ps.executeUpdate();
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            entidad.asignarId(rs.getInt(1));
                        }
                    }
                }
                if (entidad instanceof Banda b) {
                    insertarIntegrantes(conn, b);
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
             PreparedStatement ps = conn.prepareStatement(SQL_BAJA)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public void modificar(Artista artista) throws SQLException {
        try (Connection conn = Conexion.obtenerConexion()) {
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement ps = conn.prepareStatement(SQL_MODIFICAR)) {
                    ps.setString(1, artista.getNombre());
                    ps.setString(2, artista.getPais());
                    ps.setString(3, artista.getEstado().name());
                    if (artista instanceof Solista s) {
                        ps.setString(4, s.getNombreReal());
                    } else {
                        ps.setNull(4, java.sql.Types.VARCHAR);
                    }
                    ps.setInt(5, artista.getId());
                    ps.executeUpdate();
                }
                if (artista instanceof Banda b) {
                    try (PreparedStatement ps = conn.prepareStatement(SQL_BORRAR_INTEGRANTES)) {
                        ps.setInt(1, b.getId());
                        ps.executeUpdate();
                    }
                    insertarIntegrantes(conn, b);
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    @Override
    public Artista buscar(int id) throws SQLException {
        try (Connection conn = Conexion.obtenerConexion();
        PreparedStatement ps = conn.prepareStatement(SQL_BUSCAR_ARTISTA_POR_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return crearArtista(rs, conn);
                }
            }
        }
        // Acá debe ir una excepción propia que capture esto porque no es una SQLException
        throw new SQLException("No existe el artista con el id: " + id);
    }

    @Override
    public ArrayList<Artista> buscarTodos() throws SQLException {
        ArrayList<Artista> lista = new ArrayList<>();
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_BUSCAR_ARTISTAS);
             ResultSet rs = ps.executeQuery()
             ) {
            while (rs.next()) {
                lista.add(crearArtista(rs, conn));
            }
        }
        return lista;
    }

    private Artista crearArtista(ResultSet rs, Connection conn) throws SQLException {
        int id = rs.getInt("id");
        String nombre = rs.getString("nombre");
        String pais = rs.getString("pais");
        EstadoArtista estado = EstadoArtista.valueOf(rs.getString("estado"));
        TipoArtista tipo = TipoArtista.valueOf(rs.getString("tipo"));
        if (tipo == TipoArtista.SOLISTA) {
            return new Solista(id, nombre, pais, estado, rs.getString("nombre_real"));
        }
        return new Banda(id, nombre, pais, estado, buscarIntegrantes((Connection) conn, id));
    }

    private List<String> buscarIntegrantes(Connection conn, int artistaId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(SQL_BUSCAR_INTEGRANTES)) {
            ps.setInt(1, artistaId);
            ResultSet rs = ps.executeQuery();
            List<String> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(rs.getString("nombre"));
            }
            return lista;
        }
    }

    private void insertarIntegrantes(Connection conn, Banda b) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(SQL_INSERTAR_INTEGRANTE)) {
            for (String integrante : b.getIntegrantes()) {
                ps.setInt(1, b.getId());
                ps.setString(2, integrante);
                ps.executeUpdate();
            }
        }
    }
}

package dao;

import config.Conexion;
import enums.EstadoContrato;
import enums.Genero;
import interfaces.Dao;
import modelo.Artista;
import modelo.Contrato;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ContratoDao implements Dao<Contrato> {
    private static final String SQL_SELECT = "SELECT id, artista_id, fecha_inicio, fecha_fin, porcentaje_regalias, " +
            "estado FROM contrato";
    private static final String SQL_BUSCAR_CONTRATOS = SQL_SELECT + " ORDER BY fecha_inicio";
    private static final String SQL_BUSCAR_CONTRATO_POR_ID = SQL_SELECT + " WHERE id = ?";
    private static final String SQL_INSERTAR_CONTRATO =
            "INSERT INTO contrato(artista_id, fecha_inicio, fecha_fin, porcentaje_regalias, estado) " +
                    "VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_ELIMINAR = "DELETE FROM contrato WHERE id = ?";
    private static final String SQL_MODIFICAR =
            "UPDATE contrato SET fecha_inicio = ?, fecha_fin = ?, porcentaje_regalias = ?, " +
                    "estado = ? WHERE id = ?";

    private final ArtistaDao artistaDao;

    public ContratoDao(ArtistaDao artistaDao) { this.artistaDao = artistaDao; }

    @Override
    public void agregar(Contrato entidad) throws SQLException {
        try (Connection conn = Conexion.obtenerConexion()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(SQL_INSERTAR_CONTRATO,
                    PreparedStatement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, entidad.getArtista().getId());
                ps.setDate(2, Date.valueOf(entidad.getFechaInicio()));
                ps.setDate(3, Date.valueOf(entidad.getFechaFin()));
                ps.setDouble(4, entidad.getPorcentajeRegalias());
                ps.setString(5, entidad.getEstadoContrato().name());
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        entidad.setId(rs.getInt(1));
                    }
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
                throw new SQLException("No existe el contrato con el id: " + id);
            }
        }
    }

    @Override
    public void modificar(Contrato entidad) throws SQLException {
        try (Connection conn = Conexion.obtenerConexion()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(SQL_MODIFICAR)) {
                ps.setDate(1, Date.valueOf(entidad.getFechaInicio()));
                ps.setDate(2, Date.valueOf(entidad.getFechaFin()));
                ps.setDouble(3, entidad.getPorcentajeRegalias());
                ps.setString(4, entidad.getEstadoContrato().name());
                ps.setInt(5, entidad.getId());
                if (ps.executeUpdate() == 0) {
                    // Acá debe ir una excepción propia que capture esto porque no es una SQLException
                    throw new SQLException("No existe el contrato con el id: " + entidad.getId());
                }
                conn.commit();
            }  catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }

    }

    @Override
    public Contrato buscar(int id) throws SQLException {
        try (Connection conn = Conexion.obtenerConexion();
            PreparedStatement ps = conn.prepareStatement(SQL_BUSCAR_CONTRATO_POR_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return crearContrato(rs, conn, artistaDao.buscar(rs.getInt("artista_id")));
                }
            }
        }
        // Acá debe ir una excepción propia que capture esto porque no es una SQLException
        throw new SQLException("No existe el contrato con el id: " + id);
    }

    @Override
    public ArrayList<Contrato> buscarTodos() throws SQLException {
        Map<Integer, Artista> artistas = new HashMap<>();
        for (Artista a : artistaDao.buscarTodos()) {
            artistas.put(a.getId(), a);
        }

        ArrayList<Contrato> lista = new ArrayList<>();
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_BUSCAR_CONTRATOS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Artista artista = artistas.get(rs.getInt("artista_id"));
                lista.add(crearContrato(rs, conn, artista));
            }
        }
        return lista;
    }

    private Contrato crearContrato(ResultSet rs, Connection conn, Artista artista) throws SQLException {
        int id = rs.getInt("id");
        LocalDate fechaInicio = rs.getDate("fecha_inicio").toLocalDate();
        LocalDate fechaFin = rs.getDate("fecha_fin").toLocalDate();
        double porcentajeRegalias = rs.getDouble("porcentaje_regalias");
        EstadoContrato estadoContrato = EstadoContrato.valueOf(rs.getString("estado"));

        return new Contrato(fechaInicio, fechaFin, porcentajeRegalias, estadoContrato, artista);
    }
}

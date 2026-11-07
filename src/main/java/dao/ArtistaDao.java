package dao;

import config.Conexion;
import enums.EstadoArtista;
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
    private static final String SQL_BUSCAR_ARTISTAS = "SELECT id, tipo, nombre, pais, estado, nombre_real FROM artista";
    private static final String SQL_BUSCAR_INTEGRANTES = "SELECT nombre FROM banda_integrante WHERE artista_id = ?";

    @Override
    public void agregar(Artista entidad) throws SQLException {

    }

    @Override
    public void eliminar(int id) throws SQLException {

    }

    @Override
    public void modificar(Artista entidad) throws SQLException {

    }

    @Override
    public Artista buscar(int id) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<Artista> buscarTodos() {
        ArrayList<Artista> lista = new ArrayList<>();
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_BUSCAR_ARTISTAS);
             ResultSet rs = ps.executeQuery()
             ) {
            while (rs.next()) {
                lista.add(crearArtista(rs, conn));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    private Artista crearArtista(ResultSet rs, Connection conn) throws SQLException {
        int id = rs.getInt("id");
        String nombre = rs.getString("nombre");
        String pais = rs.getString("pais");
        EstadoArtista estado = EstadoArtista.valueOf(rs.getString("estado"));
        if (rs.getString("tipo").equals("SOLISTA")) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

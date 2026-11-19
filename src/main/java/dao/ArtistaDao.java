package dao;

import config.Conexion;
import enums.EstadoArtista;
import enums.TipoArtista;
import excepciones.EntidadNoEncontradaException;
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

/**
 * Guarda y lee artistas de la base de datos.
 * Solistas y bandas van en la misma tabla "artista" (la columna "tipo" dice cuál es).
 * Los integrantes de las bandas van en la tabla "banda_integrante".
 */
public class ArtistaDao implements Dao<Artista> {
    // Consultas SQL que usa esta clase
    private static final String SQL_SELECT =
            "SELECT id, tipo, nombre, pais, estado, nombre_real FROM artista";
    private static final String SQL_BUSCAR_ARTISTAS =
            SQL_SELECT + " ORDER BY nombre";
    private static final String SQL_BUSCAR_INTEGRANTES =
            "SELECT nombre FROM banda_integrante WHERE artista_id = ?";
    private static final String SQL_BUSCAR_ARTISTA_POR_ID =
            SQL_SELECT + " WHERE id = ?";
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

    // Guarda un artista nuevo y le asigna el id que generó la base.
    // Si es una banda, también guarda sus integrantes.
    // Todo va en una transacción: si algo falla, no queda nada guardado a medias.
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
                    // solo los solistas tienen nombre real
                    if (entidad instanceof Solista) {
                        ps.setString(5, ((Solista) entidad).getNombreReal());
                    } else {
                        ps.setNull(5, java.sql.Types.VARCHAR);
                    }
                    ps.executeUpdate();
                    // recupera el id generado por la base
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
            } catch (SQLException | RuntimeException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    // Baja lógica: no borra el artista, lo pasa a INACTIVO.
    @Override
    public void eliminar(int id) throws SQLException {
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_BAJA)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() == 0) {
                throw new EntidadNoEncontradaException("artista", id);
            }
        }
    }

    // Actualiza los datos del artista. Si es una banda, borra los integrantes
    // y los vuelve a cargar con la lista actual.
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
                    if (ps.executeUpdate() == 0) {
                        throw new EntidadNoEncontradaException("artista", artista.getId());
                    }
                }
                if (artista instanceof Banda b) {
                    try (PreparedStatement ps = conn.prepareStatement(SQL_BORRAR_INTEGRANTES)) {
                        ps.setInt(1, b.getId());
                        ps.executeUpdate();
                    }
                    insertarIntegrantes(conn, b);
                }
                conn.commit();
            } catch (SQLException | RuntimeException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    // Busca un artista por id; avisa si no existe.
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
        throw new EntidadNoEncontradaException("artista", id);
    }

    // Devuelve todos los artistas ordenados por nombre.
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

    // Arma un Solista o una Banda a partir de una fila de la tabla, según la columna "tipo".
    private Artista crearArtista(ResultSet rs, Connection conn) throws SQLException {
        int id = rs.getInt("id");
        String nombre = rs.getString("nombre");
        String pais = rs.getString("pais");
        EstadoArtista estado = EstadoArtista.valueOf(rs.getString("estado"));
        TipoArtista tipo = TipoArtista.valueOf(rs.getString("tipo"));
        if (tipo == TipoArtista.SOLISTA) {
            return new Solista(id, nombre, pais, estado, rs.getString("nombre_real"));
        }
        return new Banda(id, nombre, pais, estado, buscarIntegrantes(conn, id));
    }

    // Trae los nombres de los integrantes de una banda.
    private List<String> buscarIntegrantes(Connection conn, int artistaId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(SQL_BUSCAR_INTEGRANTES)) {
            ps.setInt(1, artistaId);
            try (ResultSet rs = ps.executeQuery()) {
                List<String> lista = new ArrayList<>();
                while (rs.next()) {
                    lista.add(rs.getString("nombre"));
                }
                return lista;
            }
        }
    }

    // Guarda los integrantes de una banda.
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

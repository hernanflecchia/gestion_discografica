package gestores;

import interfaces.Dao;
import modelo.Artista;
import modelo.Lanzamiento;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestor de lanzamientos. La validación la hace cada lanzamiento
 * (Single o Album) con sus propias reglas.
 */
public class GestorLanzamiento extends Gestor<Lanzamiento> {
    public GestorLanzamiento(Dao<Lanzamiento> dao) {
        super(dao);
    }

    // Le pide al lanzamiento que se valide (polimorfismo: Single y Album validan distinto).
    @Override
    protected void validar(Lanzamiento entidad) throws Exception {
        entidad.validar();
    }

    // Devuelve solo los lanzamientos de ese artista.
    public List<Lanzamiento> listarPorArtista(Artista artista) throws SQLException {
        List<Lanzamiento> lista = new ArrayList<>();
        for (Lanzamiento lanzamiento: dao.buscarTodos() ) {
            if (artista.getId() == lanzamiento.getArtista().getId()) {
                lista.add(lanzamiento);
            }
        }
        return lista;
    }
}

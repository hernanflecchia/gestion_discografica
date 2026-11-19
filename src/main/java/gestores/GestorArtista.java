package gestores;

import excepciones.ArtistaDuplicadoException;
import interfaces.Dao;
import modelo.Artista;

import java.sql.SQLException;

/**
 * Gestor de artistas. Agrega la regla de que no puede haber
 * dos artistas con el mismo nombre.
 */
public class GestorArtista extends Gestor<Artista> {
    public GestorArtista(Dao<Artista> dao) {
        super(dao);
    }

    // Recorre los artistas guardados y avisa si el nombre ya está usado por otro.
    // Compara sin importar mayúsculas y minúsculas.
    @Override
    protected void validar(Artista entidad) throws Exception {
        for (Artista artista: dao.buscarTodos()) {
            if (artista.getId() != entidad.getId() &&
                artista.getNombre().equalsIgnoreCase(entidad.getNombre())) {
                throw new ArtistaDuplicadoException("Ya existe un artista llamado " + entidad.getNombre());
            }
        }
    }
}

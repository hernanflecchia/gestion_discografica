package gestores;

import excepciones.ArtistaDuplicadoException;
import interfaces.Dao;
import modelo.Artista;

import java.sql.SQLException;

public class GestorArtista extends Gestor<Artista> {
    public GestorArtista(Dao<Artista> dao) {
        super(dao);
    }

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

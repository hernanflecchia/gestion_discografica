package gestores;

import interfaces.Repositorio;
import modelo.Album;

import java.sql.SQLException;

public class GestorAlbum extends Gestor<Album> {
    protected GestorAlbum(Repositorio<Album> repositorio) {
        super(repositorio);
    }

    @Override
    protected void validar(Album entidad) throws SQLException {

    }
}

package gestores;

import interfaces.Dao;
import modelo.Album;

import java.sql.SQLException;

public class GestorAlbum extends Gestor<Album> {
    protected GestorAlbum(Dao<Album> repositorio) {
        super(repositorio);
    }

    @Override
    protected void validar(Album entidad) throws SQLException {

    }
}

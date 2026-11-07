package gestores;

import interfaces.Dao;
import modelo.Artista;

import java.sql.SQLException;

public class GestorArtista extends Gestor<Artista> {
    public GestorArtista(Dao<Artista> repositorio) {
        super(repositorio);
    }

    @Override
    protected void validar(Artista entidad) throws SQLException {

    }
}

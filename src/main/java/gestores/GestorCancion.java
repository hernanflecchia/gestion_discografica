package gestores;

import interfaces.Dao;
import modelo.Cancion;

import java.sql.SQLException;

public class GestorCancion extends Gestor<Cancion> {
    protected GestorCancion(Dao<Cancion> repositorio) {
        super(repositorio);
    }

    @Override
    protected void validar(Cancion entidad) throws SQLException {

    }
}

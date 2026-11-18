package gestores;

import interfaces.Dao;
import modelo.Lanzamiento;

import java.sql.SQLException;

public class GestorLanzamiento extends Gestor<Lanzamiento> {
    public GestorLanzamiento(Dao<Lanzamiento> dao) {
        super(dao);
    }

    @Override
    protected void validar(Lanzamiento entidad) throws SQLException {

    }
}

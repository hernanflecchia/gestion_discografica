package gestores;

import interfaces.Dao;
import modelo.Single;

import java.sql.SQLException;

public class GestorSingle extends Gestor<Single> {
    protected GestorSingle(Dao<Single> repositorio) {
        super(repositorio);
    }

    @Override
    protected void validar(Single entidad) throws SQLException {

    }
}

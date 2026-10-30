package gestores;

import interfaces.Dao;
import modelo.Banda;

import java.sql.SQLException;

public class GestorBanda extends Gestor<Banda> {
    protected GestorBanda(Dao<Banda> repositorio) {
        super(repositorio);
    }

    @Override
    protected void validar(Banda entidad) throws SQLException {

    }
}

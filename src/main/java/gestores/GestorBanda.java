package gestores;

import interfaces.Repositorio;
import modelo.Banda;

import java.sql.SQLException;

public class GestorBanda extends Gestor<Banda> {
    protected GestorBanda(Repositorio<Banda> repositorio) {
        super(repositorio);
    }

    @Override
    protected void validar(Banda entidad) throws SQLException {

    }
}

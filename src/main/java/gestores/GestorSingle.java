package gestores;

import interfaces.Repositorio;
import modelo.Single;

import java.sql.SQLException;

public class GestorSingle extends Gestor<Single> {
    protected GestorSingle(Repositorio<Single> repositorio) {
        super(repositorio);
    }

    @Override
    protected void validar(Single entidad) throws SQLException {

    }
}

package gestores;

import interfaces.Dao;
import modelo.Contrato;

import java.sql.SQLException;

public class GestorContrato extends Gestor<Contrato> {
    protected GestorContrato(Dao<Contrato> repositorio) {
        super(repositorio);
    }

    @Override
    protected void validar(Contrato entidad) throws SQLException {

    }
}

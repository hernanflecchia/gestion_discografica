package gestores;

import interfaces.Repositorio;
import modelo.Contrato;

import java.sql.SQLException;

public class GestorContrato extends Gestor<Contrato> {
    protected GestorContrato(Repositorio<Contrato> repositorio) {
        super(repositorio);
    }

    @Override
    protected void validar(Contrato entidad) throws SQLException {

    }
}

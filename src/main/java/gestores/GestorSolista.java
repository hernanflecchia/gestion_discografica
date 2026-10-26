package gestores;

import interfaces.Identificable;
import interfaces.Repositorio;
import modelo.Solista;

import java.sql.SQLException;

public class GestorSolista extends Gestor<Solista> {


    protected GestorSolista(Repositorio<Solista> repositorio) {
        super(repositorio);
    }

    @Override
    protected void validar(Solista entidad) throws SQLException {

    }
}

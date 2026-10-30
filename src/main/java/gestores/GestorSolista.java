package gestores;

import interfaces.Dao;
import modelo.Solista;

import java.sql.SQLException;

public class GestorSolista extends Gestor<Solista> {


    protected GestorSolista(Dao<Solista> repositorio) {
        super(repositorio);
    }

    @Override
    protected void validar(Solista entidad) throws SQLException {

    }
}

package gestores;

import interfaces.Dao;
import modelo.Lanzamiento;

import java.sql.SQLException;

public class GestorLanzamiento extends Gestor<Lanzamiento> {
    protected GestorLanzamiento(Dao<Lanzamiento> repositorio) {
        super(repositorio);
    }

    @Override
    protected void validar(Lanzamiento entidad) throws SQLException {

    }
}

package gestores;

import interfaces.Repositorio;
import modelo.Cancion;

import java.sql.SQLException;

public class GestorCancion extends Gestor<Cancion> {
    protected GestorCancion(Repositorio<Cancion> repositorio) {
        super(repositorio);
    }

    @Override
    protected void validar(Cancion entidad) throws SQLException {

    }
}

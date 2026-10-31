package gestores;

import interfaces.Dao;
import modelo.Contrato;

import java.sql.SQLException;

public class GestorArtista extends Gestor<Contrato> {
    protected GestorArtista(Dao<Contrato> repositorio) {
        super(repositorio);
    }

    @Override
    protected void validar(Contrato entidad) throws SQLException {

    }
}

package dao;

import interfaces.Dao;
import modelo.Contrato;

import java.sql.SQLException;
import java.util.ArrayList;

public class ContratoDao implements Dao<Contrato> {
    @Override
    public void agregar(Contrato entidad) throws SQLException {

    }

    @Override
    public void eliminar(int id) throws SQLException {

    }

    @Override
    public void modificar(Contrato entidad) throws SQLException {

    }

    @Override
    public Contrato buscar(int id) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<Contrato> buscarTodos() {
        return null;
    }
}

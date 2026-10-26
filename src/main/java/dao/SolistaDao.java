package dao;

import interfaces.Repositorio;
import modelo.Solista;

import java.sql.SQLException;
import java.util.ArrayList;

public class SolistaDao implements Repositorio<Solista> {
    @Override
    public void agregar(Solista entidad) throws SQLException {

    }

    @Override
    public void eliminar(int id) throws SQLException {

    }

    @Override
    public void modificar(Solista entidad) throws SQLException {

    }

    @Override
    public Solista buscar(int id) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<Solista> buscarTodos() {
        return null;
    }
}

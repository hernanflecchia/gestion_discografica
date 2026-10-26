package dao;

import interfaces.Repositorio;
import modelo.Single;

import java.sql.SQLException;
import java.util.ArrayList;

public class SingleDao implements Repositorio<Single> {
    @Override
    public void agregar(Single entidad) throws SQLException {

    }

    @Override
    public void eliminar(int id) throws SQLException {

    }

    @Override
    public void modificar(Single entidad) throws SQLException {

    }

    @Override
    public Single buscar(int id) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<Single> buscarTodos() {
        return null;
    }
}

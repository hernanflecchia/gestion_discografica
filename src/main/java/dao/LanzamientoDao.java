package dao;

import interfaces.Dao;
import modelo.Lanzamiento;

import java.sql.SQLException;
import java.util.ArrayList;

public class LanzamientoDao implements Dao<Lanzamiento> {
    @Override
    public void agregar(Lanzamiento entidad) throws SQLException {

    }

    @Override
    public void eliminar(int id) throws SQLException {

    }

    @Override
    public void modificar(Lanzamiento entidad) throws SQLException {

    }

    @Override
    public Lanzamiento buscar(int id) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<Lanzamiento> buscarTodos() {
        return null;
    }
}

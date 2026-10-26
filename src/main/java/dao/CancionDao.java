package dao;

import interfaces.Repositorio;
import modelo.Cancion;

import java.sql.SQLException;
import java.util.ArrayList;

public class CancionDao implements Repositorio<Cancion> {
    @Override
    public void agregar(Cancion entidad) throws SQLException {

    }

    @Override
    public void eliminar(int id) throws SQLException {

    }

    @Override
    public void modificar(Cancion entidad) throws SQLException {

    }

    @Override
    public Cancion buscar(int id) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<Cancion> buscarTodos() {
        return null;
    }
}

package dao;

import interfaces.Dao;
import modelo.Artista;

import java.sql.SQLException;
import java.util.ArrayList;

public class ArtistaDao implements Dao {
    @Override
    public void agregar(Object entidad) throws SQLException {

    }

    @Override
    public void eliminar(int id) throws SQLException {

    }

    @Override
    public void modificar(Object entidad) throws SQLException {

    }

    @Override
    public Object buscar(int id) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<Artista> buscarTodos() {
        return null;
    }
}

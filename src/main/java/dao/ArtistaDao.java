package dao;

import interfaces.Dao;
import modelo.Artista;

import java.sql.SQLException;
import java.util.ArrayList;

public class ArtistaDao implements Dao<Artista> {

    @Override
    public void agregar(Artista entidad) throws SQLException {

    }

    @Override
    public void eliminar(int id) throws SQLException {

    }

    @Override
    public void modificar(Artista entidad) throws SQLException {

    }

    @Override
    public Artista buscar(int id) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<Artista> buscarTodos() {
        return null;
    }
}

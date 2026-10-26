package dao;

import interfaces.Repositorio;
import modelo.Album;

import java.sql.SQLException;
import java.util.ArrayList;

public class AlbumDao implements Repositorio<Album> {
    @Override
    public void agregar(Album entidad) throws SQLException {

    }

    @Override
    public void eliminar(int id) throws SQLException {

    }

    @Override
    public void modificar(Album entidad) throws SQLException {

    }

    @Override
    public Album buscar(int id) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<Album> buscarTodos() {
        return null;
    }
}

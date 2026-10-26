package dao;

import interfaces.Repositorio;
import modelo.Banda;

import java.sql.SQLException;
import java.util.ArrayList;

public class BandaDao implements Repositorio<Banda> {
    @Override
    public void agregar(Banda entidad) throws SQLException {

    }

    @Override
    public void eliminar(int id) throws SQLException {

    }

    @Override
    public void modificar(Banda entidad) throws SQLException {

    }

    @Override
    public Banda buscar(int id) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<Banda> buscarTodos() {
        return null;
    }
}

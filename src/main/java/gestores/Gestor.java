package gestores;

import interfaces.Identificable;
import interfaces.Repositorio;

import java.sql.SQLException;
import java.util.ArrayList;

public abstract class Gestor<T extends Identificable> {
    protected  final Repositorio<T> repositorio;

    protected Gestor(Repositorio<T> repositorio) {
        this.repositorio = repositorio;
    }

    protected abstract void validar(T entidad) throws SQLException;

    public void alta(T entidad) throws SQLException {
        validar(entidad);
        repositorio.agregar(entidad);
    }

    public void modificar(T entidad) throws SQLException {
        validar(entidad);
        repositorio.modificar(entidad);
    }

    public void baja(int id) throws SQLException {
        repositorio.eliminar(id);
    }

    public T buscar(int id) throws SQLException{
        return repositorio.buscar(id);
    }

    public ArrayList<T> listar(){
        return repositorio.buscarTodos();
    }
}

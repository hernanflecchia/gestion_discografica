package gestores;

import interfaces.Identificable;
import interfaces.Dao;

import java.sql.SQLException;
import java.util.ArrayList;

public abstract class Gestor<T extends Identificable> {
    protected  final Dao<T> dao;

    protected Gestor(Dao<T> dao) {
        this.dao = dao;
    }

    protected abstract void validar(T entidad) throws Exception;

    public void alta(T entidad) throws Exception {
        validar(entidad);
        dao.agregar(entidad);
    }

    public void modificar(T entidad) throws Exception {
        validar(entidad);
        dao.modificar(entidad);
    }

    public void baja(int id) throws SQLException {
        dao.eliminar(id);
    }

    public T buscar(int id) throws SQLException{
        return dao.buscar(id);
    }

    public ArrayList<T> listar() throws SQLException {
        return dao.buscarTodos();
    }
}

package interfaces;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Dao<T> {
    void agregar(T entidad) throws SQLException;
    void eliminar(int id) throws SQLException;
    void modificar(T entidad) throws SQLException;
    T buscar(int id) throws SQLException;
    ArrayList<T> buscarTodos();
}

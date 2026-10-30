package interfaces;

import java.util.ArrayList;
import java.sql.SQLException;

public interface Repositorio<T> {
    void agregar(T entidad) throws SQLException;
    void eliminar(int id) throws SQLException;
    void modificar(T entidad) throws SQLException;
    T buscar(int id) throws SQLException;
    ArrayList<T> buscarTodos();

}

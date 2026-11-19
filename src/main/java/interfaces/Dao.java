package interfaces;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Contrato común para las clases que guardan y leen datos de la base (DAO).
 * Es genérica: T es la entidad que maneja cada DAO (Artista, Contrato, Lanzamiento).
 */
public interface Dao<T> {

    // Guarda una entidad nueva en la base.
    void agregar(T entidad) throws SQLException;

    // Elimina (o da de baja) la entidad con ese id.
    void eliminar(int id) throws SQLException;

    // Actualiza en la base los datos de una entidad que ya existe.
    void modificar(T entidad) throws SQLException;

    // Devuelve la entidad con ese id.
    T buscar(int id) throws SQLException;

    // Devuelve todas las entidades guardadas.
    ArrayList<T> buscarTodos() throws SQLException;
}

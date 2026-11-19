package gestores;

import interfaces.Identificable;
import interfaces.Dao;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase genérica y abstracta con las operaciones comunes de todos los gestores:
 * alta, modificación, baja, búsqueda y listado.
 * Antes de guardar, valida la entidad con las reglas de negocio de cada gestor.
 * T es la entidad que maneja (Artista, Contrato o Lanzamiento).
 */
public abstract class Gestor<T extends Identificable> {
    protected  final Dao<T> dao;   // acceso a la base para esta entidad

    // Recibe el DAO desde afuera (lo crea Main).
    protected Gestor(Dao<T> dao) {
        this.dao = dao;
    }

    // Cada gestor define sus propias reglas para validar la entidad.
    protected abstract void validar(T entidad) throws Exception;

    // Valida la entidad y, si está bien, la guarda.
    public void alta(T entidad) throws Exception {
        validar(entidad);
        dao.agregar(entidad);
    }

    // Valida la entidad y, si está bien, guarda los cambios.
    public void modificar(T entidad) throws Exception {
        validar(entidad);
        dao.modificar(entidad);
    }

    // Da de baja la entidad con ese id.
    public void baja(int id) throws SQLException {
        dao.eliminar(id);
    }

    // Busca la entidad con ese id.
    public T buscar(int id) throws SQLException{
        return dao.buscar(id);
    }

    // Devuelve todas las entidades.
    public ArrayList<T> listar() throws SQLException {
        return dao.buscarTodos();
    }
}

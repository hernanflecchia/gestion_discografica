package repositorio;

import interfaces.Identificable;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Clase genérica para guardar objetos en memoria (no en la base).
 * Sirve para cualquier clase que tenga id (T extends Identificable).
 * En el sistema se usa para los álbumes que están de gira.
 */
public class Repositorio<T extends Identificable> {
    private final List<T> items = new ArrayList<>();

    // Agrega un elemento; no acepta nulos ni ids repetidos.
    public void agregar(T item) {
        if (item == null) {
            throw new IllegalArgumentException("El item no puede ser nulo.");
        }
        for (T existente : items) {
            if (existente.getId() == item.getId()) {
                throw new IllegalArgumentException("Ya existe un elemento con el id " + item.getId());
            }
        }
        items.add(item);
    }

    // Devuelve el elemento con ese id; avisa si no existe.
    public T buscarPorId(int id) {
        for (T item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        throw new NoSuchElementException("No existe un elemento con el id " + id);
    }

    // Devuelve todos los elementos.
    public List<T> listar() {
        return new ArrayList<>(items);      // copia: desde afuera no se puede modificar la lista interna
    }

    // Saca el elemento con ese id.
    public void eliminar(int id) {
        items.remove(buscarPorId(id));      // reutiliza buscarPorId, que ya avisa si no existe
    }

    // Dice si hay un elemento con ese id.
    public boolean existe(int id) {
        for (T item : items) {
            if (item.getId() == id) {
                return true;
            }
        }
        return false;
    }
}
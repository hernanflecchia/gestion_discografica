package repositorio;

import interfaces.Identificable;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Repositorio<T extends Identificable> {
    private final List<T> items = new ArrayList<>();

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

    public T buscarPorId(int id) {
        for (T item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        throw new NoSuchElementException("No existe un elemento con el id " + id);
    }

    public List<T> listar() {
        return new ArrayList<>(items);      // copia: desde afuera no se puede modificar la lista interna
    }

    public void eliminar(int id) {
        items.remove(buscarPorId(id));      // reutiliza buscarPorId, que ya avisa si no existe
    }

    public boolean existe(int id) {
        for (T item : items) {
            if (item.getId() == id) {
                return true;
            }
        }
        return false;
    }
}
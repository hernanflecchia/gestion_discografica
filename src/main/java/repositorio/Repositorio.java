package repositorio;

import interfaces.Identificable;

import java.util.ArrayList;
import java.util.List;

public class Repositorio<T extends Identificable> {
    private final List<T> items = new ArrayList<>();

    public void agregar(T item) {
        if (item == null) {
            throw  new IllegalArgumentException("El item no puede ser nulo.");
        }
    }        // validar id repetido

    public T buscarPorId(int id) {
        return null;
    }      // si no está → excepción propia

    public List<T> listar() {
        return items;
    }           // devolver copia (encapsulamiento)

    public void eliminar(int id) {

    }
}
package modelo;

import enums.Genero;
import enums.TipoLanzamiento;
import excepciones.LanzamientoInvalidoException;
import interfaces.Tourable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Lanzamiento con varias canciones. Es el único tipo de lanzamiento
 * que puede salir de gira, por eso implementa Tourable.
 */
public class Album extends Lanzamiento implements Tourable {
    private static final int MIN_CANCIONES = 4;
    private static final double INGRESO_POR_SHOW = 1000.0; // num random como demo

    private List<Cancion> canciones;          // canciones del álbum, en orden
    private Map<LocalDate, String> agendaTour; // shows agendados: fecha -> ciudad

    // Crea el álbum vacío; las canciones se agregan después con agregarCancion.
    public Album(int id, String titulo, LocalDate fecha, double precio,
                 Genero genero, Artista artista) {
        super(id, titulo, fecha, precio, genero, artista);
        this.canciones = new ArrayList<>();
        this.agendaTour = new TreeMap<>(); // ordenado por fecha
    }

    // Agrega una canción al final del álbum.
    public void agregarCancion(Cancion cancion) {
        if (cancion == null) {
            throw new IllegalArgumentException("La canción no puede ser nula.");
        }
        canciones.add(cancion);
    }

    // Devuelve las canciones en una lista que no se puede modificar desde afuera.
    public List<Cancion> getCanciones() {
        return Collections.unmodifiableList(canciones);
    }

    @Override
    public TipoLanzamiento getTipo() { return TipoLanzamiento.ALBUM; }

    // Regla del álbum: tiene que tener al menos MIN_CANCIONES canciones.
    @Override
    public void validar() throws LanzamientoInvalidoException {
        if (canciones.size() < MIN_CANCIONES) {
            throw new LanzamientoInvalidoException(
                "Un álbum necesita al menos " + MIN_CANCIONES + " canciones (tiene "
                + canciones.size() + ").");
        }
    }

    // Ingresos del álbum: lo que se vendió más lo que dejan los shows.
    @Override
    public double calcularIngresos() {
        double ventas = getUnidadesVendidas() * getPrecio();
        double tour = agendaTour.size() * INGRESO_POR_SHOW;
        return ventas + tour;
    }


    // Agenda un show; no puede haber dos shows del álbum el mismo día.
    @Override
    public void agendarTour(LocalDate fecha, String ciudad) {
        if (fecha == null || ciudad == null || ciudad.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha y la ciudad son obligatorias.");
        }
        if (agendaTour.containsKey(fecha)) {
            throw new IllegalArgumentException("Ya hay un show agendado el " + fecha + ".");
        }
        agendaTour.put(fecha, ciudad.trim());
    }

    // Cancela el show de esa fecha; avisa si no había ninguno.
    @Override
    public void cancelarTour(LocalDate fecha) {
        if (agendaTour.remove(fecha) == null) {
            throw new IllegalArgumentException("No hay ningún show agendado el " + fecha + ".");
        }
    }

    // Devuelve la agenda en un mapa que no se puede modificar desde afuera.
    @Override
    public Map<LocalDate, String> getAgendaTour() {
        return Collections.unmodifiableMap(agendaTour);
    }

    // Agrega a los datos generales la cantidad de canciones y de shows.
    @Override
    public String toString() {
        return super.toString() + " | canciones: " + canciones.size()
                + " | shows: " + agendaTour.size();
    }
}

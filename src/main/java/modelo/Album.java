package modelo;

import enums.Genero;
import excepciones.LanzamientoInvalidoException;
import interfaces.Tourable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Album extends Lanzamiento implements Tourable {
    private static final int MIN_CANCIONES = 4;
    private static final double INGRESO_POR_SHOW = 1000.0; // num random como demo

    private List<Cancion> canciones;
    private Map<LocalDate, String> agendaTour;

    public Album(int id, String titulo, LocalDate fecha, double precio,
                 Genero genero, Artista artista) {
        super(id, titulo, fecha, precio, genero, artista);
        this.canciones = new ArrayList<>();
        this.agendaTour = new TreeMap<>(); // ordenado por fecha
    }

    public void agregarCancion(Cancion cancion) {
        if (cancion == null) {
            throw new IllegalArgumentException("La canción no puede ser nula.");
        }
        canciones.add(cancion);
    }

    public List<Cancion> getCanciones() {
        return Collections.unmodifiableList(canciones);
    }


    @Override
    public void validar() throws LanzamientoInvalidoException {
        if (canciones.size() < MIN_CANCIONES) {
            throw new LanzamientoInvalidoException(
                "Un álbum necesita al menos " + MIN_CANCIONES + " canciones (tiene "
                + canciones.size() + ").");
        }
    }

    @Override
    public double calcularIngresos() {
        double ventas = getUnidadesVendidas() * getPrecio();
        double tour = agendaTour.size() * INGRESO_POR_SHOW;
        return ventas + tour;
    }


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

    @Override
    public void cancelarTour(LocalDate fecha) {
        if (agendaTour.remove(fecha) == null) {
            throw new IllegalArgumentException("No hay ningún show agendado el " + fecha + ".");
        }
    }

    @Override
    public Map<LocalDate, String> getAgendaTour() {
        return Collections.unmodifiableMap(agendaTour);
    }

    @Override
    public String toString() {
        return super.toString() + " | canciones: " + canciones.size()
                + " | shows: " + agendaTour.size();
    }
}

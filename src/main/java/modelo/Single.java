package modelo;

import enums.Genero;
import enums.TipoLanzamiento;
import excepciones.LanzamientoInvalidoException;
import java.time.LocalDate;

/**
 * Lanzamiento de una sola canción.
 */
public class Single extends Lanzamiento {
    private static final int MAX_DURACION_SEG = 600; // 10 minutos

    private Cancion cancion;   // la única canción del single

    public Single(int id, String titulo, LocalDate fecha, double precio,
                  Genero genero, Artista artista, Cancion cancion) {
        super(id, titulo, fecha, precio, genero, artista);
        setCancion(cancion);
    }

    public Cancion getCancion() {
        return cancion;
    }

    // El single siempre tiene que tener su canción.
    public void setCancion(Cancion cancion) {
        if (cancion == null) {
            throw new IllegalArgumentException("El single debe tener una cancion.");
        }
        this.cancion = cancion;
    }

    @Override
    public TipoLanzamiento getTipo() { return TipoLanzamiento.SINGLE; }

    //se puede cambiar a otro max, pero siendo realistas mas de 10 min un single no suele durar.
    // el sello obligaria a un radio edit, por ej.
    @Override
    public void validar() throws LanzamientoInvalidoException {
        if (cancion.getDuracionSeg() > MAX_DURACION_SEG) {
            throw new LanzamientoInvalidoException(
                "Un single no puede durar más de " + MAX_DURACION_SEG / 60 + " minutos.");
        }
    }

    // Ingresos del single: solo las ventas.
    @Override
    public double calcularIngresos() {
        return getUnidadesVendidas() * getPrecio();
    }

    // Agrega a los datos generales el nombre y la duración del tema.
    @Override
    public String toString() {
        return super.toString() + " | tema: " + cancion;
    }
}

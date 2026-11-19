package modelo;

/**
 * Canción de un lanzamiento. No existe por separado: siempre pertenece
 * a un Single o a un Album (composición).
 */
public class Cancion {
    private String titulo;
    private int duracionSeg;   // duración en segundos

    public Cancion(String titulo, int duracionSeg) {
        setTitulo(titulo);
        setDuracionSeg(duracionSeg);
    }

    public String getTitulo() {
        return titulo;
    }

    // El título es obligatorio.
    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El titulo de la cancion no puede estar vacio.");
        }
        this.titulo = titulo.trim();
    }

    public int getDuracionSeg() {
        return duracionSeg;
    }

    // La duración tiene que ser mayor a cero.
    public void setDuracionSeg(int duracionSeg) {
        if (duracionSeg <= 0) {
            throw new IllegalArgumentException("La duracion debe ser mayor a 0 segundos.");
        }
        this.duracionSeg = duracionSeg;
    }

    // Muestra la canción con la duración en formato minutos:segundos, por ejemplo "Flaca (4:37)".
    @Override
    public String toString() {
        return titulo + " (" + duracionSeg / 60 + ":" + String.format("%02d", duracionSeg % 60) + ")";
    }

    }

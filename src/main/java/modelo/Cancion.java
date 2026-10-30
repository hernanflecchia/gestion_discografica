package modelo;

public class Cancion {
    private String titulo;
    private int duracionSeg;

    public Cancion(String titulo, int duracionSeg) {
        setTitulo(titulo);
        setDuracionSeg(duracionSeg);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El titulo de la cancion no puede estar vacio.");
        }
        this.titulo = titulo.trim();
    }

    public int getDuracionSeg() {
        return duracionSeg;
    }

    public void setDuracionSeg(int duracionSeg) {
        if (duracionSeg <= 0) {
            throw new IllegalArgumentException("La duracion debe ser mayor a 0 segundos.");
        }
        this.duracionSeg = duracionSeg;
    }

    @Override
    public String toString() {
        return titulo + " (" + duracionSeg / 60 + ":" + String.format("%02d", duracionSeg % 60) + ")";
    }

    }

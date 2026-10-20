package modelo;

import excepciones.LanzamientoInvalidoException;
import java.time.LocalDate;

public abstract class Lanzamiento {
    private int id;
    private String titulo;
    private LocalDate fecha;
    private double precio;
    private int unidadesVendidas;
    private Genero genero;
    private Artista artista;

    protected Lanzamiento(int id, String titulo, LocalDate fecha, double precio,
                          Genero genero, Artista artista) {
        setId(id);
        setTitulo(titulo);
        setFecha(fecha);
        setPrecio(precio);
        setGenero(genero);
        setArtista(artista);
        this.unidadesVendidas = 0;
    }

    public abstract void validar() throws LanzamientoInvalidoException;

    public abstract double calcularIngresos();

    public void registrarVenta(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad de ventas debe ser superior a 0.");
        }
        unidadesVendidas += cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID invalido. Debe ser mayor a 0.");
        }
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El titulo no puede estar vacio.");
        }
        this.titulo = titulo.trim();
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula.");
        }
        this.fecha = fecha;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        this.precio = precio;
    }

    public int getUnidadesVendidas() {
        return unidadesVendidas;
    }

    public void setUnidadesVendidas(int unidadesVendidas) {
        if (unidadesVendidas < 0) {
            throw new IllegalArgumentException("Las unidades vendidas no pueden ser negativas.");
        }
        this.unidadesVendidas = unidadesVendidas;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        if (genero == null) {
            throw new IllegalArgumentException("Debe ingresarse un genero.");
        }
        this.genero = genero;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        if (artista == null) {
            throw new IllegalArgumentException("El lanzamiento necesita un artista.");
        }
        this.artista = artista;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " #" + id + " | " + titulo
                + " | " + artista.getNombre() + " | " + genero
                + " | $" + precio + " | vendidos: " + unidadesVendidas;
    }
}

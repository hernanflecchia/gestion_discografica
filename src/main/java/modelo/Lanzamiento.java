package modelo;

import enums.Genero;
import enums.TipoLanzamiento;
import excepciones.LanzamientoInvalidoException;
import interfaces.Identificable;
import java.time.LocalDate;

/**
 * Clase base de todo lo que publica el sello.
 * Es abstracta: se crea un Single o un Album. Guarda los datos comunes
 * (título, fecha, precio, género, artista y unidades vendidas) y los valida.
 * Cada subclase define cómo se valida y cómo calcula sus ingresos.
 */
public abstract class Lanzamiento implements Identificable {
    private int id;
    private String titulo;
    private LocalDate fecha;
    private double precio;
    private int unidadesVendidas;
    private Genero genero;
    private Artista artista;   // artista que lo publica

    // Un lanzamiento nuevo arranca con 0 unidades vendidas.
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

    // Revisa que el lanzamiento cumpla las reglas de su tipo.
    public abstract void validar() throws LanzamientoInvalidoException;

    // Calcula cuánta plata generó el lanzamiento.
    public abstract double calcularIngresos();

    // Devuelve si es SINGLE o ALBUM.
    public abstract TipoLanzamiento getTipo();

    // Suma una venta a las unidades vendidas; la cantidad tiene que ser mayor a 0.
    public void registrarVenta(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad vendida debe ser mayor a 0.");
        }
        unidadesVendidas += cantidad;
    }

    @Override
    public int getId() {
        return id;
    }

    // El id puede ser 0 (todavía no se guardó) pero nunca negativo.
    @Override
    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("El id no puede ser negativo.");
        }
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    // El título es obligatorio.
    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacio.");
        }
        this.titulo = titulo.trim();
    }

    public LocalDate getFecha() {
        return fecha;
    }

    // La fecha de lanzamiento es obligatoria.
    public void setFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula.");
        }
        this.fecha = fecha;
    }

    public double getPrecio() {
        return precio;
    }

    // El precio no puede ser negativo.
    public void setPrecio(double precio) {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        this.precio = precio;
    }

    public int getUnidadesVendidas() {
        return unidadesVendidas;
    }

    // Se usa al leer de la base, para cargar las unidades que ya se vendieron.
    public void setUnidadesVendidas(int unidadesVendidas) {
        if (unidadesVendidas < 0) {
            throw new IllegalArgumentException("Las unidades vendidas no pueden ser negativas.");
        }
        this.unidadesVendidas = unidadesVendidas;
    }

    public Genero getGenero() {
        return genero;
    }

    // El género es obligatorio.
    public void setGenero(Genero genero) {
        if (genero == null) {
            throw new IllegalArgumentException("El genero no puede ser nulo.");
        }
        this.genero = genero;
    }

    public Artista getArtista() {
        return artista;
    }

    // Todo lanzamiento tiene que tener un artista.
    public void setArtista(Artista artista) {
        if (artista == null) {
            throw new IllegalArgumentException("El lanzamiento necesita un artista.");
        }
        this.artista = artista;
    }

    // Muestra los datos en una línea; empieza con "Single" o "Album" según la clase.
    @Override
    public String toString() {
        return getClass().getSimpleName() + " #" + id + " | " + titulo
                + " | " + artista.getNombre() + " | " + genero
                + " | $" + precio + " | vendidos: " + unidadesVendidas;
    }
}

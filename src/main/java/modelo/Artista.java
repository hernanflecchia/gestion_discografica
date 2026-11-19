package modelo;

import enums.EstadoArtista;
import enums.TipoArtista;
import interfaces.Identificable;

/**
 * Clase base de todos los artistas del sello.
 * Es abstracta: no se crea un "Artista" suelto, sino un Solista o una Banda.
 * Guarda los datos comunes (id, nombre, país y estado) y los valida.
 */
public abstract class Artista implements Identificable {

    private  int id;
    private String nombre;
    private String pais;
    private EstadoArtista estado;

    // Cada subclase dice qué tipo de artista es (SOLISTA o BANDA).
    public abstract TipoArtista getTipo();

    //constructor para nuevo artista
    //No genero ID, queda en 0 (debería ser generado al guardarse en la Base de Datos?)
    //El ID se asigna cuando se guarda en la base de datos.
    public Artista(String nombre, String pais) {

        setNombre(nombre);
        setPais(pais);// lo hago de esta forma porque después hago validación del nombre y pais
        this.estado = EstadoArtista.ACTIVO;
    }

    //constructor para recuperar un artista existente en base de datos
    public Artista(int id, String nombre, String pais, EstadoArtista estado) {
        validarId(id);//hago llamada al método validarId
        this.id = id;
        setNombre(nombre);
        setPais(pais);
        setEstado(estado);
    }

    //getters
    @Override
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPais() {
        return pais;
    }

    public EstadoArtista getEstado() {
        return estado;
    }

    //Setter del nombre con validacion (nombre vacio o carga de espacios)
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre no puede estar vacio."
            );
        }
        this.nombre = nombre.trim();
    }

    //Setter pais con validacion

    public void setPais(String pais) {
        if(pais == null || pais.trim().isEmpty()){
            throw new IllegalArgumentException(
                    "El pais no puede estar vacio."
            );
        }
        this.pais = pais.trim();
    }

    //Setter del estado con validacion

    public void setEstado(EstadoArtista estado) {
        if (estado == null) {
            throw new IllegalArgumentException(
                    "El estado no puede ser nulo."
            );
        }
        this.estado = estado;
    }

    // Implementación del metodo setId de Idntificable
    // El ID solamente puede asignarse una vez.
    @Override
    public void setId(int id) {
        asignarId(id);
    }

    // Asigna el ID generado por la Base de Datos.
    public void asignarId(int id) {
        if (this.id != 0) {
            throw new IllegalStateException(
                    "El artista ya tiene un ID asignado."
            );
        }

        validarId(id);
        this.id = id;
    }

    //Validacion del ID
    private void validarId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El ID debe ser mayor que cero."
            );
        }
    }

    // Cada subclase agrega su descripción (nombre real, integrantes, etc.).
    public abstract String descripcion();

    // Muestra los datos comunes y después la descripción de la subclase.
    @Override
    public String toString() {
        return "Artista: " +
                "\nID: " + id +
                "\nNombre: " + nombre +
                "\nPais: " + pais +
                "\nEstado: " + estado +
                "\n" + descripcion();
    }
}

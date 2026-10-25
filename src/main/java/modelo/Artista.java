package modelo;

import enums.EstadoArtista;

public abstract class Artista {

    private  int id;
    private String nombre;
    private EstadoArtista estado;

    //constructor para nuevo artista
    //No genero ID (deberia ser generado al guardarse en la Base de Datos?)

    public Artista(String nombre) {

        setNombre(nombre);// lo hago de esta forma porque despues hago validacion del nombre
        this.estado = EstadoArtista.ACTIVO;
    }

    //constructor para recuperar un artista

    public Artista(int id, String nombre, EstadoArtista estado) {
        validarId(id);//hago llamada al metodo validarId
        this.id = id;
        setNombre(nombre);
        setEstado(estado);
    }


    //getters


    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public EstadoArtista getEstado() {
        return estado;
    }

    //setters

    //Setter del nombre con validacion
    public void setNombre(String nombre) {
        if (nombre == null) {
            throw new IllegalArgumentException(
                    "El nombre no puede ser nulo."
            );
        }

        nombre = nombre.trim();//con el .trim elimino los posibles espacios generados al comienzo y al final

        if (nombre.isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre no puede estar vacío."
            );
        }

        this.nombre = nombre;
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

    //Asigna el ID generado por la Base de Datos. Hernan deberia recuperar ID en ArtistaDAO
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

}

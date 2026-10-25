package modelo;

import enums.EstadoArtista;

public abstract class Artista {

    private  int id;
    private String nombre;
    private String pais;
    private EstadoArtista estado;

    //constructor para nuevo artista
    //No genero ID (deberia ser generado al guardarse en la Base de Datos?)

    public Artista(String nombre, String pais) {

        setNombre(nombre);
        setPais(pais);// lo hago de esta forma porque despues hago validacion del nombre y pais
        this.estado = EstadoArtista.ACTIVO;
    }

    //constructor para recuperar un artista

    public Artista(int id, String nombre, String pais, EstadoArtista estado) {
        validarId(id);//hago llamada al metodo validarId
        this.id = id;
        setNombre(nombre);
        setPais(pais);
        setEstado(estado);
    }


    //getters


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

    //setters

    //Setter del nombre con validacion (nombre vacio o carga de espacios)
    public void setNombre(String nombre) {
        if (nombre == null || pais.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre no puede estar vacio."
            );
        }
        this.nombre = nombre;
    }

    //Setter pais con validacion

    public void setPais(String pais) {
        if(pais == null || pais.trim().isEmpty()){
            throw new IllegalArgumentException(
                    "El pais no puede estar vacio."
            );
        }
        this.pais = pais;
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

    //metodos
    //Validacion del ID
    private void validarId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El ID debe ser mayor que cero."
            );
        }
    }

    //metodo abstracto

    public abstract String descripcion();

    //to string

    @Override
    public String toString() {
        return "Artista: " +
                "\nID: " + id +
                "\nNombre: " + nombre + '\'' +
                "\nPais: " + pais + '\'' +
                "\nEstado: " + estado +
                '|'+ descripcion();
    }


//OBSERVACIONES

// Realice algunas consultas al chatgpt porque tenia algunas dudas en cuanto a la generacion del ID desde
//la clase Artista; como ya trabajamos con la Base de Datos para la generacion y la persistencia el ID
//se genera ahi. Al crear un nuevo Artista el ID queda temporalmente en 0; al recuperarlo de la base de daros
// el constructor recibe el ID existente y se asigna mediante asignarId() para la persistencia

//Segun chatgpt un codigo similar a este deberia estar en artistaDAO para asignar ID a artista

// String sql =
//    "INSERT INTO artistas (nombre, estado) VALUES (?, ?)";
//
//try (PreparedStatement ps = conexion.prepareStatement(
//        sql, Statement.RETURN_GENERATED_KEYS)) {
//
//    ps.setString(1, artista.getNombre());
//    ps.setString(2, artista.getEstado().name());
//
//    ps.executeUpdate();
//
//    try (ResultSet rs = ps.getGeneratedKeys()) {
//        if (rs.next()) {
//            int idGenerado = rs.getInt(1);
//            artista.asignarId(idGenerado);
//        }
//    }
//}


}

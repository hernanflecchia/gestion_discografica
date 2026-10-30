package modelo;

import enums.EstadoArtista;

public class Solista extends Artista{

    private String nombreReal;

    //constructores
    public Solista(String nombre, String pais, String nombreReal) {
        super(nombre, pais);
        setNombreReal(nombreReal);
    }

    public Solista(int id, String nombre, String pais, EstadoArtista estado, String nombreReal) {
        super(id, nombre, pais, estado);
        setNombreReal(nombreReal);
    }

    //getter

    public String getNombreReal() {
        return nombreReal;
    }

    //Setter con validacion


    public void setNombreReal(String nombreReal) {
        if (nombreReal == null || nombreReal.trim().isEmpty()){
            throw new IllegalArgumentException(
                    "El nombre real no puede estar vacio."
            );
        }
        this.nombreReal = nombreReal.trim();
    }

    //implementacion de metodo abstracto
    @Override
    public String descripcion() {
        return "Tipo de Artista: Solista";
    }

    //to string


    @Override
    public String toString() {
        return "Solista: " +
                "\nNombreReal: " + nombreReal + '\'' +
                "| " + super.toString();
    }

    @Override
    public void setId(int id) {

    }
}

conflicts//OBSERVACIONES
//sin dudas en cuanto a esta clase
//lo mismo que en la clase abstracta, nno genere this.nombreReal desde el constructor
// en constructor puse setNombreReal(nombreReal) pero en los setters hice validacion por nombre nulo,
//o vacio o con espacios con manejo excepcion IllegalArgumentException

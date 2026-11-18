
package modelo;

import enums.EstadoArtista;
import enums.TipoArtista;

public class Solista extends Artista {

    private String nombreReal;

    // Constructor para crear un solista nuevo
    public Solista(String nombre, String pais, String nombreReal) {
        super(nombre, pais);
        setNombreReal(nombreReal);
    }

    // Constructor para recuperar un solista existente
    public Solista(int id, String nombre, String pais,
                   EstadoArtista estado, String nombreReal) {
        super(id, nombre, pais, estado);
        setNombreReal(nombreReal);
    }

    // Getter
    public String getNombreReal() {
        return nombreReal;
    }

    // Setter con validación
    public void setNombreReal(String nombreReal) {
        if (nombreReal == null || nombreReal.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre real no puede estar vacío."
            );
        }
        this.nombreReal = nombreReal.trim();
    }

    // Implementación de los métodos abstractos de Artista
    @Override
    public TipoArtista getTipo() {
        return TipoArtista.SOLISTA;
    }

    @Override
    public String descripcion() {
        return "Tipo de Artista: Solista";
    }

    // Representación textual del solista
    @Override
    public String toString() {
        return "Solista: " +
                "\nNombre real: " + nombreReal +
                "\n" + super.toString();
    }
}
//OBSERVACIONES
//sin dudas en cuanto a esta clase
//lo mismo que en la clase abstracta, nno genere this.nombreReal desde el constructor
// en constructor puse setNombreReal(nombreReal) pero en los setters hice validacion por nombre nulo,
//o vacio o con espacios con manejo excepcion IllegalArgumentException\
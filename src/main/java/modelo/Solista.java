
package modelo;

import enums.EstadoArtista;
import enums.TipoArtista;

/**
 * Artista que trabaja solo.
 * Además de los datos de Artista, guarda su nombre real.
 */
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

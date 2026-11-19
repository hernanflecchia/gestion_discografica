package excepciones;

/**
 * Salta la exception si un lanzamiento no cumple con los reqs de su tipo.
 * Por ejemplo: un álbum con menos canciones del mínimo, o un single demasiado largo.
 */
public class LanzamientoInvalidoException extends Exception {

    public LanzamientoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
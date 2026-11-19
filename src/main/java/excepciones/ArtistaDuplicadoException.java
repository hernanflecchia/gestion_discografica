package excepciones;

/**
 * Se lanza cuando se intenta dar de alta un artista con un nombre
 * que ya está registrado (sin importar mayúsculas y minúsculas).
 */
public class ArtistaDuplicadoException extends RuntimeException {
    public ArtistaDuplicadoException(String mensaje) {

      super(mensaje);
    }
}

package excepciones;

public class ArtistaDuplicadoException extends RuntimeException {
    public ArtistaDuplicadoException(String mensaje) {

      super(mensaje);
    }
}

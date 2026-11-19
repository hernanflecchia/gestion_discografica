package excepciones;

// Se lanza cuando se busca, modifica o da de baja un id que no existe en la base.
// El mensaje se arma con el nombre de la entidad y el id, así sale siempre igual.
public class EntidadNoEncontradaException extends RuntimeException {

    public EntidadNoEncontradaException(String entidad, int id) {
        super("No existe el " + entidad + " con el id: " + id);
    }
}
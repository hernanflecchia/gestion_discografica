package excepciones;

/**
 * Se lanza cuando se intenta rescindir un contrato que ya está vencido.
 * Guarda el id del contrato por si se quiere informar o consultar.
 */
public class ContratoVencidoException extends Exception{

    private final int id;   // id del contrato vencido

    public ContratoVencidoException(int id) {
        super("El contrato " + id + " se encuentra vencido.");
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

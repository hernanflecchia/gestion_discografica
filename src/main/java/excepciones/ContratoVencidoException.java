package excepciones;

public class ContratoVencidoException extends Exception{

    private final int id;

    public ContratoVencidoException(int id) {
        super("El contrato " + id + " se encuentra vencido.");
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

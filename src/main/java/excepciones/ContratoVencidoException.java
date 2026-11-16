package excepciones;

public class ContratoVencidoException extends Exception{

    private final int id;

    public ContratoVencidoException(int id) {
        super("El contrato se encuentra vencido"+ id);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

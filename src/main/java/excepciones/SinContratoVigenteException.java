package excepciones;

import modelo.Artista;

/**
 * Se lanza cuando un artista quiere hacer algo que requiere un contrato vigente
 * (publicar un lanzamiento o cobrar regalías) y no lo tiene.
 * Hereda de IllegalStateException porque es un estado que no permite la operación.
 */
public class SinContratoVigenteException extends IllegalStateException {
    public SinContratoVigenteException(Artista artista) {
        super("El artista " + artista.getNombre() + " no tiene un contrato vigente.");
    }
}
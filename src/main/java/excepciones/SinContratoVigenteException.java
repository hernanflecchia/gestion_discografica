package excepciones;

import modelo.Artista;

public class SinContratoVigenteException extends IllegalStateException {
    public SinContratoVigenteException(Artista artista) {
        super("El artista " + artista.getNombre() + " no tiene un contrato vigente.");
    }
}
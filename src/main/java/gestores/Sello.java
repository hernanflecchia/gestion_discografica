package gestores;

import excepciones.LanzamientoInvalidoException;
import modelo.Artista;
import modelo.Lanzamiento;

import java.time.LocalDate;

public class Sello {
    private String nombre;
    private GestorArtista artistas;
    private GestorLanzamiento lanzamientos;
    private GestorContrato contratos;

    public void firmarContrato(Artista a, LocalDate inicio, LocalDate fin, double porcentaje) {

    } // crea el Contrato y llama a contratos.alta(...)

    public void publicarLanzamiento(Lanzamiento l) throws LanzamientoInvalidoException {

    } // 1) ¿el artista tiene un contrato vigente? (estaVigente())  2) l.validar()  3) lanzamientos.alta(l)

    public double calcularRegalias(Artista a) {
        return 0;
    } // suma calcularIngresos() de sus lanzamientos * porcentajeRegalias del contrato
}
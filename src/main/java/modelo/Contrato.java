package modelo;

import enums.EstadoContrato;
import interfaces.Identificable;

import java.time.LocalDate;

public class Contrato implements Identificable {

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double porcentajeRegalias;
    private EstadoContrato estadoContrato;

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void setId(int id) {

    }

    public boolean estaVigente() {
        return false;
    }

    public void actualizarEstado() {

    }

    public void rescindir() {
    }
}

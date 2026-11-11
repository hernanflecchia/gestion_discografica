package modelo;

import enums.EstadoContrato;
import interfaces.Identificable;

import java.time.LocalDate;

public class Contrato implements Identificable {

    private int id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double porcentajeRegalias;
    private EstadoContrato estadoContrato;
    private Artista artista;

    public Contrato(LocalDate fechaInicio, LocalDate fechaFin, double porcentajeRegalias, EstadoContrato estadoContrato, Artista artista) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.porcentajeRegalias = porcentajeRegalias;
        this.estadoContrato = estadoContrato;
        this.artista = artista;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void setId(int id) {

    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public double getPorcentajeRegalias() {
        return porcentajeRegalias;
    }

    public EstadoContrato getEstadoContrato() {
        return estadoContrato;
    }

    public Artista getArtista() {
        return artista;
    }

    public boolean estaVigente() {
        return false;
    }

    public void actualizarEstado() {

    }

    public void rescindir() {
    }
}

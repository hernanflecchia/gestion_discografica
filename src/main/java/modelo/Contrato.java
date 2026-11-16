package modelo;

import enums.EstadoContrato;
import excepciones.ContratoVencidoException;
import interfaces.Identificable;

import java.time.LocalDate;

public class Contrato implements Identificable {

    private int id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double porcentajeRegalias;
    private EstadoContrato estado;
    private Artista artista;

    // Contrato nuevo (firmarContrato): nace VIGENTE y sin id, el id lo asigna la base de datos
    public Contrato(Artista artista, LocalDate fechaInicio, LocalDate fechaFin, double porcentajeRegalias) {
        this(0, fechaInicio, fechaFin, porcentajeRegalias, EstadoContrato.VIGENTE, artista);
    }

    // Contrato recuperado de la base de datos (lo usa ContratoDao)
    public Contrato(int id, LocalDate fechaInicio, LocalDate fechaFin, double porcentajeRegalias,
                    EstadoContrato estado, Artista artista) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.porcentajeRegalias = porcentajeRegalias;
        this.estado = estado;
        this.artista = artista;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public double getPorcentajeRegalias() {
        return porcentajeRegalias;
    }

    public void setPorcentajeRegalias(double porcentajeRegalias) {
        this.porcentajeRegalias = porcentajeRegalias;
    }

    public Artista getArtista() {
        return artista;
    }

    public EstadoContrato getEstado() {
        actualizarEstado();
        return estado;
    }

    public void setEstado(EstadoContrato estado) {
        this.estado = estado;
    }

    public boolean estaVigente() {
        actualizarEstado();
        return this.estado == EstadoContrato.VIGENTE;
    }

    public void actualizarEstado() {
        if (this.estado != EstadoContrato.RESCINDIDO && this.fechaFin != null) {
            if (LocalDate.now().isAfter(fechaFin)) {
                this.estado = EstadoContrato.VENCIDO;
            } else {
                this.estado = EstadoContrato.VIGENTE;
            }
        }
    }

    public void rescindir() throws ContratoVencidoException {
        actualizarEstado();
        if (this.estado == EstadoContrato.VENCIDO) {
            throw new ContratoVencidoException(id);
        }
        if (this.estado == EstadoContrato.RESCINDIDO) {
            throw new IllegalStateException("El contrato ya se encuentra rescindido");
        }
        this.estado = EstadoContrato.RESCINDIDO;
    }

    @Override
    public String toString() {
        return "Contrato #" + id + " | " + (artista != null ? artista.getNombre() : "sin artista")
                + " | " + fechaInicio + " a " + fechaFin
                + " | " + porcentajeRegalias + "% | " + getEstado();
    }
}

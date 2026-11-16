package modelo;

import Enums.EstadoContrato;
import Excepciones.ContratoVencidoException;

import java.time.LocalDate;

public class Contrato implements Identificable{

    private int id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double porcentajeRegalias;
    private EstadoContrato estado;


    public Contrato(int id, LocalDate fechaInicio, LocalDate fechaFin, double porcentajeRegalias) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.porcentajeRegalias = porcentajeRegalias;
        this.estado = EstadoContrato.VIGENTE;
    }

    public int getId() {
        return id;
    }

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

    public EstadoContrato getEstado() {
        actualizarEstado();
        return estado;
    }

    public void setEstado(EstadoContrato estado) {
        this.estado = estado;
    }



    public boolean estaVigente(){
        actualizarEstado();
        return this.estado == EstadoContrato.VIGENTE;

    }

    public void actualizarEstado(){

        if (this.estado!= EstadoContrato.RESCINDIDO && this.fechaFin !=null)
        {
            if( LocalDate.now().isAfter(fechaFin))
            {
                this.estado=EstadoContrato.VENCIDO;
            }
            else
                this.estado=EstadoContrato.VIGENTE;
        }
    }

    public void rescindir() throws ContratoVencidoException
    {
        actualizarEstado();
        if(this.estado == EstadoContrato.VENCIDO)
        {
            throw new ContratoVencidoException(id);
        }
        if(this.estado==EstadoContrato.RESCINDIDO)
        {
            throw new IllegalStateException("El contrato ya se encuentra rescindido");
        }
        this.estado= EstadoContrato.RESCINDIDO;
    }

}

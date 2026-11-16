package modelo;

import Enums.EstadoContrato;
import Excepciones.ContratoVencidoException;
import Interfaces.Identificable;

import java.time.LocalDate;

public class Contrato implements Identificable {

    private int id;
    private Artista artista;    //firmado por..
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double porcentajeRegalias;
    private EstadoContrato estado;


    public Contrato( Artista artista, LocalDate fechaInicio, LocalDate fechaFin, double porcentajeRegalias) {
       setArtista(artista);
       setFechaInicio(fechaInicio);
       setFechaFin(fechaFin);
       setPorcentajeRegalias(porcentajeRegalias);
        this.estado=EstadoContrato.VIGENTE;
    }
    public Contrato( int id,Artista artista, LocalDate fechaInicio, LocalDate fechaFin, double porcentajeRegalias,EstadoContrato estado) {
        validadId(id);
        this.id=id;
        setArtista(artista);
        setFechaInicio(fechaInicio);
        setFechaFin(fechaFin);
        setPorcentajeRegalias(porcentajeRegalias);
        setEstado(estado);
    }


    @Override
    public int getId() {
        return id;
    }

    public Artista getArtista() {
        return artista;
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
    public EstadoContrato getEstado() {
        actualizarEstado();
        return estado;
    }

    public void asignarId(int id){
        if(this.id != 0 ){
            throw new IllegalStateException("El contrato ya tiene un identificador asignado");
        }
        validarId(id);
        this.id=id;
    }
    public void setArtista(Artista artista)
    {
     if(artista == null){
         throw  new IllegalArgumentException("El contrato debe estar asociado a un artista");
     }
     this.artista=artista;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        if(fechaInicio == null)
        {
            throw  new IllegalArgumentException("La fecha de inicio no puede nula");
        }
        if(fechaInicio.isAfter(this.fechaFin))
        {
            throw new IllegalArgumentException("La fecha inico no puede ser posterior a la fecha de inicio");
        }
        this.fechaInicio = fechaInicio;
    }


    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }



    public void setPorcentajeRegalias(double porcentajeRegalias) {
        this.porcentajeRegalias = porcentajeRegalias;
    }



    public void setEstado(EstadoContrato estado) {
        this.estado = estado;
    }



    public boolean estaVigente(){
        actualizarEstado();
        return this.estado == EstadoContrato.VIGENTE;

    }
    private void validarId(int id) {
        if (id <= 0)
        {
            throw new IllegalArgumentException("El identificador debe ser mayor que cero");
        }
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

    @Override
    public String toString() {
        return "Contrato" +
                "ID=" + id +
                ", Artista=" + artista.getNombre() +
                ", Fecha Inicio=" + fechaInicio +
                ", Fecha Fin=" + fechaFin +
                ", Porcentaje Regalias=" + porcentajeRegalias + "%"+
                ", Estado=" + estado +
                '}';
    }

    return "Contrato: " +
            "\nID: " + id +
            "\nArtista: " + artista.getNombre() + " (ID: " + artista.getId() + ")" +
            "\nFecha de Inicio: " + fechaInicio +
            "\nFecha de Fin: " + fechaFin +
            "\nPorcentaje de Regalías: " + porcentajeRegalias + "%" +
            "\nEstado: " + estado;
}
}

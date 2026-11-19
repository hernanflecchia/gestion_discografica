package modelo;

import enums.EstadoContrato;
import excepciones.ContratoVencidoException;
import interfaces.Identificable;

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
        validarId(id);
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

    @Override
    public void setId(int id) {
        this.id = id;
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
        if(this.fechaFin != null && fechaInicio.isAfter(this.fechaFin))
        {
            throw new IllegalArgumentException("La fecha inico no puede ser posterior a la fecha de inicio");
        }
        this.fechaInicio = fechaInicio;
    }


    public void setFechaFin(LocalDate fechaFin)
    {
     if (fechaFin == null)
     {
         throw new IllegalArgumentException("La fecha fin no puede ser nula");
     }
     if(this.fechaInicio != null && fechaFin.isBefore(this.fechaInicio))
     {
         throw  new IllegalArgumentException("La fecha fin no puede ser anterior a la fecha inicio");
     }
        this.fechaFin = fechaFin;
    }



    public void setPorcentajeRegalias(double porcentajeRegalias)
    {
        if(porcentajeRegalias <= 0 || porcentajeRegalias > 100)
        {
          throw  new IllegalArgumentException("El porcentaje de regalias debe ser mayor a cero y menor o igual a cien");
        }

        this.porcentajeRegalias = porcentajeRegalias;
    }



    public void setEstado(EstadoContrato estado)
    {
        if(estado == null)
        {
         throw new IllegalArgumentException("El estado no puede ser nulo");
        }
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
                ", Estado=" + getEstado() +
                '}';
    }


}

package modelo;

import enums.EstadoContrato;
import excepciones.ContratoVencidoException;
import interfaces.Identificable;

import java.time.LocalDate;

/**
 * Contrato entre el sello y un artista: tiene un período (inicio y fin)
 * y el porcentaje de regalías que cobra el artista.
 * El estado (vigente, vencido o rescindido) se recalcula según la fecha de hoy.
 */
public class Contrato implements Identificable {

    private int id;
    private Artista artista;    //firmado por..
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double porcentajeRegalias;
    private EstadoContrato estado;


    // Constructor para un contrato nuevo: nace VIGENTE y sin id (lo pone la base).
    public Contrato( Artista artista, LocalDate fechaInicio, LocalDate fechaFin, double porcentajeRegalias) {
       setArtista(artista);
       setFechaInicio(fechaInicio);
       setFechaFin(fechaFin);
       setPorcentajeRegalias(porcentajeRegalias);
        this.estado=EstadoContrato.VIGENTE;
    }

    // Constructor para recuperar un contrato que ya está guardado en la base.
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
    // Antes de devolver el estado lo actualiza, por si el contrato venció.
    public EstadoContrato getEstado() {
        actualizarEstado();
        return estado;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    // Asigna el id generado por la base; solo se puede hacer una vez.
    public void asignarId(int id){
        if(this.id != 0 ){
            throw new IllegalStateException("El contrato ya tiene un identificador asignado");
        }
        validarId(id);
        this.id=id;
    }
    // Todo contrato tiene que estar asociado a un artista.
    public void setArtista(Artista artista)
    {
     if(artista == null){
         throw  new IllegalArgumentException("El contrato debe estar asociado a un artista");
     }
     this.artista=artista;
    }

    // La fecha de inicio es obligatoria y tiene que ser anterior a la de fin.
    public void setFechaInicio(LocalDate fechaInicio) {
        if(fechaInicio == null)
        {
            throw  new IllegalArgumentException("La fecha de inicio no puede ser nula");
        }
        if (this.fechaFin != null && !fechaInicio.isBefore(this.fechaFin))
        {
            throw new IllegalArgumentException("La fecha inicio tiene que ser anterior a la fecha fin");
        }
        this.fechaInicio = fechaInicio;
    }


    // La fecha de fin es obligatoria y tiene que ser posterior a la de inicio.
    public void setFechaFin(LocalDate fechaFin)
    {
     if (fechaFin == null)
     {
         throw new IllegalArgumentException("La fecha fin no puede ser nula");
     }
     if(this.fechaInicio != null && !fechaFin.isAfter(this.fechaInicio))
     {
         throw  new IllegalArgumentException("La fecha fin tiene que ser posterior a la fecha inicio");
     }
        this.fechaFin = fechaFin;
    }



    // El porcentaje tiene que ser mayor a 0 y como máximo 100.
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



    // Está vigente si no fue rescindido y hoy está entre la fecha de inicio y la de fin.
    public boolean estaVigente() {
        actualizarEstado();
        LocalDate hoy = LocalDate.now();
        return this.estado == EstadoContrato.VIGENTE
                && !hoy.isBefore(fechaInicio)
                && !hoy.isAfter(fechaFin);
    }

    // El id tiene que ser mayor a cero.
    private void validarId(int id) {
        if (id <= 0)
        {
            throw new IllegalArgumentException("El identificador debe ser mayor que cero");
        }
    }

    // Recalcula el estado según la fecha de hoy.
    // Un contrato rescindido no cambia nunca más.
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

    // Corta el contrato antes de tiempo.
    // No se puede rescindir uno vencido ni uno que ya estaba rescindido.
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
        return "Contrato{" + "ID=" + id +
                ", Artista=" + artista.getNombre() +
                ", Fecha Inicio=" + fechaInicio +
                ", Fecha Fin=" + fechaFin +
                ", Porcentaje Regalias=" + porcentajeRegalias + "%"+
                ", Estado=" + getEstado() +
                '}';
    }


}

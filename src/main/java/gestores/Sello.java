package gestores;

import enums.EstadoArtista;
import excepciones.LanzamientoInvalidoException;
import modelo.Artista;
import modelo.Contrato;
import modelo.Lanzamiento;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Sello {
    private String nombre;
    private GestorArtista artistas;
    private GestorContrato contratos;
    private GestorLanzamiento lanzamientos;

    public Sello(String nombre, GestorArtista artistas, GestorContrato contratos, GestorLanzamiento lanzamientos) {
        this.nombre = nombre;
        this.artistas = artistas;
        this.lanzamientos = lanzamientos;
        this.contratos = contratos;
    }

    // Artistas
    public void altaArtista(Artista artista) throws Exception {
        artistas.alta(artista);
    }

    public Artista buscarArtista(int id) throws SQLException {
        return artistas.buscar(id);
    }

    public void bajaArtista(int id) throws SQLException {
        artistas.baja(id);
    }

    // Contratos
    public void firmarContrato(int artistaId, LocalDate inicio, LocalDate fin, double porcentaje) throws Exception {
        Artista artista = artistas.buscar(artistaId);
        if (artista.getEstado() != EstadoArtista.ACTIVO) {
            throw new IllegalStateException("Este artista no esta activo");
        }
        Contrato contrato = new Contrato(artista, inicio, fin, porcentaje);
        contratos.alta(contrato);
    }

    public void rescindirContrato(int contratoId) throws Exception {
        Contrato contrato = contratos.buscar(contratoId);
        contrato.rescindir();
        // guarda el estado RESCINDIDO en la BD
        contratos.modificar(contrato);
    }

    // Lanzamientos
    public void publicarLanzamiento(Lanzamiento lanzamiento) throws Exception {
        Artista artista = lanzamiento.getArtista();
        if (artista.getEstado() != EstadoArtista.ACTIVO) {
            throw new IllegalStateException("Este artista no esta activo");
        }
        if (contratos.buscarVigente(artista) == null) {
            throw new IllegalStateException("El artista " + artista.getNombre() + " no tiene un contrato vigente.");
        }
        lanzamientos.alta(lanzamiento);
    }

    public void registrarVenta(int lanzamientoId, int cantidad) throws Exception {
        Lanzamiento lanzamiento = lanzamientos.buscar(lanzamientoId);
        lanzamiento.registrarVenta(cantidad);
        lanzamientos.modificar(lanzamiento);
    }

    public Lanzamiento buscarLanzamiento(int id) throws SQLException {
        return lanzamientos.buscar(id);
    }

    // Regalías
    public double calcularRegalias(int artistaId) throws Exception {
        Artista artista = artistas.buscar(artistaId);
        Contrato contrato = contratos.buscarVigente(artista);
        if (contrato == null) {
            throw new IllegalStateException("El artista " + artista.getNombre() + " no tiene un contrato vigente.");
        }
        double importe = 0;
        for (Lanzamiento lanzamiento: lanzamientos.listarPorArtista(artista)) {
            importe += lanzamiento.calcularIngresos();
        }
        return importe * contrato.getPorcentajeRegalias() / 100;
    }

    public List<Artista> listarArtistas() throws SQLException {
        return artistas.listar();

    }
    public List<Lanzamiento> listarLanzamientos() throws SQLException {
        return lanzamientos.listar();
    }

    public List<Contrato> listarContratos() throws SQLException {
        return contratos.listar();
    }
}
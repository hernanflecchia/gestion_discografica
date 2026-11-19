package gestores;

import enums.EstadoArtista;
import modelo.Album;
import modelo.Artista;
import modelo.Contrato;
import modelo.Lanzamiento;

import repositorio.Repositorio;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Sello {
    private String nombre;
    private GestorArtista artistas;
    private GestorContrato contratos;
    private GestorLanzamiento lanzamientos;

    public String getNombre() {
        return nombre;
    }

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

    public List<Artista> listarArtistas() throws SQLException {
        return artistas.listar();

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

    public List<Contrato> listarContratos() throws SQLException {
        return contratos.listar();
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

    public List<Lanzamiento> listarLanzamientos() throws SQLException {
        return lanzamientos.listar();
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
            if (albumesEnGira.existe(lanzamiento.getId())) {
                lanzamiento = albumesEnGira.buscarPorId(lanzamiento.getId());   // la versión con shows
            }
            importe += lanzamiento.calcularIngresos();
        }
        return importe * contrato.getPorcentajeRegalias() / 100;
    }

    // Tours
    private final Repositorio<Album> albumesEnGira = new Repositorio<>();

    // Devuelve el álbum en memoria; si todavía no está, lo trae de la BD y lo guarda
    private Album obtenerAlbum(int lanzamientoId) throws SQLException {
        if (albumesEnGira.existe(lanzamientoId)) {
            return albumesEnGira.buscarPorId(lanzamientoId);
        }
        Lanzamiento l = lanzamientos.buscar(lanzamientoId);
        if (!(l instanceof Album album)) {
            throw new IllegalArgumentException("Solo los álbumes pueden salir de gira.");   // RN-T01
        }
        albumesEnGira.agregar(album);
        return album;
    }

    public void agendarShow(int albumId, LocalDate fecha, String ciudad) throws SQLException {
        obtenerAlbum(albumId).agendarTour(fecha, ciudad);
    }

    public void cancelarShow(int albumId, LocalDate fecha) {
        if (!albumesEnGira.existe(albumId)) {
            throw new IllegalArgumentException("El lanzamiento " + albumId + " no tiene shows agendados.");
        }
        albumesEnGira.buscarPorId(albumId).cancelarTour(fecha);
    }

    public Map<LocalDate, String> verAgenda(int albumId) throws SQLException {
        return obtenerAlbum(albumId).getAgendaTour();
    }

    public List<Album> listarAlbumesEnGira() {
        List<Album> enGira = new ArrayList<>();
        for (Album album : albumesEnGira.listar()) {
            if (!album.getAgendaTour().isEmpty()) {
                enGira.add(album);
            }
        }
        return enGira;
    }
}
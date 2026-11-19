package gestores;

import enums.EstadoArtista;
import excepciones.SinContratoVigenteException;
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

/**
 * Fachada del sistema: es la única clase con la que hablan los menús.
 * Resuelve las operaciones que involucran a varias entidades
 * (firmar contratos, publicar lanzamientos, calcular regalías y manejar giras)
 * y delega el resto en los gestores.
 */
public class Sello {
    private String nombre;
    private GestorArtista artistas;
    private GestorContrato contratos;
    private GestorLanzamiento lanzamientos;

    public String getNombre() {
        return nombre;
    }

    // Recibe los gestores ya creados (los arma Main).
    public Sello(String nombre, GestorArtista artistas, GestorContrato contratos, GestorLanzamiento lanzamientos) {
        this.nombre = nombre;
        this.artistas = artistas;
        this.lanzamientos = lanzamientos;
        this.contratos = contratos;
    }

    // Artistas
    // Da de alta un artista (el gestor controla que el nombre no esté repetido).
    public void altaArtista(Artista artista) throws Exception {
        artistas.alta(artista);
    }

    public Artista buscarArtista(int id) throws SQLException {
        return artistas.buscar(id);
    }

    // Baja lógica: el artista pasa a INACTIVO.
    public void bajaArtista(int id) throws SQLException {
        artistas.baja(id);
    }

    public List<Artista> listarArtistas() throws SQLException {
        return artistas.listar();

    }

    // Contratos
    // Avisa si el artista no está activo. Se usa al firmar contratos y al publicar.
    private void verificarActivo(Artista artista) {
        if (artista.getEstado() != EstadoArtista.ACTIVO) {
            throw new IllegalStateException("El artista " + artista.getNombre() + " no está activo.");
        }
    }

    // Firma un contrato nuevo para un artista activo.
    public void firmarContrato(int artistaId, LocalDate inicio, LocalDate fin, double porcentaje) throws Exception {
        Artista artista = artistas.buscar(artistaId);
        verificarActivo(artista);
        Contrato contrato = new Contrato(artista, inicio, fin, porcentaje);
        contratos.alta(contrato);
    }

    // Rescinde un contrato y guarda el cambio.
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
    // solo publica un artista activo con contrato vigente
    public void verificarPuedePublicar(Artista artista) throws Exception {
        verificarActivo(artista);
        if (contratos.buscarVigente(artista) == null) {
            throw new SinContratoVigenteException(artista);
        }
    }

    // Publica un lanzamiento si el artista puede hacerlo.
    // El gestor además valida las reglas del tipo (single o álbum).
    public void publicarLanzamiento(Lanzamiento lanzamiento) throws Exception {
        verificarPuedePublicar(lanzamiento.getArtista());
        lanzamientos.alta(lanzamiento);
    }

    // Suma una venta al lanzamiento y la guarda.
    // Si el álbum está de gira, se usa la copia en memoria para que las dos queden iguales.
    public void registrarVenta(int lanzamientoId, int cantidad) throws Exception {
        Lanzamiento lanzamiento;
        if (albumesEnGira.existe(lanzamientoId)) {
            lanzamiento = albumesEnGira.buscarPorId(lanzamientoId);   // la versión con shows
        } else {
            lanzamiento = lanzamientos.buscar(lanzamientoId);
        }
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
    // Suma los ingresos de todos los lanzamientos del artista y aplica
    // el porcentaje de su contrato vigente.
    public double calcularRegalias(int artistaId) throws Exception {
        Artista artista = artistas.buscar(artistaId);
        Contrato contrato = contratos.buscarVigente(artista);
        if (contrato == null) {
            throw new SinContratoVigenteException(artista);
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
    // Los shows no se guardan en la base: los álbumes de gira se guardan en memoria.
    private final Repositorio<Album> albumesEnGira = new Repositorio<>();

    // Avisa si el lanzamiento no existe o no es un álbum (solo los álbumes salen de gira).
    public void verificarEsAlbum(int albumId) throws SQLException {
        obtenerAlbum(albumId);
    }

    // Avisa si el álbum no tiene shows agendados.
    public void verificarTieneShows(int albumId) {
        if (!albumesEnGira.existe(albumId) || albumesEnGira.buscarPorId(albumId).getAgendaTour().isEmpty()) {
            throw new IllegalArgumentException("El lanzamiento " + albumId + " no tiene shows agendados.");
        }
    }

    // Devuelve el álbum en memoria; si todavía no está, lo trae de la BD y lo guarda
    private Album obtenerAlbum(int lanzamientoId) throws SQLException {
        if (albumesEnGira.existe(lanzamientoId)) {
            return albumesEnGira.buscarPorId(lanzamientoId);
        }
        Lanzamiento l = lanzamientos.buscar(lanzamientoId);
        if (!(l instanceof Album album)) {
            throw new IllegalArgumentException("Solo los álbumes pueden salir de gira.");
        }
        albumesEnGira.agregar(album);
        return album;
    }

    // Agenda un show para el álbum.
    public void agendarShow(int albumId, LocalDate fecha, String ciudad) throws SQLException {
        obtenerAlbum(albumId).agendarTour(fecha, ciudad);
    }

    // Cancela un show; el álbum tiene que tener shows agendados.
    public void cancelarShow(int albumId, LocalDate fecha) {
        if (!albumesEnGira.existe(albumId)) {
            throw new IllegalArgumentException("El lanzamiento " + albumId + " no tiene shows agendados.");
        }
        albumesEnGira.buscarPorId(albumId).cancelarTour(fecha);
    }

    // Devuelve los shows agendados del álbum, ordenados por fecha.
    public Map<LocalDate, String> verAgenda(int albumId) throws SQLException {
        return obtenerAlbum(albumId).getAgendaTour();
    }

    // Devuelve los álbumes que tienen al menos un show agendado.
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
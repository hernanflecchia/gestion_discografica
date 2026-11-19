package menu;

import enums.EstadoArtista;
import enums.Genero;
import excepciones.LanzamientoInvalidoException;
import gestores.Sello;
import modelo.Album;
import modelo.Artista;
import modelo.Cancion;
import modelo.Lanzamiento;
import modelo.Single;
import util.ConsolaUtil;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

//menu maneja singles y albums (llamando a Sello)

/**
 * Menú de lanzamientos: publicar singles y álbumes, listarlos,
 * buscarlos y registrar ventas.
 */
public class MenuLanzamientos extends Menu {

    protected MenuLanzamientos(Sello sello) {
        super(sello);
    }

    @Override
    protected String titulo() {
        return "Menu Lanzamientos";
    }

    @Override
    protected String[] opciones() {
        return new String[]{
                "Publicar single.",
                "Publicar album.",
                "Listar.",
                "Buscar por id",
                "Registrar venta"
        };
    }

    // Llama al método que corresponde a cada opción.
    @Override
    protected void ejecutar(int opcion) throws Exception {
        switch (opcion) {
            case 1:
                publicarSingle();
                break;
            case 2:
                publicarAlbum();
                break;
            case 3:
                mostrarLanzamientos();
                break;
            case 4:
                buscar();
                break;
            case 5:
                registrarVenta();
                break;
        }
    }

    // ---------------------------------------------------------------- publicar

    // Elige el artista, verifica que pueda publicar, pide los datos
    // del single y su canción, y lo publica.
    private void publicarSingle() throws Exception {
        System.out.println("\n========== PUBLICAR SINGLE ==========");

        Artista artista = elegirArtista();
        if (artista == null) {
            return;
        }

        // avisa antes de pedir los datos si el artista no puede publicar
        sello.verificarPuedePublicar(artista);

        String titulo = ConsolaUtil.leerTexto("Título del single: ");
        LocalDate fecha = ConsolaUtil.leerFecha("Fecha de lanzamiento");
        double precio = ConsolaUtil.leerDecimal("Precio: ");
        Genero genero = elegirGenero();
        String tituloCancion = ConsolaUtil.leerTexto("Título de la canción: ");
        int duracion = leerDuracion("Duración de la canción");

        try {
            // id se asigna en bdd al guardar
            Single single = new Single(0, titulo, fecha, precio, genero, artista,
                    new Cancion(tituloCancion, duracion));
            sello.publicarLanzamiento(single);
            System.out.println("\nSingle publicado:\n" + single);
        } catch (IllegalArgumentException | IllegalStateException | LanzamientoInvalidoException e) {
            System.out.println("\nNo se pudo publicar el single: " + e.getMessage());
        }
    }

    // Elige el artista, verifica que pueda publicar, pide los datos
    // del álbum y sus canciones (hasta que se deja un título vacío), y lo publica.
    private void publicarAlbum() throws Exception {
        System.out.println("\n========== PUBLICAR ALBUM ==========");

        Artista artista = elegirArtista();

        if (artista == null) {
            return;
        }

        // avisa antes de pedir los datos si el artista no puede publicar
        sello.verificarPuedePublicar(artista);

        String titulo = ConsolaUtil.leerTexto("Título del álbum: ");
        LocalDate fecha = ConsolaUtil.leerFecha("Fecha de lanzamiento");
        double precio = ConsolaUtil.leerDecimal("Precio: ");
        Genero genero = elegirGenero();

        try {
            Album album = new Album(0, titulo, fecha, precio, genero, artista);

            System.out.println("\nCargá las canciones. Dejá el título vacío para terminar.");
            int numero = 1;
            while (true) {
                String tituloCancion = ConsolaUtil.leerTextoOpcional(
                        "Canción " + numero + " (Enter para terminar): ");
                if (tituloCancion == null) {
                    break;
                }
                int duracion = leerDuracion("Duración");
                album.agregarCancion(new Cancion(tituloCancion, duracion));
                numero++;
            }

            sello.publicarLanzamiento(album);
            System.out.println("\nÁlbum publicado:\n" + album);
        } catch (IllegalArgumentException | IllegalStateException | LanzamientoInvalidoException e) {
            System.out.println("\nNo se pudo publicar el álbum: " + e.getMessage());
        }
    }

    //  listar y buscar lanzamientos

    // Muestra todos los lanzamientos.
    private void mostrarLanzamientos() throws SQLException {
        System.out.println("\n========== LANZAMIENTOS ==========");
        List<Lanzamiento> lanzamientos = sello.listarLanzamientos();
        if (lanzamientos.isEmpty()) {
            System.out.println("No hay lanzamientos cargados.");
            return;
        }
        System.out.println("Cantidad de lanzamientos: " + lanzamientos.size() + "\n");
        for (Lanzamiento lanzamiento : lanzamientos) {
            System.out.println(lanzamiento);
        }
    }

    // Pide un id y muestra el lanzamiento; si es un álbum, también sus canciones.
    private void buscar() throws SQLException {
        System.out.println("\n========== BUSCAR LANZAMIENTO ==========");
        int id = ConsolaUtil.leerEntero("ID del lanzamiento: ");

        Lanzamiento lanzamiento = sello.buscarLanzamiento(id);

        System.out.println("\n" + lanzamiento);
        if (lanzamiento instanceof Album album) {
            System.out.println("Canciones:");
            int numero = 1;
            for (Cancion cancion : album.getCanciones()) {
                System.out.println("  " + numero + ". " + cancion);
                numero++;
            }
        }
        System.out.println("Ingresos: $" + lanzamiento.calcularIngresos());
    }

    // venta de lanzamientos

    // Pide el lanzamiento y la cantidad vendida, registra la venta y muestra el total.
    private void registrarVenta() throws Exception {
        System.out.println("\n========== REGISTRAR VENTA ==========");
        int id = ConsolaUtil.leerEntero("ID del lanzamiento: ");
        int cantidad = ConsolaUtil.leerEntero("Unidades vendidas a registrar: ");

        try {
            sello.registrarVenta(id, cantidad);
            Lanzamiento actualizado = sello.buscarLanzamiento(id);
            System.out.println("Venta registrada. Total de unidades vendidas de \""
                    + actualizado.getTitulo() + "\": " + actualizado.getUnidadesVendidas());
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo registrar la venta: " + e.getMessage());
        }
    }


    // Muestra los artistas activos y pide elegir uno por id.
    // Devuelve null (y avisa) si no hay activos o si el id no corresponde a uno activo.
    private Artista elegirArtista() throws SQLException {
        List<Artista> artistas = sello.listarArtistas();

        System.out.println("Artistas activos:");
        boolean hayActivos = false;
        for (Artista artista : artistas) {
            if (artista.getEstado() == EstadoArtista.ACTIVO) {
                System.out.println("  " + artista.getId() + ".- " + artista.getNombre());
                hayActivos = true;
            }
        }
        if (!hayActivos) {
            System.out.println("  (no hay artistas activos; cargá uno desde el menú de artistas)");
            return null;
        }

        int id = ConsolaUtil.leerEntero("ID del artista: ");
        for (Artista artista : artistas) {
            if (artista.getId() == id && artista.getEstado() == EstadoArtista.ACTIVO) {
                return artista;
            }
        }
        System.out.println("No hay un artista activo con el ID " + id + ".");
        return null;
    }

    // Muestra los géneros numerados y devuelve el elegido.
    private Genero elegirGenero() {
        Genero[] generos = Genero.values();
        System.out.println("Géneros disponibles:");
        for (int i = 0; i < generos.length; i++) {
            System.out.println("  " + (i + 1) + ".- " + generos[i]);
        }
        int opcion = ConsolaUtil.leerOpcion(1, generos.length);
        return generos[opcion - 1];
    }

    // El usuario escribe "m:ss" (ej: 3:30) y se devuelven los segundos (210),
    // que es como guarda la duración la clase Cancion.
    private int leerDuracion(String mensaje) {
        while (true) {
            String texto = ConsolaUtil.leerTexto(mensaje + " (m:ss, ej: 3:30): ");
            String[] partes = texto.split(":");
            try {
                if (partes.length == 2) {
                    int minutos = Integer.parseInt(partes[0].trim());
                    int segundos = Integer.parseInt(partes[1].trim());
                    if (minutos >= 0 && segundos >= 0 && segundos <= 59
                            && (minutos > 0 || segundos > 0)) {
                        return minutos * 60 + segundos;
                    }
                }
            } catch (NumberFormatException e) {
                // si no es un número, se muestra el aviso de abajo y se vuelve a pedir
            }
            System.out.println("Formato inválido. Usá minutos:segundos, por ejemplo 3:30 "
                    + "(los segundos van de 00 a 59).");
        }
    }
}
package menu;

import enums.EstadoArtista;
import excepciones.ArtistaDuplicadoException;
import gestores.Sello;
import modelo.Artista;
import modelo.Banda;
import modelo.Solista;
import util.ConsolaUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
Menú de artistas: solistas y bandas
*/

public class MenuArtistas extends Menu {

    protected MenuArtistas(Sello sello) {
        super(sello);
    }

    @Override
    protected String titulo() {
        return "Menu Artistas";
    }

    @Override
    protected String[] opciones() {
        return new String[]{
                "Alta solista",
                "Alta banda",
                "Listar artistas",
                "Buscar por id",
                "Dar de baja"
        };
    }

    @Override
    protected void ejecutar(int opcion) throws Exception {
        switch (opcion) {
            case 1 -> altaSolista();
            case 2 -> altaBanda();
            case 3 -> listar();
            case 4 -> buscar();
            case 5 -> darDeBaja();
            default -> System.out.println("La opción seleccionada no es válida.");
        }
    }

    // alta solista
    
    private void altaSolista() throws Exception {
        System.out.println("\n========== ALTA DE SOLISTA ==========");
        System.out.println("Ingresá los datos solicitados.");
        System.out.println("Los campos no pueden quedar vacíos.\n");

        String nombre = ConsolaUtil.leerTexto("Nombre artístico del solista: ");
        String pais = ConsolaUtil.leerTexto("País de origen: ");
        String nombreReal = ConsolaUtil.leerTexto("Nombre real del solista: ");

        try {
            Solista solista = new Solista(nombre, pais, nombreReal);
            
            sello.altaArtista(solista);

            System.out.println("\nSolista guardado correctamente:");
            System.out.println(solista);
        } catch (IllegalArgumentException | ArtistaDuplicadoException e) {
            System.out.println("\nNo se pudo crear el solista: " + e.getMessage());
        }
    }

  
    private void altaBanda() throws Exception {
        System.out.println("\n========== ALTA DE BANDA ==========");
        System.out.println("Ingresá los datos generales de la banda.");
        System.out.println("Luego se solicitará el nombre de cada integrante.\n");

        String nombre = ConsolaUtil.leerTexto("Nombre de la banda: ");
        String pais = ConsolaUtil.leerTexto("País de origen: ");
        
        List<String> integrantes = new ArrayList<>();
        System.out.println("\nCargá los integrantes. Dejá el nombre vacío para terminar.");
        int numero = 1;
        while (true) {
            String integrante = ConsolaUtil.leerTextoOpcional(
                    "Integrante " + numero + " (Enter para terminar): ");
            if (integrante == null) {
                break;
            }
            integrantes.add(integrante);
            numero++;
        }

        try {
            Banda banda = new Banda(nombre, pais, integrantes);

            sello.altaArtista(banda);

            System.out.println("\nBanda guardada correctamente:");
            System.out.println(banda);
        } catch (IllegalArgumentException | ArtistaDuplicadoException e) {
            System.out.println("\nNo se pudo crear la banda: " + e.getMessage());
        }
    }

    //  listar y buscar
    
    private void listar() throws SQLException {
        System.out.println("\n========== LISTADO DE ARTISTAS ==========");

        List<Artista> lista = sello.listarArtistas();

        if (lista.isEmpty()) {
            System.out.println("No hay artistas registrados para mostrar.");
            return;
        }

        System.out.println("Cantidad de artistas encontrados: " + lista.size());
        
        for (Artista artista : lista) {
            System.out.println("\n----------------------------------------");
            System.out.println(artista);
        }

        System.out.println("\nFin del listado.");
    }
    
    private void buscar() throws SQLException {
        System.out.println("\n========== BUSCAR ARTISTA ==========");
        System.out.println("Podés ver los IDs en la opción Listar artistas.\n");

        int id = ConsolaUtil.leerEntero("ID del artista: ");
        Artista artista = sello.buscarArtista(id);

        System.out.println("\nArtista encontrado:");
        System.out.println("----------------------------------------");
        System.out.println(artista);
        System.out.println("----------------------------------------");
    }

    /**
     * BAJA ARTISTA: La baja es lógica: el artista pasa a INACTIVO y
     * conserva sus contratos y lanzamientos (son historial), pero ya no puede
     * firmar contratos ni publicar lanzamientos.
     * @throws SQLException si no existe el artista o falla la operación
     */

    private void darDeBaja() throws SQLException {
        System.out.println("\n========== BAJA DE ARTISTA ==========");

        int id = ConsolaUtil.leerEntero("ID del artista a dar de baja: ");
        Artista artista = sello.buscarArtista(id);

        if (artista.getEstado() == EstadoArtista.INACTIVO) {
            System.out.println("\n" + artista.getNombre() + " ya está inactivo.");
            return;
        }

        System.out.println("\n" + artista);
        String respuesta = ConsolaUtil.leerTexto("\n¿Confirmás la baja? (s/n): ");
        if (!respuesta.equalsIgnoreCase("s")) {
            System.out.println("Baja cancelada. No se modificó nada.");
            return;
        }

        sello.bajaArtista(id);
        System.out.println(artista.getNombre() + " fue dado de baja (pasó a INACTIVO).");
    }

}

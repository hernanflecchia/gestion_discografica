/* ARME MENU NUEVO
  ANTES ESTABA DE ESTA FORMA

  package menu;

import gestores.Sello;
import modelo.Artista;
import java.util.List;

import java.sql.SQLException;

public class MenuArtistas extends Menu{
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
    protected void ejecutar(int opcion) throws SQLException {
        switch (opcion) {
            //case 1 -> altaSolista();
            //case 2 -> altaBanda();
            case 3 -> listar();
            //case 4 -> buscar();
            //case 5 -> darDeBaja();
        }
    }

    private void listar() throws SQLException {
        List<Artista> lista = sello.listarArtistas();
        if (lista.isEmpty()) {
            System.out.println("No hay artistas cargados.");
            return;
        }
        for (Artista a : lista) {
            System.out.println(a);
        }
    }
}*/

//NUEVO


package menu;

import gestores.Sello;
import modelo.Artista;
import modelo.Banda;
import modelo.Solista;
import util.ConsolaUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
  Gestiona las operaciones disponibles desde el menú de artistas.

  Permite cargar solistas y bandas, listar los artistas registrados,
  buscar un artista por su ID y acceder a la opción de baja.

  Las operaciones de persistencia se realizan a través de Sello,
  respetando la separación entre la interfaz y la lógica del negocio.
 */
    public class MenuArtistas extends Menu {

    /*
      Construye el menú de artistas utilizando el sello recibido.

      @param sello objeto Sello que recibe el menú para acceder a las operaciones disponibles.
     */
    protected MenuArtistas(Sello sello) {
        super(sello);
    }

    /*
      Devuelve el título que se muestra al ingresar al menú.

      @return título del menú
     */
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
    protected void ejecutar(int opcion) throws SQLException {
        switch (opcion) {
            case 1 -> altaSolista();
            case 2 -> altaBanda();
            case 3 -> listar();
            case 4 -> buscar();
            case 5 -> darDeBaja();
            default -> System.out.println(
                    "La opción seleccionada no es válida."
            );
        }
    }

    /* Solicita los datos de un solista y construye el objeto.

Importante: en esta versión, el objeto se valida y se muestra, pero todavía no se guarda en la base de datos.
     */
    private void altaSolista() {
        System.out.println("\n========== ALTA DE SOLISTA ==========");
        System.out.println("Ingresá los datos solicitados.");
        System.out.println("Los campos no pueden quedar vacíos.\n");

        // Se solicitan los datos utilizando la utilidad de consola.
        String nombre = ConsolaUtil.leerTexto(
                "Nombre artístico del solista: "
        );

        String pais = ConsolaUtil.leerTexto(
                "País de origen: "
        );

        String nombreReal = ConsolaUtil.leerTexto(
                "Nombre real del solista: "
        );

        try {
            // Se crea el objeto con los datos ingresados.
            Solista solista = new Solista(
                    nombre, pais, nombreReal
            );

            // Se muestran los datos para que el usuario pueda revisarlos.
            System.out.println("\n--- Resumen de los datos ingresados ---");
            System.out.println(solista);

            System.out.println(
                    "\nLa información se validó correctamente."
            );
            System.out.println(
                    "El alta todavía no está conectada con Sello."
            );
            System.out.println(
                    "El solista no fue guardado en la base de datos."
            );

        } catch (IllegalArgumentException e) {
            // Se informa el error de validación sin cerrar el menú.
            System.out.println(
                    "\nNo se pudo crear el solista: " + e.getMessage()
            );
        }
    }

    /* Solicita los datos generales de una banda y sus integrantes.

      Cada integrante se incorpora a una lista que luego recibe el constructor de Banda.

      En esta versión, la banda se construye y se muestra,pero todavía no se persiste en la base de datos.
     */
    private void altaBanda() {
        System.out.println("\n========== ALTA DE BANDA ==========");
        System.out.println("Ingresá los datos generales de la banda.");
        System.out.println(
                "Luego se solicitará el nombre de cada integrante.\n"
        );

        String nombre = ConsolaUtil.leerTexto(
                "Nombre de la banda: "
        );

        String pais = ConsolaUtil.leerTexto(
                "País de origen: "
        );

        // La banda debe tener al menos un integrante.
        System.out.println("\n¿Cuántos integrantes tiene la banda?");
        System.out.println("Ingresá un número entre 1 y 10.");
        System.out.print("Cantidad de integrantes: ");

        int cantidad = ConsolaUtil.leerOpcion(1, 10);

        // Se almacenan los nombres antes de construir la banda.
        List<String> integrantes = new ArrayList<>();

        for (int i = 1; i <= cantidad; i++) {
            System.out.println(
                    "\nDatos del integrante " + i + " de " + cantidad
            );

            String integrante = ConsolaUtil.leerTexto(
                    "Nombre y apellido: "
            );

            integrantes.add(integrante);
        }

        try {
            // El constructor de Banda valida la lista de integrantes.
            Banda banda = new Banda(
                    nombre, pais, integrantes
            );

            System.out.println("\n--- Resumen de los datos ingresados ---");
            System.out.println(banda);

            System.out.println(
                    "\nLa información se validó correctamente."
            );
            System.out.println(
                    "El alta todavía no está conectada con Sello."
            );
            System.out.println(
                    "La banda no fue guardada en la base de datos."
            );

        } catch (IllegalArgumentException e) {
            // Se muestra el motivo por el que no se pudo crear la banda.
            System.out.println(
                    "\nNo se pudo crear la banda: " + e.getMessage()
            );
        }
    }

    /* Consulta los artistas registrados y muestra sus datos.

      @throws SQLException si falla la consulta de artistas
     */
    private void listar() throws SQLException {
        System.out.println("\n========== LISTADO DE ARTISTAS ==========");
        System.out.println("Consultando los artistas registrados...\n");

        // Sello delega la consulta en el gestor correspondiente.
        List<Artista> lista = sello.listarArtistas();

        // Se informa si no existen registros para mostrar.
        if (lista.isEmpty()) {
            System.out.println(
                    "No hay artistas registrados para mostrar."
            );
            return;
        }

        System.out.println(
                "Cantidad de artistas encontrados: " + lista.size()
        );

        // Se muestra cada artista utilizando su implementación de toString().
        for (Artista artista : lista) {
            System.out.println("\n----------------------------------------");
            System.out.println(artista);
        }

        System.out.println("\nFin del listado.");
    }

    /* Busca un artista por ID recorriendo la lista obtenida desde Sello.

      Esta implementación evita depender de un metodo de busquedaindividual que todavía no está expuesto en Sello.
      @throws SQLException si falla la consulta de artistas
     */
    private void buscar() throws SQLException {
        System.out.println("\n========== BUSCAR ARTISTA ==========");
        System.out.println(
                "Ingresá el ID del artista que querés consultar."
        );
        System.out.println(
                "Podés encontrar el ID en la opción Listar artistas.\n"
        );

        int id = ConsolaUtil.leerOpcion(1, Integer.MAX_VALUE);

        // Se obtiene la lista y se busca el ID solicitado.
        List<Artista> artistas = sello.listarArtistas();

        for (Artista artista : artistas) {
            if (artista.getId() == id) {
                System.out.println("\nArtista encontrado:");
                System.out.println("----------------------------------------");
                System.out.println(artista);
                System.out.println("----------------------------------------");
                return;
            }
        }

        // Si el recorrido termina sin coincidencias, el ID no existe.
        System.out.println(
                "\nNo se encontró ningún artista con el ID " + id + "."
        );
        System.out.println(
                "Verificá el número e intentá nuevamente."
        );
    }

    /* Presenta la opción de baja de un artista.

     La operación queda pendiente de integración con Sello.

      No se elimina ni modifica ningún registro en esta versión.

      @throws SQLException se conserva para mantener la firma compatible con ejecutar()
     */
    private void darDeBaja() throws SQLException {
        System.out.println("\n========== BAJA DE ARTISTA ==========");
        System.out.println(
                "Esta opción permitirá dar de baja un artista registrado."
        );
        System.out.println(
                "La operación todavía no está conectada con Sello."
        );
        System.out.println(
                "No se realizó ninguna modificación en la base de datos."
        );
    }
}


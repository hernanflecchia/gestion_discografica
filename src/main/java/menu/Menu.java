package menu;

import gestores.Sello;
import util.ConsolaUtil;

import java.sql.SQLException;

/**
 * Clase base de todos los menús de consola.
 * Arma el ciclo que se repite en todos: mostrar el título y las opciones,
 * leer la opción elegida y ejecutarla, hasta que el usuario elige 0.
 * Cada submenú solo define su título, sus opciones y qué hace cada una.
 */
public abstract class Menu {
    protected  final Sello sello;   // los menús solo hablan con el Sello

    protected Menu(Sello sello) {
        this.sello = sello;
    }

    // Título que se muestra arriba del menú.
    protected abstract String titulo();
    // Opciones del menú (se numeran desde 1; el 0 es para salir).
    protected abstract String[] opciones();
    // Qué hacer con la opción elegida.
    protected abstract void ejecutar(int opcion) throws Exception;

    // Si después de cada opción se espera un Enter (el menú principal no lo hace).
    protected boolean pausarDespuesDeEjecutar() {
        return true;
    }
    // Texto de la opción 0.
    protected String textoSalida() {
        return "Volver";
    }

    // Muestra el menú y lo repite hasta que se elija 0.
    // Si una opción falla, se muestra el error y el programa sigue funcionando.
    public final void mostrar() {
        int opcion;
        do {
            ConsolaUtil.limpiarConsola();
            System.out.println("\n=== " + titulo() + " ===");
            String[] ops = opciones();
            for (int i = 0; i < ops.length; i++) {
                System.out.println((i + 1) + ".- " + ops[i]);
            }
            System.out.println("0.- " + textoSalida());

            opcion = ConsolaUtil.leerOpcion(0, ops.length);
            if (opcion != 0) {
                try {
                    ejecutar(opcion);
                } catch (Exception e) {
                    // atrapa cualquier error de las opciones y muestra el mensaje
                    System.out.println("Error: " + e.getMessage());
                }
                if (pausarDespuesDeEjecutar()) ConsolaUtil.pausar();
            }
        } while (opcion != 0);
    }
}

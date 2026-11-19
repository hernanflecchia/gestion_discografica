package menu;

import gestores.Sello;

/**
 * Menú con el que arranca el programa. Lleva a cada submenú.
 */
public class MenuPrincipal extends Menu {
    public MenuPrincipal(Sello sello) {
        super(sello);
    }

    // Al volver de un submenú no hace falta pausar.
    @Override
    protected boolean pausarDespuesDeEjecutar() {
        return false;
    }

    // En el menú principal, la opción 0 cierra el programa.
    @Override
    protected String textoSalida() {
        return "Salir";
    }

    @Override
    protected String titulo() {
        return "Menu Principal";
    }

    @Override
    protected String[] opciones() {
        return new String[]{"Artistas.", "Lanzamientos.", "Contratos.", "Tours."};
    }

    // Abre el submenú elegido.
    @Override
    protected void ejecutar(int opcion) throws Exception {
        switch (opcion) {
            case 1 -> new MenuArtistas(sello).mostrar();
            case 2 -> new MenuLanzamientos(sello).mostrar();
            case 3 -> new MenuContratos(sello).mostrar();
            case 4 -> new MenuTours(sello).mostrar();
        }
    }
}

package menu;

import gestores.Sello;

public class MenuPrincipal extends Menu {
    public MenuPrincipal(Sello sello) {
        super(sello);
    }

    @Override
    protected String titulo() {
        return "Menu Principal";
    }

    @Override
    protected String[] opciones() {
        return new String[]{"Artistas.", "Lanzamientos.", "Contratos.", "Tours."};
    }

    @Override
    protected void ejecutar(int opcion) {
        switch (opcion) {
            case 1 -> new MenuArtistas(sello).mostrar();
            case 2 -> new MenuLanzamientos(sello).mostrar();
            case 3 -> new MenuContratos(sello).mostrar();
            case 4 -> new MenuTours(sello).mostrar();
            //case 4 -> mostrarRegalias();   // método privado de esta clase
        }
    }
}

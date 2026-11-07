package menu;

import gestores.Sello;

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
        return new String[]{"Interpretes.", "Bandas.", "Solistas."};
    }

    @Override
    protected void ejecutar(int opcion) {
        switch (opcion) {
            case 1 -> new MenuInterpretes(sello).mostrar();
        }
    }
}

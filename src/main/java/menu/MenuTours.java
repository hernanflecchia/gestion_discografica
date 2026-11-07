package menu;

import gestores.Sello;

public class MenuTours extends Menu {
    protected MenuTours(Sello sello) {
        super(sello);
    }

    @Override
    protected String titulo() {
        return "Menu Tours";
    }

    @Override
    protected String[] opciones() {
        return new String[]{"Crear.", "Listar.", "Actualizar."};
    }

    @Override
    protected void ejecutar(int opcion) {

    }
}

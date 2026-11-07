package menu;

import gestores.Sello;

public class MenuLanzamientos extends Menu{
    protected MenuLanzamientos(Sello sello) {
        super(sello);
    }

    @Override
    protected boolean pausarDespuesDeEjecutar() {
        return false;
    }

    @Override
    protected String titulo() {
        return "Menu Lanzamientos";
    }

    @Override
    protected String[] opciones() {
        return new String[]{"Canciones.", "Singles.", "Albunes."};
    }

    @Override
    protected void ejecutar(int opcion) {

    }
}

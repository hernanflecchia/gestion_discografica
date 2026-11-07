package menu;

import gestores.Sello;

public class MenuContratos extends Menu{
    protected MenuContratos(Sello sello) {
        super(sello);
    }

    @Override
    protected String titulo() {
        return "Menu Contratos";
    }

    @Override
    protected String[] opciones() {
        return new String[]{"Nuevo.", "Listar.", "Actualizar."};
    }

    @Override
    protected void ejecutar(int opcion) {

    }
}

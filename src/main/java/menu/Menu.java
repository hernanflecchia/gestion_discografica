package menu;

public abstract class Menu {
    protected abstract String titulo();
    protected abstract String[] opciones();
    protected abstract void ejecutar(int opcion);

    public void mostrar() {

    }
}

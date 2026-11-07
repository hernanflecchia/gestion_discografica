package menu;

import gestores.Sello;
import util.ConsolaUtil;

import java.sql.SQLException;

public abstract class Menu {
    protected  final Sello sello;

    protected Menu(Sello sello) {
        this.sello = sello;
    }

    protected abstract String titulo();
    protected abstract String[] opciones();
    protected abstract void ejecutar(int opcion) throws SQLException;

    protected boolean pausarDespuesDeEjecutar() {
        return true;
    }
    protected String textoSalida() {
        return "Volver";
    }

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
                    System.out.println("Error: " + e.getMessage());
                }
                if (pausarDespuesDeEjecutar()) ConsolaUtil.pausar();
            }
        } while (opcion != 0);
    }
}

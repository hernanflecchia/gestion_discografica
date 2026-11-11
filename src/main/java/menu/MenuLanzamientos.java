package menu;

import gestores.Sello;
import modelo.Lanzamiento;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuLanzamientos extends Menu{
    protected MenuLanzamientos(Sello sello) {
        super(sello);
    }

    @Override
    protected String titulo() {
        return "Menu Lanzamientos";
    }

    @Override
    protected String[] opciones() {
        return new String[]{
                "Publicar single.",
                "Publicar album.",
                "Listar.",
                "Buscar por id",
                "Registrar venta"
        };
    }

    @Override
    protected void ejecutar(int opcion) throws SQLException {
        switch (opcion) {
            //case 1 -> publicarSingle();
            //case 2 -> publicarAlbum();
            case 3 -> mostrarLanzamientos();
            //case 4 -> buscar();
            //case 5 -> registrarVenta();
        }
    }

    private void mostrarLanzamientos() throws SQLException {
        List<Lanzamiento> lanzamientos = sello.listarLanzamientos();
        if (lanzamientos.isEmpty()) {
            System.out.println("No hay lanzamientos cargados");
            return;
        }
        for (Lanzamiento lanzamiento : lanzamientos) {
            System.out.println(lanzamiento);
        }
    }
}

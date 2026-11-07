package menu;

import gestores.Sello;
import modelo.Artista;

import java.sql.SQLException;
import java.util.List;

public class MenuInterpretes extends Menu{
    protected MenuInterpretes(Sello sello) {
        super(sello);
    }

    @Override
    protected String titulo() {
        return "Menu Interpretes";
    }

    @Override
    protected String[] opciones() {
        return new String[]{"Cargar.", "Listar.", "Actualizar."};
    }

    @Override
    protected void ejecutar(int opcion) throws SQLException {
        switch (opcion) {
                case 2 -> listar();

        }
    }

    private void listar() throws SQLException {
        List<Artista> lista = sello.listarArtistas();
        if (lista.isEmpty()) {
            System.out.println("No hay artistas cargados.");
            return;
        }
        for (Artista a : lista) {
            System.out.println(a);
        }
    }
}

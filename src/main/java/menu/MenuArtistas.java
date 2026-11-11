package menu;

import gestores.Sello;
import modelo.Artista;
import java.util.List;

import java.sql.SQLException;

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
        return new String[]{
                "Alta solista",
                "Alta banda",
                "Listar",
                "Buscar por id",
                "Dar de baja"
        };
    }

    @Override
    protected void ejecutar(int opcion) throws SQLException {
        switch (opcion) {
            //case 1 -> altaSolista();
            //case 2 -> altaBanda();
            case 3 -> mostrarArtistas();
            //case 4 -> buscar();
            //case 5 -> darDeBaja();
        }
    }

    private void mostrarArtistas() throws SQLException {
        List<Artista> lista = sello.listarArtistas();
        if (lista.isEmpty()) {
            System.out.println("No hay artistas cargados.");
            return;
        }
        for (Artista artista : lista) {
            System.out.println(artista);
        }
    }
}

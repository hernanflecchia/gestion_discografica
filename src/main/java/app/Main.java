package app;

import dao.ArtistaDao;
import dao.ContratoDao;
import dao.LanzamientoDao;
import gestores.GestorArtista;
import gestores.GestorContrato;
import gestores.GestorLanzamiento;
import gestores.Sello;
import menu.MenuPrincipal;

/**
 * Punto de entrada del programa.
 * Es el único lugar donde se crean los objetos principales: arma los DAOs,
 * los gestores y el Sello, y se los pasa por constructor a quien los necesita.
 * Después muestra el menú principal.
 */
public class Main {
    public static void main(String[] args) {
        // Daos
        // ContratoDao y LanzamientoDao usan el mismo ArtistaDao para cargar los artistas.
        ArtistaDao artistaDao = new ArtistaDao();
        ContratoDao contratoDao = new ContratoDao(artistaDao);
        LanzamientoDao lanzamientoDao = new LanzamientoDao(artistaDao);
        // gestores
        GestorArtista gestorArtista = new GestorArtista(artistaDao);
        GestorContrato gestorContrato = new GestorContrato(contratoDao);
        GestorLanzamiento gestorLanzamiento = new GestorLanzamiento(lanzamientoDao);
        // sello
        Sello sello = new Sello("Discografica UTN", gestorArtista, gestorContrato, gestorLanzamiento);
        // menu principal
        new MenuPrincipal(sello).mostrar();
    }
}

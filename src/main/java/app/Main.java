package app;

import dao.ArtistaDao;
import dao.ContratoDao;
import dao.LanzamientoDao;
import gestores.GestorArtista;
import gestores.GestorContrato;
import gestores.GestorLanzamiento;
import gestores.Sello;
import menu.MenuPrincipal;

public class Main {
    public static void main(String[] args) {
        // Daos
        ArtistaDao artistaDao = new ArtistaDao();
        ContratoDao contratoDao = new ContratoDao();
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

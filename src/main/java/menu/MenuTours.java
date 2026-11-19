package menu;

import gestores.Sello;
import modelo.Album;
import util.ConsolaUtil;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
        return new String[] {
                "Agendar show.",
                "Cancelar show.",
                "Ver agenda de un album.",
                "Listar álbumes en gira."
        };
    }

    @Override
    protected void ejecutar(int opcion) throws Exception {
        switch (opcion) {
            case 1 -> agendarShow();
            case 2 -> cancelarShow();
            case 3 -> verAgendaAlbum();
            case 4 -> listarAlbumesEnGira();
        }
    }

    private void agendarShow() throws SQLException {
        System.out.println("\n========== AGENDAR SHOW ===========");
        int idAlbum = ConsolaUtil.leerEntero("Ingrese el id  del album: ");
        LocalDate fecha = ConsolaUtil.leerFecha("Ingrese la fecha del show");
        String ciudad = ConsolaUtil.leerTexto("Ingrese el nombre de la ciudad del show: ");
        sello.agendarShow(idAlbum, fecha, ciudad);
        System.out.println("Tour registrado exitosamente!");
    }

    private void cancelarShow() throws SQLException {
        System.out.println("\n========== CANCELAR SHOW ===========");
        int idAlbum = ConsolaUtil.leerEntero("Ingrese el id  del album: ");
        LocalDate fecha = ConsolaUtil.leerFecha("Ingrese fecha del show a cancelar");
        sello.cancelarShow(idAlbum, fecha);
        System.out.println("Show cancelado.");
    }

    private void listarAlbumesEnGira() {
        System.out.println("\n========== LISTAR ALBUMES EN GIRA ===========");
        List<Album> albumes = sello.listarAlbumesEnGira();
        if (albumes.isEmpty()) {
            System.out.println("No hay álbumes en gira.");
            return;
        }
        for (Album album : albumes) {
            System.out.println(album);      // el toString de Album ya muestra "shows: N"
        }
    }

    private void verAgendaAlbum() throws SQLException {
        System.out.println("\n========== VER AGENDA ALBUM ===========");
        int idAlbum = ConsolaUtil.leerEntero("Ingrese el id  del album: ");
        Map<LocalDate, String> agenda = sello.verAgenda(idAlbum);
        if (agenda.isEmpty()) {
            System.out.println("El álbum no tiene shows agendados.");
            return;
        }
        for (Map.Entry<LocalDate, String> show : agenda.entrySet()) {
            System.out.println(show.getKey() + " → " + show.getValue());   // sale ordenado por fecha (TreeMap)
        }
    }
}

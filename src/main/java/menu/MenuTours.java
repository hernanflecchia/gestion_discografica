package menu;

import gestores.Sello;
import modelo.Album;
import util.ConsolaUtil;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Menú de giras: agendar y cancelar shows de un álbum y ver las agendas.
 * Los shows se guardan en memoria mientras el programa está abierto.
 */
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

    // Llama al método que corresponde a cada opción.
    @Override
    protected void ejecutar(int opcion) throws Exception {
        switch (opcion) {
            case 1 -> agendarShow();
            case 2 -> cancelarShow();
            case 3 -> verAgendaAlbum();
            case 4 -> listarAlbumesEnGira();
        }
    }

    // Pide álbum, fecha y ciudad, y agenda el show.
    private void agendarShow() throws SQLException {
        System.out.println("\n========== AGENDAR SHOW ===========");
        int idAlbum = ConsolaUtil.leerEntero("Ingrese el id del álbum: ");
        sello.verificarEsAlbum(idAlbum);   // avisa antes de pedir fecha y ciudad
        LocalDate fecha = ConsolaUtil.leerFecha("Ingrese la fecha del show");
        String ciudad = ConsolaUtil.leerTexto("Ingrese el nombre de la ciudad del show: ");
        sello.agendarShow(idAlbum, fecha, ciudad);
        System.out.println("Show agendado exitosamente.");
    }

    // Pide álbum y fecha, y cancela ese show.
    private void cancelarShow() throws SQLException {
        System.out.println("\n========== CANCELAR SHOW ===========");
        int idAlbum = ConsolaUtil.leerEntero("Ingrese el id del álbum: ");
        sello.verificarTieneShows(idAlbum); // avisa antes de pedir fecha
        LocalDate fecha = ConsolaUtil.leerFecha("Ingrese fecha del show a cancelar");
        sello.cancelarShow(idAlbum, fecha);
        System.out.println("Show cancelado.");
    }

    // Muestra los álbumes que tienen al menos un show agendado.
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

    // Pide un álbum y muestra sus shows (fecha y ciudad).
    private void verAgendaAlbum() throws SQLException {
        System.out.println("\n========== VER AGENDA ALBUM ===========");
        int idAlbum = ConsolaUtil.leerEntero("Ingrese el id del álbum: ");
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

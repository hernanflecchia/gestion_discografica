package interfaces;

import java.time.LocalDate;
import java.util.Map;

// la gira requiere fecha y lugar

/**
 * Lo implementa todo lo que puede salir de gira (en este sistema, el Album).
 * Una gira es un conjunto de shows, cada uno con una fecha y una ciudad.
 */
public interface Tourable {

    // Agenda un show en la fecha y ciudad indicadas.
    void agendarTour(LocalDate fecha, String ciudad);

    // Cancela el show agendado en esa fecha.
    void cancelarTour(LocalDate fecha);

    // Devuelve los shows agendados: fecha -> ciudad.
    Map<LocalDate, String> getAgendaTour();
}

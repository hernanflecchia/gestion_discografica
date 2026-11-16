package interfaces;

import java.time.LocalDate;
import java.util.Map;

// la gira requiere fecha y lugar

public interface Tourable {

    void agendarTour(LocalDate fecha, String ciudad);

    void cancelarTour(LocalDate fecha);

    Map<LocalDate, String> getAgendaTour();
}

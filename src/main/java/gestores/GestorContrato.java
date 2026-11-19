package gestores;

import enums.EstadoContrato;
import interfaces.Dao;
import modelo.Artista;
import modelo.Contrato;

import java.sql.SQLException;
import java.util.List;

/**
 * Gestor de contratos. Agrega la regla de que un artista no puede tener
 * dos contratos que se superpongan en fechas, y permite buscar su contrato vigente.
 */
public class GestorContrato extends Gestor<Contrato> {
    public GestorContrato(Dao<Contrato> dao) {
        super(dao);
    }

    // Avisa si el artista ya tiene otro contrato (no rescindido) que pise esas fechas.
    @Override
    protected void validar(Contrato entidad) throws Exception {
        for (Contrato contrato: dao.buscarTodos()) {
            boolean mismoArtista = contrato.getArtista().getId() == entidad.getArtista().getId();
            boolean seSuperponen = !contrato.getFechaInicio().isAfter(entidad.getFechaFin())
                    && !entidad.getFechaInicio().isAfter(contrato.getFechaFin());
            if (mismoArtista && seSuperponen
                    && contrato.getEstado() != EstadoContrato.RESCINDIDO
                    && contrato.getId() != entidad.getId()) {
                throw new IllegalArgumentException("El artista ya tiene un contrato que se superpone con ese período.");
            }
        }
    }

    // Devuelve el contrato vigente del artista, o null si no tiene ninguno.
    public Contrato buscarVigente(Artista entidad) throws Exception {
        for (Contrato contrato: dao.buscarTodos()) {
            if (contrato.getArtista().getId() == entidad.getId() && contrato.estaVigente()) {
                return contrato;
            }
        }
        return  null;
    }
}

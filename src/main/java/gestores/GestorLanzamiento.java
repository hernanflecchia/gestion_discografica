package gestores;

import interfaces.Dao;
import modelo.Artista;
import modelo.Lanzamiento;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestorLanzamiento extends Gestor<Lanzamiento> {
    public GestorLanzamiento(Dao<Lanzamiento> dao) {
        super(dao);
    }

    @Override
    protected void validar(Lanzamiento entidad) throws Exception {
        entidad.validar();
    }

    public List<Lanzamiento> listarPorArtista(Artista artista) throws SQLException {
        List<Lanzamiento> lista = new ArrayList<>();
        for (Lanzamiento lanzamiento: dao.buscarTodos() ) {
            if (artista.getId() == lanzamiento.getArtista().getId()) {
                lista.add(lanzamiento);
            }
        }
        return lista;
    }
}

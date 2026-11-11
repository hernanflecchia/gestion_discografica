package menu;

import gestores.Sello;
import modelo.Artista;
import modelo.Contrato;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuContratos extends Menu{
    protected MenuContratos(Sello sello) {
        super(sello);
    }

    @Override
    protected String titulo() {
        return "Menu Contratos";
    }

    @Override
    protected String[] opciones() {
        return new String[]{"Nuevo.", "Listar.", "Actualizar."};
    }

    @Override
    protected void ejecutar(int opcion) throws SQLException {
        switch (opcion) {
            //case 1 -> nuevoContrato();
            case 2 -> mostrarContratos();
            //case 3 -> actualizarContrato();
        }
    }

    private void mostrarContratos() throws SQLException {
        List<Contrato> lista = sello.listarContratos();
        if (lista.isEmpty()) {
            System.out.println("No hay contratos cargados.");
            return;
        }
        for (Contrato contrato : lista) {
            System.out.println(contrato);
        }
    }
}

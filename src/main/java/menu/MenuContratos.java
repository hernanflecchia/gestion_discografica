
package menu;

import excepciones.ContratoVencidoException;
import gestores.Sello;
import modelo.Artista;
import modelo.Contrato;
import util.ConsolaUtil;

import java.sql.SQLException;
import java.time.LocalDate;
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
        return new String[]{"Nuevo.", "Listar.", " Rescindir.","Calcular Regalias."};
    }

    @Override
    protected void ejecutar(int opcion) throws Exception {
        switch (opcion) {
            case 1 -> nuevoContrato();
            case 2 -> mostrarContratos();
            case 3 -> rescindirContrato();
            case 4 -> calcularRegalias();
            default -> System.out.println(" La opcion seleccionada no es valida");
        }
    }

    private void nuevoContrato() throws Exception {
        System.out.println("\n========== NUEVO CONTRATO  ==========");

        System.out.println("Ingresá los datos solicitados.");

        System.out.println("Los campos no pueden quedar vacíos.\n");

        int idArtista = ConsolaUtil.leerEntero("Ingrese el id del artista ");

        LocalDate inicio = ConsolaUtil.leerFecha("Ingrese la fecha de inicio del contrato ");

        LocalDate fin = ConsolaUtil.leerFecha("Ingrese la fecha fin del contrato ");

        double regalias = ConsolaUtil.leerDecimal("Ingrese el porcentaje de regalias ");

        sello.firmarContrato(idArtista, inicio, fin, regalias);

        System.out.println("Contrato registrado exitosamente!");
    }


    private void mostrarContratos() throws SQLException {
        System.out.println("\n========== LISTADO DE CONTRATOS ==========");

        List<Contrato> lista = sello.listarContratos();

        if (lista.isEmpty()) {
            System.out.println("No hay contratos cargados.");
            return;
        }
        for (Contrato contrato : lista) {
            System.out.println(contrato);
        }
    }

    private void rescindirContrato() throws Exception {
        System.out.println("\n========== RESCINDIR CONTRATO ==========");

        int idContrato = ConsolaUtil.leerEntero("Ingrese el ID del contrato a rescindir: ");


        sello.rescindirContrato(idContrato);

        System.out.println("El contrato " + idContrato + "fue rescindido exitosamente");



    }
    private void calcularRegalias() throws Exception {
        System.out.println("\n========== CALCULAR REGALÍAS ==========");

        int idArtista = ConsolaUtil.leerEntero ("Ingrese el ID del artista: ");

        double regalias = sello.calcularRegalias(idArtista);

        System.out.printf("Total de regalías para el artista ID %d: $%.2f%n", idArtista,regalias);

    }



}







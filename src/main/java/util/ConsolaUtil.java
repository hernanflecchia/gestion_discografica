package util;

import java.io.IOException;
import java.util.Scanner;

public class ConsolaUtil {
    private static final Scanner scanner = new Scanner(System.in);

    private ConsolaUtil() {}

    public static void limpiarConsola() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                // Ejecuta el comando 'cls' interactuando directamente con el entorno de Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Ejecuta 'clear' en Linux y macOS
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException ex) {
            System.out.print("\nFalló limpieza de consola...");
        }
    }

    public static void pausar() {
        System.out.print("\nPresioná Enter para continuar...");
        scanner.nextLine();
    }

    public static int leerOpcion(int min, int max) {
        while (true) {
            System.out.print("Elegí una opción: ");
            String linea = scanner.nextLine().trim();
            try {
                int opcion = Integer.parseInt(linea);
                if (opcion >= min && opcion <= max) {
                    return opcion;
                }
                System.out.println("La opción debe estar entre " + min + " y " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Ingresá un número válido.");
            }
        }
    }
    public static void leerTexto(String msg) {

    }
}

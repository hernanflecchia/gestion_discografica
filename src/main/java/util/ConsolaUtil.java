package util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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

    public static String leerTexto(String msg) {
        String texto;
        do {
            System.out.print(msg);
            texto = scanner.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("El valor no puede estar vacío.");
            }
        } while (texto.isEmpty());
        return texto;
    }

    public static int leerEntero(String msg) {
        while (true) {
            String texto = leerTexto(msg);
            try {
                int valor = Integer.parseInt(texto);
                if (valor >= 0) {
                    return valor;
                }
                System.out.println("El valor no puede ser negativo.");
            } catch (NumberFormatException e) {
                System.out.println("Ingresá un número entero válido.");
            }
        }
    }

    public static double leerDecimal(String msg) {
        while (true) {
            String texto = leerTexto(msg).replace(',', '.');   // acepta 1,99 y 1.99
            try {
                double valor = Double.parseDouble(texto);
                if (valor >= 0) {
                    return valor;
                }
                System.out.println("El valor no puede ser negativo.");
            } catch (NumberFormatException e) {
                System.out.println("Ingresá un número válido.");
            }
        }
    }

    public static LocalDate leerFecha(String msg) {
        while (true) {
            String texto = leerTexto(msg + " (AAAA-MM-DD): ");
            try {
                return LocalDate.parse(texto);
            } catch (DateTimeParseException e) {
                System.out.println("Fecha inválida.");
            }
        }
    }

    public static String leerTextoOpcional(String msg) {
        String texto;
        System.out.print(msg);
        texto = scanner.nextLine().trim();
        if (texto.isEmpty()) {
            return  null;
        }
        return texto;
    }
}

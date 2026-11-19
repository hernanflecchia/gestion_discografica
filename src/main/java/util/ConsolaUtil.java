package util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Métodos de ayuda para leer datos del teclado y manejar la consola.
 * Todos los métodos de lectura vuelven a pedir el dato si es inválido,
 * así el programa nunca se corta por un error de tipeo.
 */
public class ConsolaUtil {
    private static final Scanner scanner = new Scanner(System.in);   // un solo Scanner para todo el programa

    // Constructor privado: no se crean objetos de esta clase, se usan sus métodos static.
    private ConsolaUtil() {}

    // Limpia la pantalla de la consola (funciona en Windows, Linux y macOS).
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

    // Espera a que el usuario presione Enter.
    public static void pausar() {
        System.out.print("\nPresioná Enter para continuar...");
        scanner.nextLine();
    }

    // Lee una opción de menú entre min y max.
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

    // Lee un texto que no puede quedar vacío.
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

    // Lee un número entero que no sea negativo.
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

    // Lee un número con decimales que no sea negativo.
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

    // Lee una fecha con el formato AAAA-MM-DD.
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

    // Lee un texto que puede quedar vacío; en ese caso devuelve null.
    // Se usa para cargar listas (integrantes, canciones) hasta que el usuario deja uno vacío.
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

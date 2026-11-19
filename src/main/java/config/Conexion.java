package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Centraliza los datos de conexión a la base de datos.
 * Todas las clases DAO piden su conexión acá, así los datos están en un solo lugar.
 * Usuario y clave tienen que coincidir con los que crea sql/schema.sql.
 */
public class Conexion {

    // Dirección de la base: servidor local, puerto 3306, base "discografica".
    private static final String URL = "jdbc:mysql://localhost:3306/discografica";
    private static final String USUARIO = "disco_admin";
    private static final String CLAVE = "d1sc04dm1n!";

    // Abre una conexión nueva. Quien la usa la cierra (con try-with-resources).
    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}

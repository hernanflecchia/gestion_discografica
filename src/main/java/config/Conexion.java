package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Centraliza los datos de conexión a la base de datos.
 */
public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/discografica";
    private static final String USUARIO = "disco_admin";
    private static final String CLAVE = "d1sc04dm1n";

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}

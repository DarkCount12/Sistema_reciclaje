package Backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/sistema_reciclaje?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "";

    private static Connection conexion = null;

    public static Connection obtenerConexion() {
        if (conexion == null) {
            try {
                conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
                System.out.println("Conexión establecida con éxito.");
            } catch (SQLException e) {
                System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            }
        }
        return conexion;
    }
}
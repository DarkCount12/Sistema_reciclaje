package Backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://mainline.proxy.rlwy.net:48293/sistema_reciclaje?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "HnSfCtzPooZTZKLdybCXEzhaenhwsrVP";

    public static Connection obtenerConexion() {
        try {
            Connection conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            return conexion;
        } catch (SQLException e) {
            return null;
        }
    }
}

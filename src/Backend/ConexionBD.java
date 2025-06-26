package Backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    // Cambia "sistema_reciclaje" por el nombre real de tu base de datos local si es diferente
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_reciclaje?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "IJgonaldos"; // Cambia si tu MySQL tiene contraseña

    public static Connection obtenerConexion() {
        try {
            return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar a la base de datos", e);
        }
    }
}
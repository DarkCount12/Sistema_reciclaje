package Backend;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connect {
    private static final String URL = "jdbc:mysql://mainline.proxy.rlwy.net:48293/sistema_reciclaje?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String CONTRASEÑA = "HnSfCtzPooZTZKLdybCXEzhaenhwsrVP";

    public static Connection obtenerConexion() {
        try {
            System.out.println("Conexion exitosa");
            return DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
            return null;
        }
    }
}
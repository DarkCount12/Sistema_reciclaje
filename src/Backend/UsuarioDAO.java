package Backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioDAO {
    
    // Método para insertar un nuevo usuario
    public boolean registrarUsuario(String nombre, String apellido, String correo, String contrasena, String telefono) {
        // Establecemos la conexión con la base de datos
        Connection conexion = connect.obtenerConexion();
        
        if (conexion == null) {
            System.out.println("Error: No se pudo conectar a la base de datos.");
            return false;
        }
        
        // SQL para insertar el nuevo usuario
        String sql = "INSERT INTO Usuario (nombre, apellido, correo, contrasena, telefono) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            // Asignamos los valores a los parámetros del SQL
            statement.setString(1, nombre);
            statement.setString(2, apellido);
            statement.setString(3, correo);
            statement.setString(4, contrasena);  
            statement.setString(5, telefono);
            
            // Ejecutamos la inserción
            int filasInsertadas = statement.executeUpdate();
            if (filasInsertadas > 0) {
                System.out.println("¡Usuario registrado exitosamente!");
                return true;  // Registro exitoso
            }
        } catch (SQLException e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
        } finally {
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        
        return false;  // Si no se insertó el usuario
    }
}

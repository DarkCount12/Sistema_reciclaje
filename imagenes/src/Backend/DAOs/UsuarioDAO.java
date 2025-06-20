package Backend.DAOs;

import Backend.ConexionBD;
import Backend.Modelos.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public boolean registrarUsuario(String nombre, String apellido, String correo, String contrasena, String telefono, int rol) {
    String sql = "INSERT INTO Usuario (nombre, apellido, correo, contrasena, telefono) VALUES (?, ?, ?, ?, ?)";

    try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        stmt.setString(1, nombre);
        stmt.setString(2, apellido);
        stmt.setString(3, correo);
        stmt.setString(4, contrasena);
        stmt.setString(5, telefono);

        int filas = stmt.executeUpdate();

        if (filas > 0) {
            // Obtener la clave generada
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int id_usuario = rs.getInt(1); // asumimos que id_usuario es la primera clave generada
                asignarRol(rol, id_usuario);   // aquí sí puedes pasar el ID correctamente
                return true;
            }
        }

    } catch (SQLException e) {
        System.out.println("Error al registrar usuario: " + e.getMessage());
    }

    return false;
}


    public List<Usuario> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuario";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo"),
                        rs.getString("contrasena"),
                        rs.getString("telefono")
                );
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener todos los usuarios: " + e.getMessage());
        }

        return usuarios;
    }




    public Usuario obtenerUsuarioPorCorreo(String correo) {
        String sql = "SELECT * FROM Usuario WHERE correo = ?";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, correo);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo"),
                        rs.getString("contrasena"),
                        rs.getString("telefono")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener usuario por correo: " + e.getMessage());
        }

        return null;
    }



    public boolean actualizarUsuarioPorCorreo(String correo, String nombre, String apellido, String contrasena, String telefono) {
        String sql = "UPDATE Usuario SET nombre = ?, apellido = ?, correo = ?, contrasena = ?, telefono = ? WHERE correo = ?";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, correo);
            stmt.setString(4, contrasena);
            stmt.setString(5, telefono);
            stmt.setString(6, correo);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }



    public boolean eliminarUsuarioPorCorreo(String correo) {
        String sql = "DELETE FROM Usuario WHERE correo = ?";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, correo);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }

   
    public boolean asignarRol(int rol,int id_usuario) {
         String sql = "INSERT INTO Usuario_Rol (id_usuario,id_rol,fecha_asignacion) VALUES (?, ?, ?)";
    try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setInt(1, id_usuario);
            stmt.setInt(2, rol);
            stmt.setDate(3, new java.sql.Date(System.currentTimeMillis()));

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
            return false;
        }

    }

   public String obtenerRolPorCorreo(String correo) {
    String sql = "SELECT r.nombre_rol " +
                 "FROM Usuario u " +
                 "JOIN Usuario_Rol ur ON u.id_usuario = ur.id_usuario " +
                 "JOIN Rol r ON r.id_rol = ur.id_rol " +
                 "WHERE u.correo = ?";

    try (Connection conn = ConexionBD.obtenerConexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, correo);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getString("nombre_rol");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

public int obtenerIdPorCorreo(String correo) {
    try {
        Connection conn = ConexionBD.obtenerConexion();
        String query = "SELECT id_usuario FROM Usuario WHERE correo = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, correo);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getInt("id_usuario");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return -1; // Valor que indica que no se encontró el usuario o hubo un error
}

public int obtenerPuntajeporId(int id){

    
      String sql = "SELECT puntos_totales " +
                 "FROM Puntaje " +
                 "WHERE id_usuario = ?";

    try (Connection conn = ConexionBD.obtenerConexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getInt("puntos_totales");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return 0 ;
}
}

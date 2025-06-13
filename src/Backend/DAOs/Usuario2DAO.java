package Backend.DAOs;

import Backend.ConexionBD;
import Backend.Modelos.Puntaje;
import Backend.Modelos.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Usuario2DAO implements CrudDAO<Usuario> {

    @Override
    public List<Usuario> obtenerTodos() {
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
                        rs.getString("telefono") // puede ser NULL, así que lo manejamos como String
                );
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener todos los usuarios: " + e.getMessage());
        }

        return usuarios;
    }

    @Override
    public Usuario obtenerPorID(int id) {
        String sql = "SELECT * FROM Usuario WHERE id_usuario = ?";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setInt(1, id);
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
            System.out.println("Error al obtener usuario por ID: " + e.getMessage());
        }

        return null;
    }

    @Override
    public void insertar(Usuario usuario) {
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty() ||
            usuario.getApellido() == null || usuario.getApellido().trim().isEmpty() ||
            usuario.getCorreo() == null || usuario.getCorreo().trim().isEmpty() ||
            usuario.getContrasena() == null || usuario.getContrasena().trim().isEmpty()) {

            throw new IllegalArgumentException("Nombre, apellido, correo y contraseña no pueden estar vacíos.");
        }

        String sql = "INSERT INTO Usuario (nombre, apellido, correo, contrasena, telefono) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getCorreo());
            stmt.setString(4, usuario.getContrasena());
            stmt.setString(5, usuario.getTelefono());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(int id, Usuario usuario) {
        String sql = "UPDATE Usuario SET nombre = ?, apellido = ?, correo = ?, contrasena = ?, telefono = ? WHERE id_usuario = ?";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getCorreo());
            stmt.setString(4, usuario.getContrasena());
            stmt.setString(5, usuario.getTelefono());
            stmt.setInt(6, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al actualizar usuario: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM Usuario WHERE id_usuario = ?";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
        }
    }




public List<Usuario> buscarPorNombreApellidoOCorreo(String texto) {
    List<Usuario> usuarios = new ArrayList<>();
    String sql = "SELECT * FROM Usuario WHERE nombre LIKE ? OR apellido LIKE ? OR correo LIKE ?";

    try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
        String filtro = "%" + texto + "%";
        stmt.setString(1, filtro);
        stmt.setString(2, filtro);
        stmt.setString(3, filtro);
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
        System.out.println("Error en buscarPorNombreApellidoOCorreo: " + e.getMessage());
    }

    return usuarios;
}


public String obtenerRolDeUsuario(int idUsuario) {
    String rol = "Desconocido";
    String sql = "SELECT r.nombre_rol FROM Usuario_Rol ur JOIN Rol r ON ur.id_rol = r.id_rol WHERE ur.id_usuario = ?";

    try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
        stmt.setInt(1, idUsuario);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            rol = rs.getString("nombre_rol");
        }
    } catch (SQLException e) {
        System.out.println("Error en obtenerRolDeUsuario: " + e.getMessage());
    }

    return rol;
}



public List<Usuario> buscarPorRolNombre(String nombreRol) {
    List<Usuario> usuarios = new ArrayList<>();
    String sql = "SELECT u.* FROM Usuario u " +
                 "JOIN Usuario_Rol ur ON u.id_usuario = ur.id_usuario " +
                 "JOIN Rol r ON ur.id_rol = r.id_rol " +
                 "WHERE r.nombre_rol = ?";

    try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
        stmt.setString(1, nombreRol);
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
        System.out.println("Error en buscarPorRolNombre: " + e.getMessage());
    }

    return usuarios;
}


public Puntaje obtenerPuntajeDeUsuario(int idUsuario) {
    Puntaje puntaje = null;
    String sql = "SELECT puntos_totales, puntos_ganados, puntos_gastados FROM Puntaje WHERE id_usuario = ?";

    try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
        stmt.setInt(1, idUsuario);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int totales = rs.getInt("puntos_totales");
            int ganados = rs.getInt("puntos_ganados");
            int gastados = rs.getInt("puntos_gastados");
            puntaje = new Puntaje(totales, ganados, gastados);
        }
    } catch (SQLException e) {
        System.out.println("Error en obtenerPuntajeDeUsuario: " + e.getMessage());
    }

    return puntaje;
}








}

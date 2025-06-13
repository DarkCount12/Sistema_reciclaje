package Backend.DAOs;

import Backend.ConexionBD;
import Backend.Modelos.PuntoReciclaje;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PuntoReciclajeDAO {

    public boolean registrarPunto(String correo, String nombre, String ubicacion, String horario, double latitud, double longitud) {
        String sql = "INSERT INTO Puntos_Reciclaje (id_usuario, nombre, ubicacion, horario, latitud, longitud) " +
                "VALUES ((SELECT id_usuario FROM Usuarios WHERE correo = ?), ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, correo);
            stmt.setString(2, nombre);
            stmt.setString(3, ubicacion);
            stmt.setString(4, horario);
            stmt.setDouble(5, latitud);
            stmt.setDouble(6, longitud);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al registrar punto de reciclaje: " + e.getMessage());
            return false;
        }
    }

    public List<PuntoReciclaje> obtenerTodosLosPuntos() {
        List<PuntoReciclaje> puntos = new ArrayList<>();
        String sql = "SELECT * FROM Puntos_Reciclaje";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PuntoReciclaje punto = new PuntoReciclaje(
                        rs.getInt("id_punto"),
                        rs.getString("nombre"),
                        rs.getString("ubicacion"),
                        rs.getString("horario"),
                        rs.getDouble("latitud"),
                        rs.getDouble("longitud")
                );
                puntos.add(punto);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener puntos de reciclaje: " + e.getMessage());
        }

        return puntos;
    }

    public List<PuntoReciclaje> obtenerPuntosPorCorreo(String correo) {
        List<PuntoReciclaje> puntos = new ArrayList<>();
        String sql = "SELECT DISTINCT pr.* " +
                "FROM Puntos_Reciclaje pr " +
                "JOIN Reciclaje r ON pr.id_punto = r.id_punto " +
                "JOIN Usuario u ON r.id_usuario = u.id_usuario " +
                "WHERE u.correo = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, correo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PuntoReciclaje punto = new PuntoReciclaje(
                            rs.getInt("id_punto"),
                            rs.getString("nombre"),
                            rs.getString("ubicacion"),
                            rs.getString("horario"),
                            rs.getDouble("latitud"),
                            rs.getDouble("longitud")
                    );
                    puntos.add(punto);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener puntos de reciclaje por correo: " + e.getMessage());
        }

        return puntos;
    }

    public boolean actualizarPuntosPorCorreo(String correo, String nombre, String ubicacion, String horario, double latitud, double longitud) {
        String sql = "UPDATE Puntos_Reciclaje SET nombre = ?, ubicacion = ?, horario = ?, latitud = ?, longitud = ? " +
                "WHERE id_usuario = (SELECT id_usuario FROM Usuarios WHERE correo = ?)";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, ubicacion);
            stmt.setString(3, horario);
            stmt.setDouble(4, latitud);
            stmt.setDouble(5, longitud);
            stmt.setString(6, correo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar puntos por correo: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarPuntosPorCorreo(String correo) {
        String sql = "DELETE FROM Puntos_Reciclaje WHERE id_usuario = (SELECT id_usuario FROM Usuarios WHERE correo = ?)";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, correo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar puntos por correo: " + e.getMessage());
            return false;
        }
    }
}

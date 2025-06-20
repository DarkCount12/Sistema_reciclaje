package Backend.DAOs;

import Backend.ConexionBD;
import Backend.Modelos.Reciclaje;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReciclajeDAO {

    public boolean registrarReciclaje(Date fechaReciclaje, String correo, Integer idPunto) {
        String sql = "INSERT INTO Reciclaje (fecha_reciclaje, id_usuario, id_punto) VALUES (?, (SELECT id_usuario FROM Usuario WHERE correo = ?), ?)";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setDate(1, fechaReciclaje);
            stmt.setString(2, correo);
            if (idPunto != null) {
                stmt.setInt(3, idPunto);
            } else {
                stmt.setNull(3, Types.INTEGER);
            }

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al registrar reciclaje: " + e.getMessage());
            return false;
        }
    }

    public List<Reciclaje> obtenerTodosLosReciclajes() {
        List<Reciclaje> reciclajes = new ArrayList<>();
        String sql = "SELECT * FROM Reciclaje";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Reciclaje reciclaje = new Reciclaje(
                        rs.getInt("id_reciclaje"),
                        rs.getDate("fecha_reciclaje"),
                        rs.getInt("id_usuario"),
                        rs.getObject("id_punto") != null ? rs.getInt("id_punto") : null
                );
                reciclajes.add(reciclaje);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener todos los reciclajes: " + e.getMessage());
        }

        return reciclajes;
    }

    public List<Reciclaje> obtenerReciclajesPorCorreo(String correo) {
        List<Reciclaje> reciclajes = new ArrayList<>();
        String sql = "SELECT * FROM Reciclaje WHERE id_usuario = (SELECT id_usuario FROM Usuario WHERE correo = ?)";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, correo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Reciclaje reciclaje = new Reciclaje(
                        rs.getInt("id_reciclaje"),
                        rs.getDate("fecha_reciclaje"),
                        rs.getInt("id_usuario"),
                        rs.getObject("id_punto") != null ? rs.getInt("id_punto") : null
                );
                reciclajes.add(reciclaje);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener reciclajes por correo: " + e.getMessage());
        }

        return reciclajes;
    }

    public boolean actualizarReciclajePorCorreo(String correo, Date fechaReciclaje, Integer idPunto) {
        String sql = "UPDATE Reciclaje SET fecha_reciclaje = ?, id_punto = ? WHERE id_usuario = (SELECT id_usuario FROM Usuario WHERE correo = ?)";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setDate(1, fechaReciclaje);
            if (idPunto != null) {
                stmt.setInt(2, idPunto);
            } else {
                stmt.setNull(2, Types.INTEGER);
            }
            stmt.setString(3, correo);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar reciclaje por correo: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarReciclajePorCorreo(String correo) {
        String sql = "DELETE FROM Reciclaje WHERE id_usuario = (SELECT id_usuario FROM Usuario WHERE correo = ?)";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, correo);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar reciclaje por correo: " + e.getMessage());
            return false;
        }
    }
}

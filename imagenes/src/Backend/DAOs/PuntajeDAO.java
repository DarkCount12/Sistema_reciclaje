package Backend.DAOs;

import Backend.Modelos.Puntaje;
import Backend.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PuntajeDAO {

    public boolean registrarPuntaje(Puntaje puntaje) {
        String sql = "INSERT INTO Puntaje (id_usuario, puntos_totales, puntos_gastados, puntos_ganados) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setInt(1, puntaje.getIdUsuario());
            stmt.setObject(2, puntaje.getPuntosTotales());
            stmt.setObject(3, puntaje.getPuntosGastados());
            stmt.setObject(4, puntaje.getPuntosGanados());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al registrar puntaje: " + e.getMessage());
            return false;
        }
    }

    public List<Puntaje> obtenerTodosLosPuntajes() {
        List<Puntaje> puntajes = new ArrayList<>();
        String sql = "SELECT * FROM Puntaje";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Puntaje puntaje = new Puntaje(
                        rs.getInt("id_usuario"),
                        rs.getInt("puntos_totales"),
                        rs.getInt("puntos_gastados"),
                        rs.getInt("puntos_ganados")
                );
                puntajes.add(puntaje);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener todos los puntajes: " + e.getMessage());
        }

        return puntajes;
    }

    public Puntaje obtenerPuntajePorCorreo(String correo) {
        String sql = """
                SELECT p.* FROM Puntaje p
                JOIN Usuario u ON p.id_usuario = u.id_usuario
                WHERE u.correo = ?
                """;

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, correo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Puntaje(
                        rs.getInt("id_usuario"),
                        rs.getInt("puntos_totales"),
                        rs.getInt("puntos_gastados"),
                        rs.getInt("puntos_ganados")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener puntaje por correo: " + e.getMessage());
        }

        return null;
    }

    public boolean actualizarPuntajePorCorreo(String correo, Puntaje puntaje) {
        String sql = """
                UPDATE Puntaje SET puntos_totales = ?, puntos_gastados = ?, puntos_ganados = ?
                WHERE id_usuario = (SELECT id_usuario FROM Usuario WHERE correo = ?)
                """;

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setObject(1, puntaje.getPuntosTotales());
            stmt.setObject(2, puntaje.getPuntosGastados());
            stmt.setObject(3, puntaje.getPuntosGanados());
            stmt.setString(4, correo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar puntaje por correo: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarPuntajePorCorreo(String correo) {
        String sql = """
                DELETE FROM Puntaje WHERE id_usuario = (SELECT id_usuario FROM Usuario WHERE correo = ?)
                """;

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, correo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar puntaje por correo: " + e.getMessage());
            return false;
        }
    }
}

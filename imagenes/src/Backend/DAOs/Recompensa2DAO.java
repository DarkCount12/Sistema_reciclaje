package Backend.DAOs;

import Backend.ConexionBD;
import Backend.Modelos.Recompensa;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Recompensa2DAO implements CrudDAO<Recompensa> {

    @Override
    public List<Recompensa> obtenerTodos() {
        List<Recompensa> recompensas = new ArrayList<>();
        String sql = "SELECT * FROM Recompensa";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Recompensa recompensa = new Recompensa(
                        rs.getInt("id_recompensa"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getInt("puntos_necesarios")
                );
                recompensas.add(recompensa);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener todas las recompensas: " + e.getMessage());
        }

        return recompensas;
    }

    @Override
    public Recompensa obtenerPorID(int id) {
        String sql = "SELECT * FROM Recompensa WHERE id_recompensa = ?";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Recompensa(
                        rs.getInt("id_recompensa"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getInt("puntos_necesarios")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener recompensa por ID: " + e.getMessage());
        }

        return null;
    }

@Override
public void insertar(Recompensa recompensa) {
    if (recompensa.getNombre() == null || recompensa.getNombre().trim().isEmpty() ||
        recompensa.getDescripcion() == null || recompensa.getDescripcion().trim().isEmpty()) {
        throw new IllegalArgumentException("Nombre y descripción no pueden estar vacíos");
    }

    String sql = "INSERT INTO Recompensa (nombre, descripcion, puntos_necesarios) VALUES (?, ?, ?)";

    try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
        stmt.setString(1, recompensa.getNombre());
        stmt.setString(2, recompensa.getDescripcion());
        stmt.setInt(3, recompensa.getPuntos_necesarios());
        stmt.executeUpdate();
    } catch (SQLException e) {
        // Puedes envolver la excepción en una RuntimeException para que salga del método
        throw new RuntimeException("Error al insertar recompensa: " + e.getMessage(), e);
    }
}






    
    @Override
    public void actualizar(int id, Recompensa recompensa) {
        String sql = "UPDATE Recompensa SET nombre = ?, descripcion = ?, puntos_necesarios = ? WHERE id_recompensa = ?";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, recompensa.getNombre());
            stmt.setString(2, recompensa.getDescripcion());
            stmt.setInt(3, recompensa.getPuntos_necesarios());
            stmt.setInt(4, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al actualizar recompensa: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM Recompensa WHERE id_recompensa = ?";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al eliminar recompensa: " + e.getMessage());
        }
    }
}

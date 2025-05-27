package Backend.DAOs;

import Backend.ConexionBD;
import Backend.Modelos.Recompensa;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecompensaDAO {

    public List<Recompensa> obtenerTodasLasRecompensas() {
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

    public Recompensa obtenerRecompensaPorId(int id_recompensa) {
        String sql = "SELECT * FROM Recompensa WHERE id_recompensa = ?";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setInt(1, id_recompensa);

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
}

package Backend.DAOs;

import Backend.Modelos.Canje;
import Backend.ConexionBD;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CanjeDAO {

    public boolean insertarCanje(Canje canje) {
        String sql = "INSERT INTO Canje (fecha_canje, puntos_gastados, id_usuario, id_recompensa) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setTimestamp(1, canje.getFechaCanje());
            stmt.setInt(2, canje.getPuntosGastados());
            stmt.setInt(3, canje.getIdUsuario());
            stmt.setInt(4, canje.getIdRecompensa());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al insertar canje: " + e.getMessage());
            return false;
        }
    }
}

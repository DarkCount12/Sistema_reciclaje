package Backend.DAOs;

import Backend.ConexionBD;
import Backend.Modelos.Donaciones;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DonacionesDAO {
    public boolean registrarDonacion(Donaciones donacion) {
        String sql = "INSERT INTO Donaciones (id_usuario, monto, metodo_pago, fecha_donacion) VALUES (?, ?, ?, ?)";
         try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)){
            stmt.setInt(1, donacion.getIdUsuario());
            stmt.setDouble(2, donacion.getMonto());
            stmt.setString(3, donacion.getMetodoPago());
            stmt.setDate(4, new java.sql.Date(donacion.getFechaDonacion().getTime()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Donaciones> obtenerDonacionesPorUsuario(int idUsuario) {
        List<Donaciones> donaciones = new ArrayList<>();
        String sql = "SELECT * FROM Donaciones WHERE id_usuario = ?";
        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Donaciones d = new Donaciones();
                d.setIdDonacion(rs.getInt("id_donacion"));
                d.setIdUsuario(rs.getInt("id_usuario"));
                d.setMonto(rs.getDouble("monto"));
                d.setMetodoPago(rs.getString("metodo_pago"));
                d.setFechaDonacion(rs.getDate("fecha_donacion"));
                donaciones.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donaciones;
    }
    
    public double obtenerTotalDonacionesUsuario(int idUsuario) {
        String sql = "SELECT SUM(monto) as total FROM Donaciones WHERE id_usuario = ?";
        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
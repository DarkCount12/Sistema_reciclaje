package Backend.DAOs;

import Backend.ConexionBD;
import Backend.Modelos.Descuento;
import java.sql.*;
import java.util.*;

public class Descuento2DAO implements CrudDAO<Descuento> {
  

    @Override
    public void insertar(Descuento d) {
        String sql = "INSERT INTO Descuento (nombre_descuento, porcentaje_descuento, fecha_inicio, fecha_fin, id_recompensa) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, d.getNombre_descuento());
            stmt.setDouble(2, d.getPorcentaje_descuento());
            stmt.setTimestamp(3, new Timestamp(d.getFecha_inicio().getTime()));
            stmt.setTimestamp(4, new Timestamp(d.getFecha_fin().getTime()));
            stmt.setInt(5, d.getId_recompensa());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(int id, Descuento d) {
        String sql = "UPDATE Descuento SET nombre_descuento = ?, porcentaje_descuento = ?, fecha_inicio = ?, fecha_fin = ?, id_recompensa = ? WHERE id_descuento = ?";
        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, d.getNombre_descuento());
            stmt.setDouble(2, d.getPorcentaje_descuento());
            stmt.setTimestamp(3, new Timestamp(d.getFecha_inicio().getTime()));
            stmt.setTimestamp(4, new Timestamp(d.getFecha_fin().getTime()));
            stmt.setInt(5, d.getId_recompensa());
            stmt.setInt(6, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM Descuento WHERE id_descuento = ?";
        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Descuento obtenerPorID(int id) {
        String sql = "SELECT * FROM Descuento WHERE id_descuento = ?";
        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapearDesdeResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Descuento> obtenerTodos() {
        List<Descuento> lista = new ArrayList<>();
        String sql = "SELECT * FROM Descuento";
        try (Connection conn = ConexionBD.obtenerConexion();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapearDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }


    private Descuento mapearDesdeResultSet(ResultSet rs) throws SQLException {
        Descuento d = new Descuento();
        d.setId_descuento(rs.getInt("id_descuento"));
        d.setNombre_descuento(rs.getString("nombre_descuento"));
        d.setPorcentaje_descuento(rs.getDouble("porcentaje_descuento"));
        d.setFecha_inicio(rs.getTimestamp("fecha_inicio"));
        d.setFecha_fin(rs.getTimestamp("fecha_fin"));
        d.setId_recompensa(rs.getInt("id_recompensa"));
        return d;
    }
}

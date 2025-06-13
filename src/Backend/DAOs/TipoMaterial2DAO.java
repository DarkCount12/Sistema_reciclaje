package Backend.DAOs;

import Backend.ConexionBD;
import Backend.Modelos.TipoMaterial;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoMaterial2DAO implements CrudDAO<TipoMaterial> {

 

    @Override
    public void insertar(TipoMaterial tm) {
        String sql = "INSERT INTO Tipo_Material (categoria, puntos_kg, co2_reducido_kg) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, tm.getCategoria());
            stmt.setDouble(2, tm.getPuntos_kg());
            stmt.setDouble(3, tm.getCo2_reducido_kg());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(int id, TipoMaterial tm) {
        String sql = "UPDATE Tipo_Material SET categoria=?, puntos_kg=?, co2_reducido_kg=? WHERE id_tipo_material=?";
        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, tm.getCategoria());
            stmt.setDouble(2, tm.getPuntos_kg());
            stmt.setDouble(3, tm.getCo2_reducido_kg());
            stmt.setInt(4, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM Tipo_Material WHERE id_tipo_material=?";
        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TipoMaterial obtenerPorID(int id) {
        String sql = "SELECT * FROM Tipo_Material WHERE id_tipo_material=?";
        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                TipoMaterial tm = new TipoMaterial();
                tm.setId_tipo_material(rs.getInt("id_tipo_material"));
                tm.setCategoria(rs.getString("categoria"));
                tm.setPuntos_kg(rs.getDouble("puntos_kg"));
                tm.setCo2_reducido_kg(rs.getDouble("co2_reducido_kg"));
                return tm;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<TipoMaterial> obtenerTodos() {
        List<TipoMaterial> lista = new ArrayList<>();
        String sql = "SELECT * FROM Tipo_Material";
        try (Connection conn = ConexionBD.obtenerConexion();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                TipoMaterial tm = new TipoMaterial();
                tm.setId_tipo_material(rs.getInt("id_tipo_material"));
                tm.setCategoria(rs.getString("categoria"));
                tm.setPuntos_kg(rs.getDouble("puntos_kg"));
                tm.setCo2_reducido_kg(rs.getDouble("co2_reducido_kg"));
                lista.add(tm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}

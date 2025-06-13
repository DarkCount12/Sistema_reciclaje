package Backend.DAOs;

import Backend.ConexionBD;
import Backend.Modelos.Rol;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Rol2DAO implements CrudDAO<Rol> {

    @Override
    public void insertar(Rol rol) {
        String sql = "INSERT INTO Rol (nombre_rol, descripcion) VALUES (?, ?)";
        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, rol.getNombreRol());
            stmt.setString(2, rol.getDescripcion());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(int id, Rol rol) {
        String sql = "UPDATE Rol SET nombre_rol=?, descripcion=? WHERE id_rol=?";
        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, rol.getNombreRol());
            stmt.setString(2, rol.getDescripcion());
            stmt.setInt(3, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM Rol WHERE id_rol=?";
        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Rol obtenerPorID(int id) {
        String sql = "SELECT * FROM Rol WHERE id_rol=?";
        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Rol rol = new Rol();
                rol.setIdRol(rs.getInt("id_rol"));
                rol.setNombreRol(rs.getString("nombre_rol"));
                rol.setDescripcion(rs.getString("descripcion"));
                return rol;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Rol> obtenerTodos() {
        List<Rol> lista = new ArrayList<>();
        String sql = "SELECT * FROM Rol";
        try (Connection conn = ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Rol rol = new Rol();
                rol.setIdRol(rs.getInt("id_rol"));
                rol.setNombreRol(rs.getString("nombre_rol"));
                rol.setDescripcion(rs.getString("descripcion"));
                lista.add(rol);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}

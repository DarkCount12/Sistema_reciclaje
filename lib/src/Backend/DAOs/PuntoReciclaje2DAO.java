package Backend.DAOs;

import Backend.ConexionBD;
import Backend.Modelos.PuntoReciclaje;
import java.sql.*;
import java.util.*;

public class PuntoReciclaje2DAO implements CrudDAO<PuntoReciclaje> {
   
    @Override
    public void insertar(PuntoReciclaje punto) {
        String sql = "INSERT INTO puntos_reciclaje (nombre, ubicacion, horario, latitud, longitud) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, punto.getNombre());
            stmt.setString(2, punto.getUbicacion());
            stmt.setString(3, punto.getHorario());
            stmt.setDouble(4, punto.getLatitud());
            stmt.setDouble(5, punto.getLongitud());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar punto de reciclaje", e);
        }
    }



    
    @Override
    public void actualizar(int id, PuntoReciclaje punto) {
        String sql = "UPDATE puntos_reciclaje SET nombre=?, ubicacion=?, horario=?, latitud=?, longitud=? WHERE id_punto=?";
        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, punto.getNombre());
            stmt.setString(2, punto.getUbicacion());
            stmt.setString(3, punto.getHorario());
            stmt.setDouble(4, punto.getLatitud());
            stmt.setDouble(5, punto.getLongitud());
            stmt.setInt(6, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar punto de reciclaje", e);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM puntos_reciclaje WHERE id_punto=?";
        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar punto de reciclaje", e);
        }
    }

    @Override
    public List<PuntoReciclaje> obtenerTodos() {
        List<PuntoReciclaje> lista = new ArrayList<>();
        String sql = "SELECT * FROM puntos_reciclaje";
        try (Connection conn = ConexionBD.obtenerConexion();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                lista.add(new PuntoReciclaje(
                    rs.getInt("id_punto"),
                    rs.getString("nombre"),
                    rs.getString("ubicacion"),
                    rs.getString("horario"),
                    rs.getDouble("latitud"),
                    rs.getDouble("longitud")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener puntos de reciclaje", e);
        }
        return lista;
    }

    @Override
    public PuntoReciclaje obtenerPorID(int id) {
        String sql = "SELECT * FROM puntos_reciclaje WHERE id_punto=?";
        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new PuntoReciclaje(
                    rs.getInt("id_punto"),
                    rs.getString("nombre"),
                    rs.getString("ubicacion"),
                    rs.getString("horario"),
                    rs.getDouble("latitud"),
                    rs.getDouble("longitud")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener punto de reciclaje por ID", e);
        }
        return null;
    }


public int obtenerUltimoId() throws SQLException {
    String sql = "SELECT MAX(id) AS ultimoId FROM punto_reciclaje";
    try (Connection conn = ConexionBD.obtenerConexion();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
            return rs.getInt("ultimoId");
        } else {
            throw new SQLException("No se pudo obtener el último ID.");
        }
    }
}


public int insertarYRetornarId(PuntoReciclaje punto) {
    String sql = "INSERT INTO puntos_reciclaje (nombre, ubicacion, horario, latitud, longitud) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        stmt.setString(1, punto.getNombre());
        stmt.setString(2, punto.getUbicacion());
        stmt.setString(3, punto.getHorario());
        stmt.setDouble(4, punto.getLatitud());
        stmt.setDouble(5, punto.getLongitud());

        int filasAfectadas = stmt.executeUpdate();

        if (filasAfectadas == 0) {
            throw new SQLException("La inserción falló, no se creó ninguna fila.");
        }

        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("No se pudo obtener el ID generado.");
            }
        }

    } catch (SQLException e) {
        throw new RuntimeException("Error al insertar punto de reciclaje", e);
    }
}


}

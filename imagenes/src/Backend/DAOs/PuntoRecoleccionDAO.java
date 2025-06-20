
package Backend.DAOs;

import Backend.ConexionBD;
import Backend.Modelos.PuntoRecoleccion;
import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;



public class PuntoRecoleccionDAO {

    public List<PuntoRecoleccion> obtenerTodos() {
        List<PuntoRecoleccion> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Puntos_Reciclaje")) {

            while (rs.next()) {
                PuntoRecoleccion p = new PuntoRecoleccion();
                p.setId(rs.getInt("id_punto"));
                p.setNombre(rs.getString("nombre"));
                p.setDireccion(rs.getString("ubicacion"));
                p.setHorario(rs.getString("horario"));
                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }


    
 public void insertarReciclaje(Timestamp fechaReciclaje, int idUsuario, int idPunto) {
    String sql = "INSERT INTO Reciclaje (fecha_reciclaje, id_usuario, id_punto) VALUES (?, ?, ?)";

    try (Connection conn = ConexionBD.obtenerConexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setTimestamp(1, fechaReciclaje);
        stmt.setInt(2, idUsuario);
        stmt.setInt(3, idPunto);
        stmt.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al insertar reciclaje: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
    }
} 






    public int obtenerUltimoIdReciclaje() {
    int ultimoId = -1;
    String sql = "SELECT MAX(id_reciclaje) AS ultimo_id FROM Reciclaje";

    try (Connection conn = ConexionBD.obtenerConexion();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        if (rs.next()) {
            ultimoId = rs.getInt("ultimo_id");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return ultimoId;
}

 

 public Map<String, Integer> obtenerTiposMaterial() {
    Map<String, Integer> tipos = new LinkedHashMap<>();
    String sql = "SELECT id_tipo_material, categoria FROM Tipo_Material";

    try (Connection conn = ConexionBD.obtenerConexion();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            int id = rs.getInt("id_tipo_material");
            String categoria = rs.getString("categoria");
            tipos.put(categoria, id);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return tipos;
}



   public void insertarMaterial(int idReciclaje, String material, double cantidadKg, int idTipoMaterial) {
        try (Connection conn = ConexionBD.obtenerConexion()) {
            String sql = "INSERT INTO Lista_Material (id_reciclaje, material, cantidad_kg, id_tipo_material) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idReciclaje);
                stmt.setString(2, material);
                stmt.setDouble(3, cantidadKg);
                stmt.setInt(4, idTipoMaterial);
                stmt.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al insertar en la base de datos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

public List<String> obtenerMaterialesPorPunto(int idPunto) {
    List<String> materiales = new ArrayList<>();
    String sql = """
        SELECT tm.categoria
        FROM Punto_Tipo_Material ptm
        JOIN Tipo_Material tm ON ptm.id_tipo_material = tm.id_tipo_material
        WHERE ptm.id_punto = ?
    """;

    try (Connection conn = ConexionBD.obtenerConexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idPunto);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            materiales.add(rs.getString("categoria"));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return materiales;
}

public Map<String, Integer> obtenerMaterialesPorPunto2(int idPunto) {
    Map<String, Integer> materiales = new LinkedHashMap<>();
    String sql = """
        SELECT tm.id_tipo_material, tm.categoria
        FROM Punto_Tipo_Material ptm
        JOIN Tipo_Material tm ON ptm.id_tipo_material = tm.id_tipo_material
        WHERE ptm.id_punto = ?
    """;

    try (Connection conn = ConexionBD.obtenerConexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idPunto);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id_tipo_material");
            String categoria = rs.getString("categoria");
            materiales.put(categoria, id);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return materiales;
}



}

package Backend.DAOs;

import Backend.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PuntoTipoMaterialDAO {

    public void insertarRelacion(int idPunto, int idTipoMaterial) {
        String sql = "INSERT INTO punto_tipo_material (idPunto, idTipoMaterial) VALUES (?, ?)";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPunto);
            stmt.setInt(2, idTipoMaterial);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarRelacion(int idPunto, int idTipoMaterial) {
        String sql = "DELETE FROM punto_tipo_material WHERE idPunto = ? AND idTipoMaterial = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPunto);
            stmt.setInt(2, idTipoMaterial);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existeRelacion(int idPunto, int idTipoMaterial) {
        String sql = "SELECT * FROM punto_tipo_material WHERE idPunto = ? AND idTipoMaterial = ?";
        boolean existe = false;

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPunto);
            stmt.setInt(2, idTipoMaterial);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                existe = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return existe;
    }

    public List<Integer> obtenerMaterialesPorPunto(int idPunto) {
    List<Integer> materiales = new ArrayList<>();
    String sql = "SELECT id_tipo_material FROM punto_tipo_material WHERE id_punto = ?";

    try (Connection conn = ConexionBD.obtenerConexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idPunto);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            materiales.add(rs.getInt("id_tipo_material"));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return materiales;
}







public void eliminarRelacionesPorPunto(int idPunto) {
    String sql = "DELETE FROM punto_tipo_material WHERE id_punto = ?";

    try (Connection conn = ConexionBD.obtenerConexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idPunto);
        stmt.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    }
}



public void insertarRelaciones(int idPunto, int[] materialesSeleccionados) {
    try (Connection conn = ConexionBD.obtenerConexion()) {
        String sql = "INSERT INTO punto_tipo_material (id_punto, id_tipo_material) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);

        for (int idMaterial : materialesSeleccionados) {
            stmt.setInt(1, idPunto);
            stmt.setInt(2, idMaterial);
            stmt.addBatch(); // Añade la inserción al batch
        }

        stmt.executeBatch(); // Ejecuta todas las inserciones de una vez
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}

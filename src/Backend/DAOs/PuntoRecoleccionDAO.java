
package Backend.DAOs;

import Backend.ConexionBD;
import Backend.Modelos.PuntoRecoleccion;
import java.sql.*;
import java.util.*;


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



}

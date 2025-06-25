package Backend.DAOs;

import Backend.ConexionBD;
import Backend.Modelos.Puntaje;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PuntajeDAO {

    public boolean registrarPuntaje(Puntaje puntaje) {
        String sql = "INSERT INTO Puntaje (id_usuario, puntos_totales, puntos_gastados, puntos_ganados) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setInt(1, puntaje.getIdUsuario());
            stmt.setObject(2, puntaje.getPuntosTotales());
            stmt.setObject(3, puntaje.getPuntosGastados());
            stmt.setObject(4, puntaje.getPuntosGanados());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al registrar puntaje: " + e.getMessage());
            return false;
        }
    }

    public List<Puntaje> obtenerTodosLosPuntajes() {
        List<Puntaje> puntajes = new ArrayList<>();
        String sql = "SELECT * FROM Puntaje";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Puntaje puntaje = new Puntaje(
                        rs.getInt("id_usuario"),
                        rs.getInt("puntos_totales"),
                        rs.getInt("puntos_gastados"),
                        rs.getInt("puntos_ganados")
                );
                puntajes.add(puntaje);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener todos los puntajes: " + e.getMessage());
        }

        return puntajes;
    }

    public Puntaje obtenerPuntajePorCorreo(String correo) {
        String sql = """
                SELECT p.* FROM Puntaje p
                JOIN Usuario u ON p.id_usuario = u.id_usuario
                WHERE u.correo = ?
                """;

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, correo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Puntaje(
                        rs.getInt("id_usuario"),
                        rs.getInt("puntos_totales"),
                        rs.getInt("puntos_gastados"),
                        rs.getInt("puntos_ganados")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener puntaje por correo: " + e.getMessage());
        }

        return null;
    }

    public boolean actualizarPuntajePorCorreo(String correo, Puntaje puntaje) {
        String sql = """
                UPDATE Puntaje SET puntos_totales = ?, puntos_gastados = ?, puntos_ganados = ?
                WHERE id_usuario = (SELECT id_usuario FROM Usuario WHERE correo = ?)
                """;

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setObject(1, puntaje.getPuntosTotales());
            stmt.setObject(2, puntaje.getPuntosGastados());
            stmt.setObject(3, puntaje.getPuntosGanados());
            stmt.setString(4, correo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar puntaje por correo: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarPuntajePorCorreo(String correo) {
        String sql = """
                DELETE FROM Puntaje WHERE id_usuario = (SELECT id_usuario FROM Usuario WHERE correo = ?)
                """;

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, correo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar puntaje por correo: " + e.getMessage());
            return false;
        }
    }

public List<Object[]> obtenerPuntosPorMaterial(String material) {
    List<Object[]> resultados = new ArrayList<>();
    String sql = """
            SELECT u.nombre, CAST(SUM(l.cantidad_kg * t.puntos_kg) AS UNSIGNED) AS puntos_generados
            FROM Usuario u
            JOIN Reciclaje r ON u.id_usuario = r.id_usuario
            JOIN Lista_Material l ON r.id_reciclaje = l.id_reciclaje
            JOIN Tipo_Material t ON l.id_tipo_material = t.id_tipo_material
            WHERE t.categoria = ?
            GROUP BY u.nombre
            """;

    try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
        stmt.setString(1, material);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            resultados.add(new Object[]{
                    rs.getString("nombre"),
                    rs.getInt("puntos_generados") // Ya lo recuperas como entero
            });
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener puntos por material: " + e.getMessage());
    }

    return resultados;
}





public List<Object[]> obtenerMaterialesConMayorAsignacion() {
    List<Object[]> resultados = new ArrayList<>();
    String sql = """
        SELECT tm.categoria, SUM(lm.cantidad_kg) AS total_kg
        FROM Lista_Material lm
        JOIN Tipo_Material tm ON lm.id_tipo_material = tm.id_tipo_material
        GROUP BY tm.categoria
        ORDER BY total_kg DESC
        LIMIT 10
    """;

    try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            resultados.add(new Object[]{rs.getString("categoria"), rs.getDouble("total_kg")});
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener materiales con mayor asignación: " + e.getMessage());
    }

    return resultados;
}


// Ranking filtrado por tipo
public List<Object[]> obtenerRankingPorFiltro(String filtro) {
    List<Object[]> resultados = new ArrayList<>();
    String sql = String.format("""
        SELECT u.nombre, p.%s AS puntos
        FROM Usuario u
        JOIN Puntaje p ON u.id_usuario = p.id_usuario
        ORDER BY puntos DESC
        """, filtro);

    try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            resultados.add(new Object[]{
                    rs.getString("nombre"),
                    rs.getInt("puntos")
            });
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener ranking por filtro: " + e.getMessage());
    }

    return resultados;
}

public int obtenerPromedioPuntos(String columnaFiltro) {
    String sql = "SELECT AVG(" + columnaFiltro + ") AS promedio FROM Puntaje";
    int promedio = 0;

    try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
            promedio = rs.getInt("promedio");
        }

    } catch (SQLException e) {
        System.out.println("Error al obtener promedio de puntos: " + e.getMessage());
    }

    return promedio;
}

public List<Object[]> obtenerRankingPorFiltro(String columnaFiltro, int limite) {
    List<Object[]> resultados = new ArrayList<>();
    String sql = "SELECT u.nombre, p." + columnaFiltro + " AS puntos " +
                 "FROM Puntaje p " +
                 "JOIN Usuario u ON p.id_usuario = u.id_usuario " +
                 "ORDER BY p." + columnaFiltro + " DESC";

    if (limite > 0) {
        sql += " LIMIT " + limite;
    }

    try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            resultados.add(new Object[]{rs.getString("nombre"), rs.getInt("puntos")});
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener ranking: " + e.getMessage());
    }

    return resultados;
}

public List<Object[]> obtenerReporteCanjes() {
    List<Object[]> resultados = new ArrayList<>();
    String sql = """
            SELECT r.nombre AS tipo_recompensa,
                   COUNT(c.id_canje) AS cantidad_canjeada,
                   SUM(c.puntos_gastados) AS total_puntos_usados
            FROM Canje c
            JOIN Recompensa r ON c.id_recompensa = r.id_recompensa
            GROUP BY r.nombre
            ORDER BY cantidad_canjeada DESC
            """;

    try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            resultados.add(new Object[]{
                    rs.getString("tipo_recompensa"),
                    rs.getInt("cantidad_canjeada"),
                    rs.getInt("total_puntos_usados")
            });
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener el reporte de canjes: " + e.getMessage());
    }

    return resultados;
}


public List<Object[]> obtenerUsuariosRegistradosPorFechaYRol(String fechaDesde, String fechaHasta, int idRol) {
    List<Object[]> lista = new ArrayList<>();
    String sqlBase = """
        SELECT u.nombre,
               IFNULL(SUM(ia.kg_reciclados), 0) AS total_kg_reciclados,
               IFNULL(SUM(p.puntos_ganados), 0) AS total_puntos_ganados
        FROM Usuario u
        JOIN Usuario_Rol ur ON u.id_usuario = ur.id_usuario
        LEFT JOIN Reciclaje r ON u.id_usuario = r.id_usuario
        LEFT JOIN Impacto_Ambiental ia ON r.id_reciclaje = ia.id_reciclaje
        LEFT JOIN Puntaje p ON u.id_usuario = p.id_usuario
        WHERE ur.fecha_asignacion BETWEEN ? AND ?
        """;

    if (idRol != 0) {
        sqlBase += " AND ur.id_rol = ? ";
    }

    sqlBase += " GROUP BY u.id_usuario, u.nombre;";

    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement stmt = con.prepareStatement(sqlBase)) {

        stmt.setString(1, fechaDesde);
        stmt.setString(2, fechaHasta);

        if (idRol != 0) {
            stmt.setInt(3, idRol);
        }

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String nombre = rs.getString("nombre");
            double kgReciclados = rs.getDouble("total_kg_reciclados");
            int puntosGanados = rs.getInt("total_puntos_ganados");
            lista.add(new Object[]{nombre, kgReciclados, puntosGanados});
        }

    } catch (SQLException e) {
        System.out.println("Error al obtener usuarios registrados: " + e.getMessage());
    }

    return lista;
}



}

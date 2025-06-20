package Backend.Servicios;

import Backend.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportesServicio {

    public List<Map<String, Object>> obtenerMaterialRecicladoPorTipo(String tipoMaterial) {
        List<Map<String, Object>> resultados = new ArrayList<>();
        String sql = "SELECT " +
                "tm.categoria AS tipo_material, " +
                "CONCAT(u.nombre, ' ', u.apellido) AS nombre_usuario, " +
                "lm.cantidad_kg AS total_kg_reciclado, " +
                "r.fecha_reciclaje AS fecha " +
                "FROM Lista_Material lm " +
                "JOIN Tipo_Material tm ON lm.id_tipo_material = tm.id_tipo_material " +
                "JOIN Reciclaje r ON lm.id_reciclaje = r.id_reciclaje " +
                "JOIN Usuario u ON r.id_usuario = u.id_usuario " +
                "WHERE tm.categoria = ? " +
                "ORDER BY fecha DESC";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, tipoMaterial);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("tipo_material", rs.getString("tipo_material"));
                fila.put("nombre_usuario", rs.getString("nombre_usuario"));
                fila.put("total_kg_reciclado", rs.getDouble("total_kg_reciclado"));
                fila.put("fecha", rs.getDate("fecha"));
                resultados.add(fila);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener material reciclado por tipo: " + e.getMessage());
        }

        return resultados;
    }

    public List<Map<String, Object>> obtenerPuntosGeneradosPorTipo(String tipoMaterial) {
        List<Map<String, Object>> resultados = new ArrayList<>();
        String sql = "SELECT " +
                "tm.categoria AS tipo_material, " +
                "CONCAT(u.nombre, ' ', u.apellido) AS nombre_usuario, " +
                "lm.cantidad_kg * tm.puntos_kg AS puntos_generados, " +
                "r.fecha_reciclaje AS fecha " +
                "FROM Lista_Material lm " +
                "JOIN Tipo_Material tm ON lm.id_tipo_material = tm.id_tipo_material " +
                "JOIN Reciclaje r ON lm.id_reciclaje = r.id_reciclaje " +
                "JOIN Usuario u ON r.id_usuario = u.id_usuario " +
                "WHERE tm.categoria = ? " +
                "ORDER BY fecha DESC";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, tipoMaterial);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("tipo_material", rs.getString("tipo_material"));
                fila.put("nombre_usuario", rs.getString("nombre_usuario"));
                fila.put("puntos_generados", rs.getDouble("puntos_generados"));
                fila.put("fecha", rs.getDate("fecha"));
                resultados.add(fila);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener puntos generados por tipo: " + e.getMessage());
        }

        return resultados;
    }

    public List<Map<String, Object>> obtenerCO2ReducidoPorTipo(String tipoMaterial) {
        List<Map<String, Object>> resultados = new ArrayList<>();
        String sql = "SELECT " +
                "tm.categoria AS tipo_material, " +
                "CONCAT(u.nombre, ' ', u.apellido) AS nombre_usuario, " +
                "lm.cantidad_kg * tm.co2_reducido_kg AS co2_reducido, " +
                "r.fecha_reciclaje AS fecha " +
                "FROM Lista_Material lm " +
                "JOIN Tipo_Material tm ON lm.id_tipo_material = tm.id_tipo_material " +
                "JOIN Reciclaje r ON lm.id_reciclaje = r.id_reciclaje " +
                "JOIN Usuario u ON r.id_usuario = u.id_usuario " +
                "WHERE tm.categoria = ? " +
                "ORDER BY fecha DESC";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, tipoMaterial);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("tipo_material", rs.getString("tipo_material"));
                fila.put("nombre_usuario", rs.getString("nombre_usuario"));
                fila.put("co2_reducido", rs.getDouble("co2_reducido"));
                fila.put("fecha", rs.getDate("fecha"));
                resultados.add(fila);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener CO2 reducido por tipo: " + e.getMessage());
        }

        return resultados;
    }

    public List<Map<String, Object>> obtenerActividadUsuario(String nombreUsuario) {
        List<Map<String, Object>> resultados = new ArrayList<>();
        String sql = "SELECT " +
                "CONCAT(u.nombre, ' ', u.apellido) AS nombre_usuario, " +
                "tm.categoria AS tipo_material, " +
                "lm.cantidad_kg, " +
                "lm.cantidad_kg * tm.puntos_kg AS puntos_generados, " +
                "r.fecha_reciclaje AS fecha, " +
                "pr.nombre AS punto_reciclaje " +
                "FROM Usuario u " +
                "JOIN Reciclaje r ON u.id_usuario = r.id_usuario " +
                "JOIN Lista_Material lm ON r.id_reciclaje = lm.id_reciclaje " +
                "JOIN Tipo_Material tm ON lm.id_tipo_material = tm.id_tipo_material " +
                "LEFT JOIN Puntos_Reciclaje pr ON r.id_punto = pr.id_punto " +
                "WHERE CONCAT(u.nombre, ' ', u.apellido) = ? " +
                "ORDER BY r.fecha_reciclaje DESC";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, nombreUsuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("nombre_usuario", rs.getString("nombre_usuario"));
                fila.put("tipo_material", rs.getString("tipo_material"));
                fila.put("cantidad_kg", rs.getDouble("cantidad_kg"));
                fila.put("puntos_generados", rs.getDouble("puntos_generados"));
                fila.put("fecha", rs.getDate("fecha"));
                fila.put("punto_reciclaje", rs.getString("punto_reciclaje"));
                resultados.add(fila);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener actividad de usuario: " + e.getMessage());
        }

        return resultados;
    }

    public List<Map<String, Object>> obtenerRecompensasCanjeadas(String tipoRecompensa) {
        List<Map<String, Object>> resultados = new ArrayList<>();
        String sql = "SELECT " +
                "CONCAT(u.nombre, ' ', u.apellido) AS nombre_usuario, " +
                "r.nombre AS nombre_recompensa, " +
                "r.puntos_necesarios, " +
                "COALESCE(c.puntos_gastados, 0) AS puntos_gastados, " +
                "c.fecha_canje, " +
                "r.descripcion, " +
                "d.nombre_descuento, " +
                "COALESCE(d.porcentaje_descuento, 0) AS porcentaje_descuento, " +
                "CASE " +
                "WHEN d.id_descuento IS NOT NULL AND c.fecha_canje BETWEEN d.fecha_inicio AND d.fecha_fin " +
                "THEN ROUND(r.puntos_necesarios * (1 - d.porcentaje_descuento / 100)) " +
                "WHEN COALESCE(c.puntos_gastados, 0) > 0 " +
                "THEN c.puntos_gastados " +
                "ELSE r.puntos_necesarios " +
                "END AS puntos_utilizados " +
                "FROM Canje c " +
                "JOIN Recompensa r ON c.id_recompensa = r.id_recompensa " +
                "JOIN Usuario u ON c.id_usuario = u.id_usuario " +
                "LEFT JOIN Descuento d ON r.id_recompensa = d.id_recompensa " +
                "AND c.fecha_canje BETWEEN d.fecha_inicio AND d.fecha_fin " +
                "WHERE r.nombre LIKE ? " +
                "ORDER BY c.fecha_canje DESC";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, "%" + tipoRecompensa + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("nombre_usuario", rs.getString("nombre_usuario"));
                fila.put("nombre_recompensa", rs.getString("nombre_recompensa"));
                fila.put("puntos_utilizados", rs.getInt("puntos_utilizados")); // Campo principal para la interfaz
                fila.put("puntos_gastados", rs.getInt("puntos_gastados")); // Mantener compatibilidad
                fila.put("fecha_canje", rs.getDate("fecha_canje"));
                fila.put("descripcion", rs.getString("descripcion"));
                fila.put("descuento_aplicado", rs.getString("nombre_descuento"));
                fila.put("porcentaje_descuento", rs.getBigDecimal("porcentaje_descuento"));

                // Para debug - puedes quitar esta línea después
                System.out.println("Canje: " + rs.getString("nombre_recompensa") +
                        " - Puntos utilizados: " + rs.getInt("puntos_utilizados"));

                resultados.add(fila);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener recompensas canjeadas: " + e.getMessage());
        }

        return resultados;
    }

    public List<String> obtenerTiposMaterial() {
        List<String> tipos = new ArrayList<>();
        String sql = "SELECT DISTINCT categoria FROM Tipo_Material ORDER BY categoria";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tipos.add(rs.getString("categoria"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener tipos de material: " + e.getMessage());
        }

        return tipos;
    }

    public List<String> obtenerNombresUsuarios() {
        List<String> usuarios = new ArrayList<>();
        String sql = "SELECT DISTINCT CONCAT(nombre, ' ', apellido) AS nombre_completo FROM Usuario ORDER BY nombre_completo";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                usuarios.add(rs.getString("nombre_completo"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener nombres de usuarios: " + e.getMessage());
        }

        return usuarios;
    }

    public List<String> obtenerTiposRecompensa() {
        List<String> recompensas = new ArrayList<>();
        String sql = "SELECT DISTINCT nombre FROM Recompensa ORDER BY nombre";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                recompensas.add(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener tipos de recompensa: " + e.getMessage());
        }

        return recompensas;
    }

    public int obtenerTotalKgRecicladoPorTipo(String tipoMaterial) {
        String sql = "SELECT COALESCE(SUM(lm.cantidad_kg), 0) AS total_kg " +
                "FROM Lista_Material lm " +
                "JOIN Tipo_Material tm ON lm.id_tipo_material = tm.id_tipo_material " +
                "WHERE tm.categoria = ?";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, tipoMaterial);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return (int) Math.round(rs.getDouble("total_kg"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener total kg reciclado por tipo: " + e.getMessage());
        }

        return 0;
    }

    public int obtenerTotalPuntosGeneradosPorTipo(String tipoMaterial) {
        String sql = "SELECT COALESCE(SUM(lm.cantidad_kg * tm.puntos_kg), 0) AS total_puntos " +
                "FROM Lista_Material lm " +
                "JOIN Tipo_Material tm ON lm.id_tipo_material = tm.id_tipo_material " +
                "WHERE tm.categoria = ?";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, tipoMaterial);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return (int) Math.round(rs.getDouble("total_puntos"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener total puntos generados por tipo: " + e.getMessage());
        }

        return 0;
    }

    public int obtenerTotalCO2ReducidoPorTipo(String tipoMaterial) {
        String sql = "SELECT COALESCE(SUM(lm.cantidad_kg * tm.co2_reducido_kg), 0) AS total_co2 " +
                "FROM Lista_Material lm " +
                "JOIN Tipo_Material tm ON lm.id_tipo_material = tm.id_tipo_material " +
                "WHERE tm.categoria = ?";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, tipoMaterial);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return (int) Math.round(rs.getDouble("total_co2"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener total CO2 reducido por tipo: " + e.getMessage());
        }

        return 0;
    }

    public int obtenerTotalKgRecicladoPorUsuario(String nombreUsuario) {
        String sql = "SELECT COALESCE(SUM(lm.cantidad_kg), 0) AS total_kg " +
                "FROM Usuario u " +
                "JOIN Reciclaje r ON u.id_usuario = r.id_usuario " +
                "JOIN Lista_Material lm ON r.id_reciclaje = lm.id_reciclaje " +
                "WHERE CONCAT(u.nombre, ' ', u.apellido) = ?";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, nombreUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return (int) Math.round(rs.getDouble("total_kg"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener total kg reciclado por usuario: " + e.getMessage());
        }

        return 0;
    }

    public int obtenerTotalPuntosObtenidosPorUsuario(String nombreUsuario) {
        String sql = "SELECT COALESCE(SUM(lm.cantidad_kg * tm.puntos_kg), 0) AS total_puntos " +
                "FROM Usuario u " +
                "JOIN Reciclaje r ON u.id_usuario = r.id_usuario " +
                "JOIN Lista_Material lm ON r.id_reciclaje = lm.id_reciclaje " +
                "JOIN Tipo_Material tm ON lm.id_tipo_material = tm.id_tipo_material " +
                "WHERE CONCAT(u.nombre, ' ', u.apellido) = ?";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, nombreUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return (int) Math.round(rs.getDouble("total_puntos"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener total puntos obtenidos por usuario: " + e.getMessage());
        }

        return 0;
    }

    public int obtenerTotalPuntosUtilizadosEnCanjes(String tipoRecompensa) {
        String sql = "SELECT COALESCE(SUM(" +
                "CASE " +
                "WHEN d.id_descuento IS NOT NULL AND c.fecha_canje BETWEEN d.fecha_inicio AND d.fecha_fin " +
                "THEN ROUND(r.puntos_necesarios * (1 - d.porcentaje_descuento / 100)) " +
                "WHEN COALESCE(c.puntos_gastados, 0) > 0 " +
                "THEN c.puntos_gastados " +
                "ELSE r.puntos_necesarios " +
                "END), 0) AS total_puntos_utilizados " +
                "FROM Canje c " +
                "JOIN Recompensa r ON c.id_recompensa = r.id_recompensa " +
                "LEFT JOIN Descuento d ON r.id_recompensa = d.id_recompensa " +
                "AND c.fecha_canje BETWEEN d.fecha_inicio AND d.fecha_fin " +
                "WHERE r.nombre LIKE ?";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, "%" + tipoRecompensa + "%");
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("total_puntos_utilizados");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener total puntos utilizados en canjes: " + e.getMessage());
        }

        return 0;
    }

    public int obtenerTotalPuntosUtilizadosEnTodosLosCanjes() {
        String sql = "SELECT COALESCE(SUM(" +
                "CASE " +
                "WHEN d.id_descuento IS NOT NULL AND c.fecha_canje BETWEEN d.fecha_inicio AND d.fecha_fin " +
                "THEN ROUND(r.puntos_necesarios * (1 - d.porcentaje_descuento / 100)) " +
                "WHEN COALESCE(c.puntos_gastados, 0) > 0 " +
                "THEN c.puntos_gastados " +
                "ELSE r.puntos_necesarios " +
                "END), 0) AS total_puntos_utilizados " +
                "FROM Canje c " +
                "JOIN Recompensa r ON c.id_recompensa = r.id_recompensa " +
                "LEFT JOIN Descuento d ON r.id_recompensa = d.id_recompensa " +
                "AND c.fecha_canje BETWEEN d.fecha_inicio AND d.fecha_fin";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("total_puntos_utilizados");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener total puntos utilizados en todos los canjes: " + e.getMessage());
        }

        return 0;
    }

    public int obtenerTotalCanjesPorRecompensa(String tipoRecompensa) {
        String sql = "SELECT COUNT(*) AS total_canjes " +
                "FROM Canje c " +
                "JOIN Recompensa r ON c.id_recompensa = r.id_recompensa " +
                "WHERE r.nombre LIKE ?";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, "%" + tipoRecompensa + "%");
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("total_canjes");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener total canjes por recompensa: " + e.getMessage());
        }

        return 0;
    }

    public double obtenerValorRecompensaCanje(String tipoRecompensa) {
        String sql = "SELECT AVG(" +
                "CASE " +
                "WHEN d.id_descuento IS NOT NULL AND c.fecha_canje BETWEEN d.fecha_inicio AND d.fecha_fin " +
                "THEN ROUND(r.puntos_necesarios * (1 - d.porcentaje_descuento / 100)) " +
                "WHEN COALESCE(c.puntos_gastados, 0) > 0 " +
                "THEN c.puntos_gastados " +
                "ELSE r.puntos_necesarios " +
                "END) AS valor_promedio " +
                "FROM Canje c " +
                "JOIN Recompensa r ON c.id_recompensa = r.id_recompensa " +
                "LEFT JOIN Descuento d ON r.id_recompensa = d.id_recompensa " +
                "AND c.fecha_canje BETWEEN d.fecha_inicio AND d.fecha_fin " +
                "WHERE r.nombre LIKE ?";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, "%" + tipoRecompensa + "%");
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Math.round(rs.getDouble("valor_promedio") * 100.0) / 100.0; // Redondear a 2 decimales
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener valor recompensa canje: " + e.getMessage());
        }

        return 0.0;
    }

    public int obtenerTotalKgRecicladoGeneral() {
        String sql = "SELECT COALESCE(SUM(lm.cantidad_kg), 0) AS total_kg " +
                "FROM Lista_Material lm";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return (int) Math.round(rs.getDouble("total_kg"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener total kg reciclado general: " + e.getMessage());
        }

        return 0;
    }

    public int obtenerTotalPuntosGeneradosGeneral() {
        String sql = "SELECT COALESCE(SUM(lm.cantidad_kg * tm.puntos_kg), 0) AS total_puntos " +
                "FROM Lista_Material lm " +
                "JOIN Tipo_Material tm ON lm.id_tipo_material = tm.id_tipo_material";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return (int) Math.round(rs.getDouble("total_puntos"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener total puntos generados general: " + e.getMessage());
        }

        return 0;
    }

    public int obtenerTotalCO2ReducidoGeneral() {
        String sql = "SELECT COALESCE(SUM(lm.cantidad_kg * tm.co2_reducido_kg), 0) AS total_co2 " +
                "FROM Lista_Material lm " +
                "JOIN Tipo_Material tm ON lm.id_tipo_material = tm.id_tipo_material";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return (int) Math.round(rs.getDouble("total_co2"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener total CO2 reducido general: " + e.getMessage());
        }

        return 0;
    }

    public int obtenerTotalCanjesTodasRecompensas() {
        String sql = "SELECT COUNT(*) AS total_canjes FROM Canje";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("total_canjes");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener total canjes todas recompensas: " + e.getMessage());
        }

        return 0;
    }


    public List<Map<String, Object>> obtenerRankingUsuariosPorKgReciclados(int cantidad) {
        List<Map<String, Object>> resultados = new ArrayList<>();
        String sql = "SELECT " +
                "CONCAT(u.nombre, ' ', u.apellido) AS nombre_usuario, " +
                "COALESCE(SUM(lm.cantidad_kg), 0.0) AS total_kg_reciclado, " +
                "COALESCE(SUM(lm.cantidad_kg * tm.puntos_kg), 0.0) AS total_puntos_generados, " +
                "COUNT(CASE WHEN r.id_reciclaje IS NOT NULL THEN r.id_reciclaje END) AS total_reciclajes " +
                "FROM Usuario u " +
                "LEFT JOIN Reciclaje r ON u.id_usuario = r.id_usuario " +
                "LEFT JOIN Lista_Material lm ON r.id_reciclaje = lm.id_reciclaje " +
                "LEFT JOIN Tipo_Material tm ON lm.id_tipo_material = tm.id_tipo_material " +
                "GROUP BY u.id_usuario, u.nombre, u.apellido " +
                "ORDER BY total_kg_reciclado DESC " +
                "LIMIT ?";

        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setInt(1, cantidad);
            ResultSet rs = stmt.executeQuery();

            int posicion = 1;
            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("posicion", posicion);
                fila.put("nombre_usuario", rs.getString("nombre_usuario"));

                // Asegurar que los valores no sean null
                double totalKg = rs.getDouble("total_kg_reciclado");
                double totalPuntos = rs.getDouble("total_puntos_generados");
                int totalReciclajes = rs.getInt("total_reciclajes");

                fila.put("total_kg_reciclado", totalKg);
                fila.put("total_puntos_generados", totalPuntos);
                fila.put("total_reciclajes", totalReciclajes);

                resultados.add(fila);
                posicion++;
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener ranking usuarios por kg reciclados: " + e.getMessage());
            e.printStackTrace();
        }

        return resultados;
    }

    public List<Map<String, Object>> obtenerRankingRecompensasPorCanjes(int cantidad) {
        List<Map<String, Object>> resultados = new ArrayList<>();
        String sql = "SELECT " +
                "r.nombre AS nombre_recompensa, " +
                "r.descripcion, " +
                "r.puntos_necesarios, " +
                "COUNT(c.id_canje) AS total_canjes, " +
                "COALESCE(SUM(" +
                "CASE " +
                "WHEN d.id_descuento IS NOT NULL AND c.fecha_canje BETWEEN d.fecha_inicio AND d.fecha_fin " +
                "THEN ROUND(r.puntos_necesarios * (1 - d.porcentaje_descuento / 100)) " +
                "WHEN COALESCE(c.puntos_gastados, 0) > 0 " +
                "THEN c.puntos_gastados " +
                "ELSE r.puntos_necesarios " +
                "END), 0) AS total_puntos_utilizados " +
                "FROM Recompensa r " +
                "LEFT JOIN Canje c ON r.id_recompensa = c.id_recompensa " +
                "LEFT JOIN Descuento d ON r.id_recompensa = d.id_recompensa " +
                "AND c.fecha_canje BETWEEN d.fecha_inicio AND d.fecha_fin " +
                "GROUP BY r.id_recompensa, r.nombre, r.descripcion, r.puntos_necesarios " +
                "ORDER BY total_canjes DESC, total_puntos_utilizados DESC " +
                "LIMIT ?";
        try (PreparedStatement stmt = ConexionBD.obtenerConexion().prepareStatement(sql)) {
            stmt.setInt(1, cantidad);
            ResultSet rs = stmt.executeQuery();
            int posicion = 1;
            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("posicion", posicion);
                fila.put("nombre_recompensa", rs.getString("nombre_recompensa"));
                fila.put("descripcion", rs.getString("descripcion"));
                fila.put("puntos_necesarios", rs.getInt("puntos_necesarios"));
                fila.put("total_canjes", rs.getInt("total_canjes"));
                fila.put("total_puntos_utilizados", rs.getInt("total_puntos_utilizados"));
                resultados.add(fila);
                posicion++;
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener ranking recompensas por canjes: " + e.getMessage());
        }
        return resultados;
    }
}

package Backend.DAOs;

import Backend.Modelos.ImpactoAmbiental;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImpactoAmbientalDAO {

    private Connection conexion;

    public ImpactoAmbientalDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertar(ImpactoAmbiental impacto) throws SQLException {
        String sql = "INSERT INTO Impacto_Ambiental (fecha, kg_reciclados, CO2_reducidos, id_reciclaje) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setDate(1, impacto.getFecha());
            stmt.setBigDecimal(2, impacto.getKgReciclados());
            stmt.setBigDecimal(3, impacto.getCo2Reducidos());
            stmt.setInt(4, impacto.getIdReciclaje());
            stmt.executeUpdate();
        }
    }

    public List<ImpactoAmbiental> obtenerTodos() throws SQLException {
        List<ImpactoAmbiental> lista = new ArrayList<>();
        String sql = "SELECT * FROM Impacto_Ambiental";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapearDesdeResultSet(rs));
            }
        }
        return lista;
    }

    public ImpactoAmbiental obtenerPorId(int idImpacto) throws SQLException {
        String sql = "SELECT * FROM Impacto_Ambiental WHERE id_impacto = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idImpacto);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapearDesdeResultSet(rs);
            }
        }
        return null;
    }

    public ImpactoAmbiental obtenerPorCorreo(String correo) throws SQLException {
        String sql = """
            SELECT ia.* FROM Impacto_Ambiental ia
            JOIN Reciclaje r ON ia.id_reciclaje = r.id_reciclaje
            JOIN Usuario u ON r.id_usuario = u.id_usuario
            WHERE u.correo = ?
            """;
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, correo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapearDesdeResultSet(rs);
            }
        }
        return null;
    }

    public void eliminar(int idImpacto) throws SQLException {
        String sql = "DELETE FROM Impacto_Ambiental WHERE id_impacto = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idImpacto);
            stmt.executeUpdate();
        }
    }

    public void actualizar(ImpactoAmbiental impacto) throws SQLException {
        String sql = "UPDATE Impacto_Ambiental SET fecha = ?, kg_reciclados = ?, CO2_reducidos = ?, id_reciclaje = ? WHERE id_impacto = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setDate(1, impacto.getFecha());
            stmt.setBigDecimal(2, impacto.getKgReciclados());
            stmt.setBigDecimal(3, impacto.getCo2Reducidos());
            stmt.setInt(4, impacto.getIdReciclaje());
            stmt.setInt(5, impacto.getIdImpacto());
            stmt.executeUpdate();
        }
    }

    private ImpactoAmbiental mapearDesdeResultSet(ResultSet rs) throws SQLException {
        return new ImpactoAmbiental(
                rs.getInt("id_impacto"),
                rs.getDate("fecha"),
                rs.getBigDecimal("kg_reciclados"),
                rs.getBigDecimal("CO2_reducidos"),
                rs.getInt("id_reciclaje")
        );
    }
}

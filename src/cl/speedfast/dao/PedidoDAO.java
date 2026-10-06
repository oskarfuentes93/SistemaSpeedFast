package cl.speedfast.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Clase que maneja las operaciones CRUD de pedidos en la base de datos
public class PedidoDAO {

    // Inserta un nuevo pedido en la tabla pedidos
    public boolean create(String direccion, String tipo, String estado) {
        String sql = "INSERT INTO pedidos (direccion, tipo, estado) VALUES (?, ?, ?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, direccion);
            ps.setString(2, tipo);
            ps.setString(3, estado);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al registrar pedido: " + e.getMessage());
            return false;
        }
    }

    // Consulta todos los pedidos de la tabla
    public List<Object[]> readAll() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT id, direccion, tipo, estado FROM pedidos";

        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Object[] fila = {
                        rs.getInt("id"),
                        rs.getString("direccion"),
                        rs.getString("tipo"),
                        rs.getString("estado")
                };
                lista.add(fila);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar pedidos: " + e.getMessage());
        }

        return lista;
    }

    // Actualiza un pedido existente por su ID
    public boolean update(int id, String direccion, String tipo, String estado) {
        String sql = "UPDATE pedidos SET direccion = ?, tipo = ?, estado = ? WHERE id = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, direccion);
            ps.setString(2, tipo);
            ps.setString(3, estado);
            ps.setInt(4, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar pedido: " + e.getMessage());
            return false;
        }
    }

    // Elimina un pedido por su ID
    public boolean delete(int id) {
        String sql = "DELETE FROM pedidos WHERE id = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar pedido: " + e.getMessage());
            return false;
        }
    }
}
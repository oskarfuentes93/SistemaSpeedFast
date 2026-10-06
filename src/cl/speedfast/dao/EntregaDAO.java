package cl.speedfast.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Clase que maneja las operaciones CRUD de entregas en la base de datos
public class EntregaDAO {

    // Inserta una nueva entrega asociando un pedido con un repartidor
    public void create(int idPedido, int idRepartidor, String fecha, String hora) {
        String sql = "INSERT INTO entregas (id_pedido, id_repartidor, fecha, hora) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPedido);
            ps.setInt(2, idRepartidor);
            ps.setDate(3, Date.valueOf(fecha));
            ps.setTime(4, Time.valueOf(hora + ":00"));
            ps.executeUpdate();
            System.out.println("Entrega registrada correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al registrar entrega: " + e.getMessage());
        }
    }

    // Consulta todas las entregas con datos del pedido y repartidor
    public List<Object[]> readAll() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT e.id, e.id_pedido, p.direccion, e.id_repartidor, r.nombre, e.fecha, e.hora " +
                "FROM entregas e " +
                "JOIN pedidos p ON e.id_pedido = p.id " +
                "JOIN repartidores r ON e.id_repartidor = r.id";

        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Object[] fila = {
                        rs.getInt("id"),
                        rs.getInt("id_pedido"),
                        rs.getString("direccion"),
                        rs.getInt("id_repartidor"),
                        rs.getString("nombre"),
                        rs.getString("fecha"),
                        rs.getString("hora")
                };
                lista.add(fila);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar entregas: " + e.getMessage());
        }

        return lista;
    }

    // Actualiza una entrega existente por su ID
    public void update(int id, int idPedido, int idRepartidor, String fecha, String hora) {
        String sql = "UPDATE entregas SET id_pedido = ?, id_repartidor = ?, fecha = ?, hora = ? WHERE id = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPedido);
            ps.setInt(2, idRepartidor);
            ps.setDate(3, Date.valueOf(fecha));
            ps.setTime(4, Time.valueOf(hora + ":00"));
            ps.setInt(5, id);
            ps.executeUpdate();
            System.out.println("Entrega actualizada correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al actualizar entrega: " + e.getMessage());
        }
    }

    // Elimina una entrega por su ID
    public void delete(int id) {
        String sql = "DELETE FROM entregas WHERE id = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Entrega eliminada correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al eliminar entrega: " + e.getMessage());
        }
    }
}
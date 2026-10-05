package cl.speedfast.dao;

import cl.speedfast.modelo.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public void guardar(Pedido pedido) {
        String tipo;
        if (pedido instanceof PedidoComida) {
            tipo = "COMIDA";
        } else if (pedido instanceof PedidoEncomienda) {
            tipo = "ENCOMIENDA";
        } else {
            tipo = "EXPRESS";
        }

        String sql = "INSERT INTO pedido (direccion, tipo, estado) VALUES (?, ?, ?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pedido.getDireccionEntrega());
            ps.setString(2, tipo);
            ps.setString(3, pedido.getEstado().name());
            ps.executeUpdate();

            System.out.println("Pedido guardado en la base de datos.");

        } catch (SQLException e) {
            System.out.println("Error al guardar pedido: " + e.getMessage());
        }
    }

    public List<Object[]> listarTodos() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT id, direccion, tipo, estado FROM pedido";

        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Object[] fila = {
                        rs.getInt("id"),
                        rs.getString("tipo"),
                        rs.getString("direccion"),
                        rs.getString("estado")
                };
                lista.add(fila);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar pedidos: " + e.getMessage());
        }

        return lista;
    }
}
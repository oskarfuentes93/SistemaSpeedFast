package cl.speedfast.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Clase que maneja las operaciones CRUD de repartidores en la base de datos
public class RepartidorDAO {

    // Inserta un nuevo repartidor en la tabla repartidores
    public void create(String nombre) {
        String sql = "INSERT INTO repartidores (nombre) VALUES (?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.executeUpdate();
            System.out.println("Repartidor registrado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al registrar repartidor: " + e.getMessage());
        }
    }

    // Consulta todos los repartidores de la tabla
    public List<Object[]> readAll() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT id, nombre FROM repartidores";

        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Object[] fila = {
                        rs.getInt("id"),
                        rs.getString("nombre")
                };
                lista.add(fila);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar repartidores: " + e.getMessage());
        }

        return lista;
    }

    // Actualiza un repartidor existente por su ID
    public void update(int id, String nombre) {
        String sql = "UPDATE repartidores SET nombre = ? WHERE id = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setInt(2, id);
            ps.executeUpdate();
            System.out.println("Repartidor actualizado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al actualizar repartidor: " + e.getMessage());
        }
    }

    // Elimina un repartidor por su ID
    public void delete(int id) {
        String sql = "DELETE FROM repartidores WHERE id = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Repartidor eliminado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al eliminar repartidor: " + e.getMessage());
        }
    }
}
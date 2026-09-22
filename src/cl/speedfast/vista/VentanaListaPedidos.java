package cl.speedfast.vista;

import cl.speedfast.modelo.ControladorDeEnvios;
import cl.speedfast.modelo.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaListaPedidos extends JFrame {

    private ControladorDeEnvios controlador;
    private DefaultTableModel modeloTabla;
    private JTable tabla;

    public VentanaListaPedidos(ControladorDeEnvios controlador) {
        this.controlador = controlador;

        setTitle("Lista de Pedidos");
        setSize(650, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Columnas de la tabla
        String[] columnas = {"ID", "Tipo", "Direccion", "Distancia (km)", "Estado", "Repartidor"};

        // DefaultTableModel: el modelo de datos que alimenta la JTable
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // la tabla es solo de lectura
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(25);

        // La tabla dentro de un scroll, por si hay muchos pedidos
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        // Boton para refrescar la tabla
        JButton btnRefrescar = new JButton("Refrescar");
        JPanel panelBoton = new JPanel();
        panelBoton.add(btnRefrescar);
        add(panelBoton, BorderLayout.SOUTH);

        btnRefrescar.addActionListener(e -> cargarPedidos());

        // Carga inicial al abrir la ventana
        cargarPedidos();
    }

    private void cargarPedidos() {
        // Limpia la tabla antes de volver a llenarla
        modeloTabla.setRowCount(0);

        // Recorre los pedidos del controlador y agrega una fila por cada uno
        for (Pedido pedido : controlador.getHistorial()) {
            String repartidor = pedido.getRepartidor();
            if (repartidor == null) {
                repartidor = "Sin asignar";
            }

            Object[] fila = {
                    pedido.getIdPedido(),
                    pedido.getClass().getSimpleName(),   // PedidoComida, PedidoExpress, etc.
                    pedido.getDireccionEntrega(),
                    pedido.getDistanciaKm(),
                    pedido.getEstado(),                  // PENDIENTE, EN_REPARTO, ENTREGADO
                    repartidor
            };
            modeloTabla.addRow(fila);
        }
    }
}
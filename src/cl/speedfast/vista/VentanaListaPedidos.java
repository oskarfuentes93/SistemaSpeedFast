package cl.speedfast.vista;

import cl.speedfast.dao.PedidoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaListaPedidos extends JFrame {

    private PedidoDAO pedidoDAO;
    private DefaultTableModel modeloTabla;
    private JTable tabla;

    public VentanaListaPedidos() {
        this.pedidoDAO = new PedidoDAO();

        setTitle("Lista de Pedidos");
        setSize(650, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnas = {"ID", "Tipo", "Direccion", "Estado"};

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        JButton btnRefrescar = new JButton("Refrescar");
        JPanel panelBoton = new JPanel();
        panelBoton.add(btnRefrescar);
        add(panelBoton, BorderLayout.SOUTH);

        btnRefrescar.addActionListener(e -> cargarPedidos());

        cargarPedidos();
    }

    private void cargarPedidos() {
        modeloTabla.setRowCount(0);

        List<Object[]> pedidos = pedidoDAO.listarTodos();
        for (Object[] fila : pedidos) {
            modeloTabla.addRow(fila);
        }
    }
}
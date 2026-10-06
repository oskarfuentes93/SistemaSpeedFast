package cl.speedfast.vista;

import cl.speedfast.dao.PedidoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// Ventana CRUD para gestionar pedidos
public class VentanaPedidos extends JFrame {

    private PedidoDAO dao;
    private DefaultTableModel modeloTabla;
    private JTable tabla;
    private JTextField txtDireccion;
    private JComboBox<String> comboTipo;
    private JComboBox<String> comboEstado;

    public VentanaPedidos() {
        this.dao = new PedidoDAO();

        setTitle("Gestión de Pedidos");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel del formulario
        JPanel panelForm = new JPanel(new GridLayout(3, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Pedido"));

        panelForm.add(new JLabel("Dirección:"));
        txtDireccion = new JTextField();
        panelForm.add(txtDireccion);

        panelForm.add(new JLabel("Tipo:"));
        comboTipo = new JComboBox<>(new String[]{"COMIDA", "ENCOMIENDA", "EXPRESS"});
        panelForm.add(comboTipo);

        panelForm.add(new JLabel("Estado:"));
        comboEstado = new JComboBox<>(new String[]{"PENDIENTE", "EN_REPARTO", "ENTREGADO"});
        panelForm.add(comboEstado);

        add(panelForm, BorderLayout.NORTH);

        // Tabla de pedidos
        String[] columnas = {"ID", "Dirección", "Tipo", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(25);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Al hacer clic en una fila, carga los datos en el formulario
        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                txtDireccion.setText(modeloTabla.getValueAt(fila, 1).toString());
                comboTipo.setSelectedItem(modeloTabla.getValueAt(fila, 2).toString());
                comboEstado.setSelectedItem(modeloTabla.getValueAt(fila, 3).toString());
            }
        });

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        add(panelBotones, BorderLayout.SOUTH);

        // Accion: Registrar pedido
        btnRegistrar.addActionListener(e -> {
            String direccion = txtDireccion.getText().trim();
            if (direccion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "La dirección es obligatoria.",
                        "Error de validación", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String tipo = (String) comboTipo.getSelectedItem();
            String estado = (String) comboEstado.getSelectedItem();
            if (dao.create(direccion, tipo, estado)) {
                JOptionPane.showMessageDialog(this, "Pedido registrado correctamente.");
                limpiarFormulario();
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar el pedido.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Accion: Editar pedido seleccionado
        btnEditar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona un pedido de la tabla.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String direccion = txtDireccion.getText().trim();
            if (direccion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "La dirección es obligatoria.",
                        "Error de validación", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int id = (int) modeloTabla.getValueAt(fila, 0);
            String tipo = (String) comboTipo.getSelectedItem();
            String estado = (String) comboEstado.getSelectedItem();
            if (dao.update(id, direccion, tipo, estado)) {
                JOptionPane.showMessageDialog(this, "Pedido actualizado correctamente.");
                limpiarFormulario();
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el pedido.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Accion: Eliminar pedido seleccionado
        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona un pedido de la tabla.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = (int) modeloTabla.getValueAt(fila, 0);
            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro de eliminar este pedido?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                if (dao.delete(id)) {
                    JOptionPane.showMessageDialog(this, "Pedido eliminado correctamente.");
                    limpiarFormulario();
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el pedido. Puede tener entregas asociadas.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Accion: Limpiar formulario
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        // Carga inicial
        cargarTabla();
    }

    // Consulta los pedidos de la BD y los muestra en la tabla
    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Object[]> datos = dao.readAll();
        for (Object[] fila : datos) {
            modeloTabla.addRow(fila);
        }
    }

    // Limpia los campos y deselecciona la tabla
    private void limpiarFormulario() {
        txtDireccion.setText("");
        comboTipo.setSelectedIndex(0);
        comboEstado.setSelectedIndex(0);
        tabla.clearSelection();
    }
}
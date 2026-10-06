package cl.speedfast.vista;

import cl.speedfast.dao.EntregaDAO;
import cl.speedfast.dao.PedidoDAO;
import cl.speedfast.dao.RepartidorDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// Ventana CRUD para gestionar entregas
public class VentanaEntregas extends JFrame {

    private EntregaDAO entregaDAO;
    private PedidoDAO pedidoDAO;
    private RepartidorDAO repartidorDAO;
    private DefaultTableModel modeloTabla;
    private JTable tabla;
    private JComboBox<String> comboPedido;
    private JComboBox<String> comboRepartidor;
    private JTextField txtFecha;
    private JTextField txtHora;

    public VentanaEntregas() {
        this.entregaDAO = new EntregaDAO();
        this.pedidoDAO = new PedidoDAO();
        this.repartidorDAO = new RepartidorDAO();

        setTitle("Gestión de Entregas");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel del formulario
        JPanel panelForm = new JPanel(new GridLayout(4, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos de la Entrega"));

        panelForm.add(new JLabel("Pedido:"));
        comboPedido = new JComboBox<>();
        panelForm.add(comboPedido);

        panelForm.add(new JLabel("Repartidor:"));
        comboRepartidor = new JComboBox<>();
        panelForm.add(comboRepartidor);

        panelForm.add(new JLabel("Fecha (YYYY-MM-DD):"));
        txtFecha = new JTextField();
        panelForm.add(txtFecha);

        panelForm.add(new JLabel("Hora (HH:MM):"));
        txtHora = new JTextField();
        panelForm.add(txtHora);

        add(panelForm, BorderLayout.NORTH);

        // Tabla de entregas
        String[] columnas = {"ID", "ID Pedido", "Dirección", "ID Repartidor", "Repartidor", "Fecha", "Hora"};
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
                String idPedido = modeloTabla.getValueAt(fila, 1).toString();
                String direccion = modeloTabla.getValueAt(fila, 2).toString();
                String idRepartidor = modeloTabla.getValueAt(fila, 3).toString();
                String nombreRep = modeloTabla.getValueAt(fila, 4).toString();
                txtFecha.setText(modeloTabla.getValueAt(fila, 5).toString());
                txtHora.setText(modeloTabla.getValueAt(fila, 6).toString());

                // Seleccionar el item correcto en los combos
                for (int i = 0; i < comboPedido.getItemCount(); i++) {
                    if (comboPedido.getItemAt(i).startsWith(idPedido + " - ")) {
                        comboPedido.setSelectedIndex(i);
                        break;
                    }
                }
                for (int i = 0; i < comboRepartidor.getItemCount(); i++) {
                    if (comboRepartidor.getItemAt(i).startsWith(idRepartidor + " - ")) {
                        comboRepartidor.setSelectedIndex(i);
                        break;
                    }
                }
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

        // Accion: Registrar entrega
        btnRegistrar.addActionListener(e -> {
            if (!validarCampos()) return;
            int idPedido = extraerId(comboPedido);
            int idRepartidor = extraerId(comboRepartidor);
            String fecha = txtFecha.getText().trim();
            String hora = txtHora.getText().trim();
            entregaDAO.create(idPedido, idRepartidor, fecha, hora);
            JOptionPane.showMessageDialog(this, "Entrega registrada correctamente.");
            limpiarFormulario();
            cargarTabla();
        });

        // Accion: Editar entrega seleccionada
        btnEditar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona una entrega de la tabla.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!validarCampos()) return;
            int id = (int) modeloTabla.getValueAt(fila, 0);
            int idPedido = extraerId(comboPedido);
            int idRepartidor = extraerId(comboRepartidor);
            String fecha = txtFecha.getText().trim();
            String hora = txtHora.getText().trim();
            entregaDAO.update(id, idPedido, idRepartidor, fecha, hora);
            JOptionPane.showMessageDialog(this, "Entrega actualizada correctamente.");
            limpiarFormulario();
            cargarTabla();
        });

        // Accion: Eliminar entrega seleccionada
        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona una entrega de la tabla.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = (int) modeloTabla.getValueAt(fila, 0);
            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro de eliminar esta entrega?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                entregaDAO.delete(id);
                JOptionPane.showMessageDialog(this, "Entrega eliminada correctamente.");
                limpiarFormulario();
                cargarTabla();
            }
        });

        // Accion: Limpiar formulario
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        // Carga inicial
        cargarCombos();
        cargarTabla();
    }

    // Carga los combos con datos de pedidos y repartidores desde la BD
    private void cargarCombos() {
        comboPedido.removeAllItems();
        List<Object[]> pedidos = pedidoDAO.readAll();
        for (Object[] p : pedidos) {
            comboPedido.addItem(p[0] + " - " + p[1]);
        }

        comboRepartidor.removeAllItems();
        List<Object[]> repartidores = repartidorDAO.readAll();
        for (Object[] r : repartidores) {
            comboRepartidor.addItem(r[0] + " - " + r[1]);
        }
    }

    // Extrae el ID numerico del texto del combo (formato "ID - texto")
    private int extraerId(JComboBox<String> combo) {
        String seleccion = (String) combo.getSelectedItem();
        return Integer.parseInt(seleccion.split(" - ")[0]);
    }

    // Valida que los campos obligatorios esten completos
    private boolean validarCampos() {
        if (comboPedido.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay pedidos registrados. Registra un pedido primero.",
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (comboRepartidor.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay repartidores registrados. Registra un repartidor primero.",
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        String fecha = txtFecha.getText().trim();
        String hora = txtHora.getText().trim();
        if (fecha.isEmpty() || hora.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La fecha y la hora son obligatorias.",
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!fecha.matches("\\d{4}-\\d{2}-\\d{2}")) {
            JOptionPane.showMessageDialog(this, "La fecha debe tener formato YYYY-MM-DD.",
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!hora.matches("\\d{2}:\\d{2}")) {
            JOptionPane.showMessageDialog(this, "La hora debe tener formato HH:MM.",
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // Consulta las entregas de la BD y las muestra en la tabla
    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Object[]> datos = entregaDAO.readAll();
        for (Object[] fila : datos) {
            modeloTabla.addRow(fila);
        }
    }

    // Limpia los campos y deselecciona la tabla
    private void limpiarFormulario() {
        txtFecha.setText("");
        txtHora.setText("");
        if (comboPedido.getItemCount() > 0) comboPedido.setSelectedIndex(0);
        if (comboRepartidor.getItemCount() > 0) comboRepartidor.setSelectedIndex(0);
        tabla.clearSelection();
        cargarCombos();
    }
}
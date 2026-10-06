package cl.speedfast.vista;

import cl.speedfast.dao.RepartidorDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// Ventana CRUD para gestionar repartidores
public class VentanaRepartidores extends JFrame {

    private RepartidorDAO dao;
    private DefaultTableModel modeloTabla;
    private JTable tabla;
    private JTextField txtNombre;

    public VentanaRepartidores() {
        this.dao = new RepartidorDAO();

        setTitle("Gestión de Repartidores");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel del formulario
        JPanel panelForm = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Repartidor"));

        panelForm.add(new JLabel("Nombre:"));
        txtNombre = new JTextField(20);
        panelForm.add(txtNombre);

        add(panelForm, BorderLayout.NORTH);

        // Tabla de repartidores
        String[] columnas = {"ID", "Nombre"};
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
                txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
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

        // Accion: Registrar repartidor
        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre es obligatorio.",
                        "Error de validación", JOptionPane.ERROR_MESSAGE);
                return;
            }
            dao.create(nombre);
            JOptionPane.showMessageDialog(this, "Repartidor registrado correctamente.");
            limpiarFormulario();
            cargarTabla();
        });

        // Accion: Editar repartidor seleccionado
        btnEditar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona un repartidor de la tabla.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String nombre = txtNombre.getText().trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre es obligatorio.",
                        "Error de validación", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int id = (int) modeloTabla.getValueAt(fila, 0);
            dao.update(id, nombre);
            JOptionPane.showMessageDialog(this, "Repartidor actualizado correctamente.");
            limpiarFormulario();
            cargarTabla();
        });

        // Accion: Eliminar repartidor seleccionado
        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona un repartidor de la tabla.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = (int) modeloTabla.getValueAt(fila, 0);
            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro de eliminar este repartidor?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                dao.delete(id);
                JOptionPane.showMessageDialog(this, "Repartidor eliminado correctamente.");
                limpiarFormulario();
                cargarTabla();
            }
        });

        // Accion: Limpiar formulario
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        // Carga inicial
        cargarTabla();
    }

    // Consulta los repartidores de la BD y los muestra en la tabla
    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Object[]> datos = dao.readAll();
        for (Object[] fila : datos) {
            modeloTabla.addRow(fila);
        }
    }

    // Limpia el campo de texto y deselecciona la tabla
    private void limpiarFormulario() {
        txtNombre.setText("");
        tabla.clearSelection();
    }
}
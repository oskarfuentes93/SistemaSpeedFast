package cl.speedfast.vista;

import cl.speedfast.modelo.ControladorDeEnvios;
import cl.speedfast.modelo.Pedido;
import cl.speedfast.modelo.PedidoComida;
import cl.speedfast.modelo.PedidoEncomienda;
import cl.speedfast.modelo.PedidoExpress;

import javax.swing.*;
import java.awt.*;

public class VentanaRegistroPedido extends JFrame {

    private ControladorDeEnvios controlador;

    // Campos del formulario
    private JTextField txtId;
    private JTextField txtDireccion;
    private JTextField txtDistancia;
    private JComboBox<String> comboTipo;

    public VentanaRegistroPedido(ControladorDeEnvios controlador) {
        this.controlador = controlador;

        setTitle("Registrar Pedido");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // cierra solo esta ventana, no la app

        // Panel con los campos, ordenados en una grilla de etiqueta + campo
        JPanel panelForm = new JPanel(new GridLayout(4, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        panelForm.add(new JLabel("ID del pedido:"));
        txtId = new JTextField();
        panelForm.add(txtId);

        panelForm.add(new JLabel("Direccion de entrega:"));
        txtDireccion = new JTextField();
        panelForm.add(txtDireccion);

        panelForm.add(new JLabel("Distancia (km):"));
        txtDistancia = new JTextField();
        panelForm.add(txtDistancia);

        panelForm.add(new JLabel("Tipo de pedido:"));
        comboTipo = new JComboBox<>(new String[]{"Comida", "Encomienda", "Express"});
        panelForm.add(comboTipo);

        add(panelForm, BorderLayout.CENTER);

        // Boton Guardar abajo
        JButton btnGuardar = new JButton("Guardar");
        JPanel panelBoton = new JPanel();
        panelBoton.add(btnGuardar);
        add(panelBoton, BorderLayout.SOUTH);

        // Accion del boton Guardar: valida, crea el pedido y lo agrega al controlador
        btnGuardar.addActionListener(e -> guardarPedido());
    }

    private void guardarPedido() {
        String textoId = txtId.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String textoDistancia = txtDistancia.getText().trim();

        // Validacion 1: campos no vacios
        if (textoId.isEmpty() || direccion.isEmpty() || textoDistancia.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Todos los campos son obligatorios.",
                    "Error de validacion", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validacion 2: ID y distancia deben ser numeros
        int id;
        int distancia;
        try {
            id = Integer.parseInt(textoId);
            distancia = Integer.parseInt(textoDistancia);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "El ID y la distancia deben ser numeros enteros.",
                    "Error de validacion", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Se crea el pedido segun el tipo elegido en el combo
        String tipo = (String) comboTipo.getSelectedItem();
        Pedido pedido;
        if (tipo.equals("Comida")) {
            pedido = new PedidoComida(id, direccion, distancia);
        } else if (tipo.equals("Encomienda")) {
            pedido = new PedidoEncomienda(id, direccion, distancia);
        } else {
            pedido = new PedidoExpress(id, direccion, distancia);
        }

        // Se agrega al controlador compartido (lista en memoria)
        controlador.registrarEntrega(pedido);

        // Confirmacion
        JOptionPane.showMessageDialog(this,
                "Pedido #" + id + " (" + tipo + ") registrado correctamente.",
                "Exito", JOptionPane.INFORMATION_MESSAGE);

        // Limpia el formulario para el siguiente registro
        txtId.setText("");
        txtDireccion.setText("");
        txtDistancia.setText("");
        comboTipo.setSelectedIndex(0);
    }
}
package cl.speedfast.vista;

import cl.speedfast.modelo.ControladorDeEnvios;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    // Controlador compartido: guarda los pedidos en memoria y se pasa a las demas ventanas
    private ControladorDeEnvios controlador;

    public VentanaPrincipal() {
        // Se crea el controlador una sola vez; todas las ventanas usaran este mismo
        controlador = new ControladorDeEnvios();

        // Configuracion basica de la ventana
        setTitle("SpeedFast - Sistema de Gestion de Entregas");
        setSize(450, 300);
        setLocationRelativeTo(null);            // centra la ventana en pantalla
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel superior con el titulo
        JLabel titulo = new JLabel("Gestion de Entregas SpeedFast", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(titulo, BorderLayout.NORTH);

        // Panel central con los botones, uno debajo del otro
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 40, 30, 40));

        JButton btnRegistrar = new JButton("Registrar pedido");
        JButton btnListar = new JButton("Listar pedidos");
        JButton btnAsignar = new JButton("Asignar repartidor / Iniciar entrega");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnListar);
        panelBotones.add(btnAsignar);

        add(panelBotones, BorderLayout.CENTER);

        // Accion del boton "Registrar pedido": abre la ventana de registro
        btnRegistrar.addActionListener(e -> {
            VentanaRegistroPedido ventana = new VentanaRegistroPedido(controlador);
            ventana.setVisible(true);
        });

        // Accion del boton "Listar pedidos": abre la ventana con la tabla
        btnListar.addActionListener(e -> {
            VentanaListaPedidos ventana = new VentanaListaPedidos(controlador);
            ventana.setVisible(true);
        });

        // Accion del boton "Asignar repartidor / Iniciar entrega"
        btnAsignar.addActionListener(e -> {
            if (controlador.getHistorial().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No hay pedidos registrados para asignar.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                // Asigna repartidor y despacha cada pedido registrado
                controlador.getHistorial().forEach(p -> {
                    p.asignarRepartidor();
                    p.despachar();
                });
                JOptionPane.showMessageDialog(this,
                        "Repartidores asignados y entregas iniciadas.",
                        "Entregas", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}
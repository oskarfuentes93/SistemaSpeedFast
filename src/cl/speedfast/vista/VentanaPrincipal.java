package cl.speedfast.vista;

import javax.swing.*;
import java.awt.*;

// Ventana principal del sistema SpeedFast
public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {
        setTitle("SpeedFast - Sistema de Gestión de Entregas");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Titulo superior
        JLabel titulo = new JLabel("Gestión de Entregas SpeedFast", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(titulo, BorderLayout.NORTH);

        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 40, 30, 40));

        JButton btnRepartidores = new JButton("Gestionar Repartidores");
        JButton btnPedidos = new JButton("Gestionar Pedidos");
        JButton btnEntregas = new JButton("Gestionar Entregas");

        panelBotones.add(btnRepartidores);
        panelBotones.add(btnPedidos);
        panelBotones.add(btnEntregas);

        add(panelBotones, BorderLayout.CENTER);

        // Accion: abrir ventana de repartidores
        btnRepartidores.addActionListener(e -> {
            VentanaRepartidores ventana = new VentanaRepartidores();
            ventana.setVisible(true);
        });

        // Accion: abrir ventana de pedidos
        btnPedidos.addActionListener(e -> {
            VentanaPedidos ventana = new VentanaPedidos();
            ventana.setVisible(true);
        });

        // Accion: abrir ventana de entregas
        btnEntregas.addActionListener(e -> {
            VentanaEntregas ventana = new VentanaEntregas();
            ventana.setVisible(true);
        });
    }
}
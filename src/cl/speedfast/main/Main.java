package cl.speedfast.main;

import cl.speedfast.vista.VentanaPrincipal;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Inicia la aplicacion abriendo la ventana principal.
        // SwingUtilities.invokeLater asegura que la interfaz se cree en el hilo correcto de Swing.
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}
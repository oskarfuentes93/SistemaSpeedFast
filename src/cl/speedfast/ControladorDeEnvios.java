package cl.speedfast;

import java.util.ArrayList;

public class ControladorDeEnvios implements Rastreable {
    private ArrayList<Pedido> historial = new ArrayList<>();

    public void registrarEntrega(Pedido pedido) {
        historial.add(pedido);
    }

    @Override
    public void verHistorial() {
        System.out.println("=== Historial de entregas ===");
        if (historial.isEmpty()) {
            System.out.println("No hay entregas registradas.");
        } else {
            for (Pedido pedido : historial) {
                System.out.println("Pedido #" + pedido.getIdPedido() + " - " + pedido.getClass().getSimpleName());
            }
        }
    }
}
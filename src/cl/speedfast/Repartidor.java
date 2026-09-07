package cl.speedfast;

import java.util.List;
import java.util.Random;

public class Repartidor implements Runnable {
    private String nombre;
    private List<Pedido> pedidosAsignados;
    private Random random = new Random();

    public Repartidor(String nombre, List<Pedido> pedidosAsignados) {
        this.nombre = nombre;
        this.pedidosAsignados = pedidosAsignados;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Pedido> getPedidosAsignados() {
        return pedidosAsignados;
    }

    @Override
    public void run() {
        System.out.println("[Repartidor " + nombre + "] Inicia su ruta con " + pedidosAsignados.size() + " pedidos.");

        for (Pedido pedido : pedidosAsignados) {
            System.out.println("[Repartidor " + nombre + "] Saliendo a entregar pedido #"
                    + pedido.getIdPedido() + " (" + pedido.getClass().getSimpleName()
                    + "), tiempo estimado " + pedido.calcularTiempoEntrega() + " min.");

            try {
                int demora = 1000 + random.nextInt(2000); // entre 1000 y 2999 ms
                Thread.sleep(demora);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("[Repartidor " + nombre + "] Interrumpido durante la entrega del pedido #"
                        + pedido.getIdPedido() + ".");
                return;
            }

            System.out.println("[Repartidor " + nombre + "] Pedido #" + pedido.getIdPedido() + " entregado.");
        }

        System.out.println("[Repartidor " + nombre + "] Termino todas sus entregas.");
    }
}
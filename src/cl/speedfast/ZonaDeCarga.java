package cl.speedfast;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ZonaDeCarga {
    private BlockingQueue<Pedido> pedidos;

    public ZonaDeCarga() {
        this.pedidos = new LinkedBlockingQueue<>();
        System.out.println("[Zona de carga inicializada]");
    }

    public synchronized void agregarPedido(Pedido p) {
        pedidos.add(p);
        System.out.println("Pedido #" + p.getIdPedido() + " agregado. Destino: " + p.getDireccionEntrega());
    }

    public synchronized Pedido retirarPedido() {
        return pedidos.poll();
    }
}
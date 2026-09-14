package cl.speedfast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        ZonaDeCarga zonaDeCarga = new ZonaDeCarga();

        zonaDeCarga.agregarPedido(new PedidoComida(1, "Av. Italia 456", 4));
        zonaDeCarga.agregarPedido(new PedidoExpress(2, "Av. Apoquindo 1500", 7));
        zonaDeCarga.agregarPedido(new PedidoEncomienda(3, "Av. Independencia 123", 6));
        zonaDeCarga.agregarPedido(new PedidoComida(4, "Los Leones 890", 3));
        zonaDeCarga.agregarPedido(new PedidoExpress(5, "Av. Kennedy 5200", 9));
        zonaDeCarga.agregarPedido(new PedidoEncomienda(6, "Gran Avenida 2100", 12));

        System.out.println();

        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.submit(new Repartidor("Carlos", zonaDeCarga));
        executor.submit(new Repartidor("Laura", zonaDeCarga));
        executor.submit(new Repartidor("Javiera", zonaDeCarga));

        executor.shutdown();

        try {
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                System.out.println("[Main] Algunos repartidores no finalizaron a tiempo.");
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            executor.shutdownNow();
        }

        System.out.println("\nTodos los pedidos han sido entregados correctamente");
    }
}
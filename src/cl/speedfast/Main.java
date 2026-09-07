package cl.speedfast;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        ControladorDeEnvios controlador = new ControladorDeEnvios();

        Pedido comida1 = new PedidoComida(1, "Av. Italia 456", 4);
        Pedido express1 = new PedidoExpress(2, "Av. Apoquindo 1500", 7);
        Pedido encomienda1 = new PedidoEncomienda(3, "Av. Independencia 123", 6);
        Pedido comida2 = new PedidoComida(4, "Los Leones 890", 3);
        Pedido express2 = new PedidoExpress(5, "Av. Kennedy 5200", 9);
        Pedido encomienda2 = new PedidoEncomienda(6, "Gran Avenida 2100", 12);

        List<Pedido> ruta1 = List.of(comida1, express1);
        List<Pedido> ruta2 = List.of(encomienda1, comida2);
        List<Pedido> ruta3 = List.of(express2, encomienda2);

        Repartidor r1 = new Repartidor("Carlos", ruta1);
        Repartidor r2 = new Repartidor("Laura", ruta2);
        Repartidor r3 = new Repartidor("Javiera", ruta3);

        System.out.println("=== SpeedFast: entregas concurrentes ===");

        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.execute(r1);
        executor.execute(r2);
        executor.execute(r3);

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

        System.out.println();
        System.out.println("[Main] Todos los repartidores terminaron sus entregas.");
        System.out.println();

        for (Pedido pedido : ruta1) {
            controlador.registrarEntrega(pedido);
        }
        for (Pedido pedido : ruta2) {
            controlador.registrarEntrega(pedido);
        }
        for (Pedido pedido : ruta3) {
            controlador.registrarEntrega(pedido);
        }

        controlador.verHistorial();
    }
}
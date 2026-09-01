package cl.speedfast;

public class Main {
    public static void main(String[] args) {
        ControladorDeEnvios controlador = new ControladorDeEnvios();

        Pedido comida = new PedidoComida(1, "Av. Italia 456", 4);
        Pedido encomienda = new PedidoEncomienda(2, "Av. Independencia 123", 6);
        Pedido express = new PedidoExpress(3, "Av. Apoquindo 1500", 7);

        System.out.println("--- Asignacion de repartidores ---");
        comida.asignarRepartidor();
        encomienda.asignarRepartidor();
        express.asignarRepartidor("Carlos Soto");
        System.out.println();

        Pedido[] pedidos = { comida, encomienda, express };

        System.out.println("--- Despacho de pedidos ---");
        for (Pedido pedido : pedidos) {
            pedido.mostrarResumen();
            System.out.println("Tiempo estimado: " + pedido.calcularTiempoEntrega() + " min");
            pedido.despachar();
            controlador.registrarEntrega(pedido);
            System.out.println();
        }

        System.out.println("--- Cancelacion ---");
        encomienda.cancelar();
        System.out.println();

        controlador.verHistorial();
    }
}
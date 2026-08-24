package cl.speedfast;

public class Main {
    public static void main(String[] args) {
        PedidoComida pedidoComida = new PedidoComida(1, "Av. Grecia 1234");
        PedidoEncomienda pedidoEncomienda = new PedidoEncomienda(2, "Calle Los Aromos 567");
        PedidoExpress pedidoExpress = new PedidoExpress(3, "Pasaje El Roble 89");

        Pedido[] pedidos = { pedidoComida, pedidoEncomienda, pedidoExpress };

        System.out.println("=== Asignacion general (sobrescritura) ===");
        for (Pedido pedido : pedidos) {
            pedido.asignarRepartidor();
        }

        System.out.println("\n=== Asignacion con nombre de repartidor (sobrecarga) ===");
        pedidoComida.asignarRepartidor("Carlos");
        pedidoEncomienda.asignarRepartidor("Maria");
        pedidoExpress.asignarRepartidor("Diego");
    }
}
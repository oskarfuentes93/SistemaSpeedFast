package cl.speedfast;

public class PedidoComida extends Pedido {

    public PedidoComida(int idPedido, String direccionEntrega) {
        super(idPedido, direccionEntrega, "Comida");
    }

    @Override
    public void asignarRepartidor() {
        System.out.println("Pedido de comida " + getIdPedido()
                + ": se necesita un repartidor con mochila termica disponible.");
    }

    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("Pedido de comida " + getIdPedido() + ": el repartidor "
                + nombreRepartidor + " fue asignado. Se verifica que cuente con mochila termica.");
    }
}
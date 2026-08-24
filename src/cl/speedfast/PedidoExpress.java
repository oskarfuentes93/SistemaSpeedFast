package cl.speedfast;

public class PedidoExpress extends Pedido {

    public PedidoExpress(int idPedido, String direccionEntrega) {
        super(idPedido, direccionEntrega, "Compra Express");
    }

    @Override
    public void asignarRepartidor() {
        System.out.println("Pedido express " + getIdPedido()
                + ": se asigna al repartidor mas cercano con disponibilidad inmediata.");
    }

    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("Pedido express " + getIdPedido() + ": el repartidor "
                + nombreRepartidor + " fue asignado por ser el mas cercano y estar disponible de inmediato.");
    }
}
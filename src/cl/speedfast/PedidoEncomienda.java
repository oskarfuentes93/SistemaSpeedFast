package cl.speedfast;

public class PedidoEncomienda extends Pedido {

    public PedidoEncomienda(int idPedido, String direccionEntrega) {
        super(idPedido, direccionEntrega, "Encomienda");
    }

    @Override
    public void asignarRepartidor() {
        System.out.println("Pedido de encomienda " + getIdPedido()
                + ": se necesita un repartidor que valide peso y embalaje.");
    }

    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("Pedido de encomienda " + getIdPedido() + ": el repartidor "
                + nombreRepartidor + " fue asignado. Se valida el peso y el embalaje del paquete.");
    }
}
package cl.speedfast;

public class PedidoEncomienda extends Pedido {

    public PedidoEncomienda(int idPedido, String direccionEntrega, int distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm);
    }

    @Override
    public int calcularTiempoEntrega() {
        return (int) (20 + 1.5 * getDistanciaKm());
    }

    @Override
    public void asignarRepartidor() {
        setRepartidor("Repartidor en camioneta");
        System.out.println("Pedido #" + getIdPedido() + ": encomienda asignada a repartidor en camioneta.");
    }
}
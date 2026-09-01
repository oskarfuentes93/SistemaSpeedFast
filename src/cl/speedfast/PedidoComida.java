package cl.speedfast;

public class PedidoComida extends Pedido {

    public PedidoComida(int idPedido, String direccionEntrega, int distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm);
    }

    @Override
    public int calcularTiempoEntrega() {
        return 15 + 2 * getDistanciaKm();
    }

    @Override
    public void asignarRepartidor() {
        setRepartidor("Repartidor en moto");
        System.out.println("Pedido #" + getIdPedido() + ": comida asignada a repartidor en moto.");
    }
}
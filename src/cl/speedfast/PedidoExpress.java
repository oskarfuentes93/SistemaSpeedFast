package cl.speedfast;

public class PedidoExpress extends Pedido {

    public PedidoExpress(int idPedido, String direccionEntrega, int distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm);
    }

    @Override
    public int calcularTiempoEntrega() {
        int tiempo = 10;
        if (getDistanciaKm() > 5) {
            tiempo = tiempo + 5;
        }
        return tiempo;
    }

    @Override
    public void asignarRepartidor() {
        setRepartidor("Repartidor en bicicleta");
        System.out.println("Pedido #" + getIdPedido() + ": express asignada a repartidor en bicicleta.");
    }
}
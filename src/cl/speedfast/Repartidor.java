package cl.speedfast;

public class Repartidor implements Runnable {
    private String nombre;
    private ZonaDeCarga zonaDeCarga;

    public Repartidor(String nombre, ZonaDeCarga zonaDeCarga) {
        this.nombre = nombre;
        this.zonaDeCarga = zonaDeCarga;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public void run() {
        Pedido pedido;
        while ((pedido = zonaDeCarga.retirarPedido()) != null) {
            System.out.println("[Repartidor - " + nombre + "] Retirando pedido #" + pedido.getIdPedido() + "...");
            pedido.setEstado(EstadoPedido.EN_REPARTO);
            System.out.println("[Repartidor - " + nombre + "] Estado: " + pedido.getEstado());

            System.out.println("[Repartidor - " + nombre + "] Entregando pedido #" + pedido.getIdPedido() + "...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            pedido.setEstado(EstadoPedido.ENTREGADO);
            System.out.println("[Repartidor - " + nombre + "] Estado: " + pedido.getEstado());
        }
    }
}
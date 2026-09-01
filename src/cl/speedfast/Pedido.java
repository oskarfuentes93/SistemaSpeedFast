package cl.speedfast;

public abstract class Pedido implements Despachable, Cancelable {
    private int idPedido;
    private String direccionEntrega;
    private int distanciaKm;
    private String repartidor;

    public Pedido(int idPedido, String direccionEntrega, int distanciaKm) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.distanciaKm = distanciaKm;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public int getDistanciaKm() {
        return distanciaKm;
    }

    public String getRepartidor() {
        return repartidor;
    }

    protected void setRepartidor(String repartidor) {
        this.repartidor = repartidor;
    }

    public void mostrarResumen() {
        System.out.println(getClass().getSimpleName() + " #" + idPedido);
        System.out.println("Direccion: " + direccionEntrega);
        System.out.println("Distancia: " + distanciaKm + " km");
    }

    public abstract int calcularTiempoEntrega();

    public void asignarRepartidor() {
        this.repartidor = "Repartidor generico";
        System.out.println("Pedido #" + idPedido + ": repartidor asignado automaticamente -> " + repartidor);
    }

    public void asignarRepartidor(String nombre) {
        this.repartidor = nombre;
        System.out.println("Pedido #" + idPedido + ": repartidor asignado manualmente -> " + repartidor);
    }

    @Override
    public void despachar() {
        System.out.println("Pedido #" + idPedido + " despachado con " + repartidor + ". Tiempo estimado: " + calcularTiempoEntrega() + " min.");
    }

    @Override
    public void cancelar() {
        System.out.println("Pedido #" + idPedido + " ha sido cancelado.");
    }
}

# SistemaSpeedFast

Sistema orientado a objetos para la empresa de reparto SpeedFast, desarrollado en Java (IntelliJ IDEA) para el ramo Desarrollo Orientado a Objetos II (DuocUC).

## Semana 4 - Concurrencia con hilos

Simulacion de repartidores entregando pedidos de forma concurrente.

- `Pedido` (abstracta) + subclases `PedidoComida`, `PedidoEncomienda`, `PedidoExpress`.
- Interfaces `Despachable`, `Cancelable`, `Rastreable`.
- `Repartidor` implementa `Runnable`: entrega sus pedidos de forma secuencial simulando el tiempo con `Thread.sleep()`.
- `Main` lanza 3 repartidores en paralelo con `ExecutorService` y muestra el historial de entregas al finalizar.

### Ejecucion
Ejecutar la clase `Main` (`src/cl/speedfast/Main.java`).

# SistemaSpeedFast

Sistema orientado a objetos para la empresa de reparto SpeedFast, desarrollado en Java (IntelliJ IDEA) para el ramo Desarrollo Orientado a Objetos II (DuocUC).

## Estructura del proyecto

El proyecto está organizado en paquetes:

- `cl.speedfast.modelo` : clases del dominio (Pedido y subclases, Repartidor, ControladorDeEnvios, ZonaDeCarga, interfaces y enum).
- `cl.speedfast.vista` : interfaces gráficas Swing (ventanas).
- `cl.speedfast.main` : clase Main que inicia la aplicación.

## Semana 6 - Interfaces gráficas con Swing

Interfaz gráfica de escritorio para la gestión de entregas, construida con Java Swing.

- `VentanaPrincipal` (JFrame) con botones para registrar pedidos, listar pedidos y asignar repartidor / iniciar entrega.
- `VentanaRegistroPedido` (JFrame) con formulario (ID, dirección, distancia, tipo mediante JComboBox), validación de datos y confirmación con JOptionPane.
- `VentanaListaPedidos` (JFrame) con JTable y DefaultTableModel, con opción de refrescar.
- Navegación entre ventanas y datos compartidos en memoria mediante ControladorDeEnvios.

## Semanas anteriores

- Semana 4 - Concurrencia con hilos: simulación de repartidores entregando pedidos de forma concurrente con ExecutorService.
- Semana 5 - Sincronización de procesos: acceso concurrente a la zona de carga compartida.

## Ejecución

Ejecutar la clase `Main` (`src/cl/speedfast/main/Main.java`).

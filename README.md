# SistemaSpeedFast

Sistema orientado a objetos para la empresa de reparto SpeedFast, desarrollado en Java con IntelliJ IDEA para el ramo Desarrollo Orientado a Objetos II (PRY2203) de DuocUC.

El proyecto ha evolucionado semana a semana, partiendo de un modelo de clases con herencia e interfaces, incorporando luego concurrencia con hilos, una interfaz gráfica de escritorio con Java Swing y, finalmente, persistencia de datos con MySQL mediante JDBC.

## Tecnologías

* Java (JDK 21)
* IntelliJ IDEA
* Java Swing (interfaces gráficas)
* API de concurrencia de Java (`ExecutorService`, `BlockingQueue`)
* MySQL 8.0 (base de datos relacional)
* JDBC (conexión Java - MySQL)

## Estructura del proyecto

El código está organizado en paquetes según su responsabilidad:

* `cl.speedfast.modelo` : clases del dominio (lógica de negocio).
* `cl.speedfast.dao` : clases de acceso a datos (conexión y operaciones JDBC).
* `cl.speedfast.vista` : interfaces gráficas Swing (ventanas).
* `cl.speedfast.main` : clase `Main` que inicia la aplicación.

## Modelo de dominio

### Jerarquía de pedidos

* `Pedido` (clase abstracta): define los atributos comunes (id, dirección de entrega, distancia, repartidor y estado) y el método abstracto `calcularTiempoEntrega()`. Implementa las interfaces `Despachable` y `Cancelable`.
* `PedidoComida`, `PedidoEncomienda`, `PedidoExpress` (subclases): cada una redefine `calcularTiempoEntrega()` con su propia fórmula y `asignarRepartidor()` con su tipo de repartidor (moto, camioneta, bicicleta). Ejemplo de herencia y polimorfismo.

### Interfaces

* `Despachable` : define `despachar()`.
* `Cancelable` : define `cancelar()`.
* `Rastreable` : define `verHistorial()`.

### Otras clases

* `EstadoPedido` (enum): PENDIENTE, EN_REPARTO, ENTREGADO.
* `Repartidor` : implementa `Runnable`; retira y entrega pedidos desde la zona de carga.
* `ZonaDeCarga` : cola de pedidos compartida (`BlockingQueue`) con acceso sincronizado.
* `ControladorDeEnvios` : implementa `Rastreable`; almacena el historial de pedidos en memoria.
* `Entrega` : representa la relación entre un pedido y un repartidor, con fecha y hora.

### Capa de acceso a datos (DAO)

* `ConexionDB` : clase utilitaria que centraliza la conexión JDBC a MySQL mediante `DriverManager`.
* `PedidoDAO` : inserta y consulta pedidos en la base de datos.
* `RepartidorDAO` : inserta y lista repartidores desde MySQL.
* `EntregaDAO` : registra entregas asociando un pedido con un repartidor.

## Evolución por semanas

### Herencia, polimorfismo e interfaces
Diseño de la jerarquía `Pedido` con sus subclases, aplicando clases abstractas, polimorfismo y las interfaces `Despachable`, `Cancelable` y `Rastreable`.

### Semana 4 - Concurrencia con hilos
Simulación de repartidores entregando pedidos de forma concurrente. La clase `Repartidor` implementa `Runnable` y la clase `Main` lanza varios repartidores en paralelo mediante un `ExecutorService` con un pool de hilos.

### Semana 5 - Sincronización de procesos
Manejo del acceso concurrente a la `ZonaDeCarga` compartida. Se usa una `BlockingQueue` y métodos sincronizados para que varios repartidores retiren pedidos sin conflictos, gestionando correctamente los estados de cada pedido (PENDIENTE → EN_REPARTO → ENTREGADO).

### Semana 6 - Interfaces gráficas con Swing
Interfaz gráfica de escritorio para la gestión de entregas:

* `VentanaPrincipal` (JFrame): ventana de inicio con botones para registrar pedidos, listar pedidos y asignar repartidor / iniciar entrega. Usa `BorderLayout` y `GridLayout`.
* `VentanaRegistroPedido` (JFrame): formulario con campos ID, dirección, distancia y tipo de pedido (`JComboBox`). Valida los datos ingresados y confirma el registro con `JOptionPane`.
* `VentanaListaPedidos` (JFrame): muestra los pedidos en una `JTable` gestionada con `DefaultTableModel`, con opción de refrescar.
* La navegación entre ventanas y el almacenamiento en memoria se comparten a través de `ControladorDeEnvios`.

### Semana 7 - Conexión JDBC con MySQL
Persistencia de datos conectando la aplicación Java con una base de datos MySQL mediante JDBC:

* Base de datos `speedfast_db` con tres tablas: `repartidor`, `pedido` y `entrega`, relacionadas mediante claves foráneas.
* `ConexionDB` centraliza la conexión JDBC usando `DriverManager` con manejo de excepciones.
* Clases DAO (`PedidoDAO`, `RepartidorDAO`, `EntregaDAO`) implementan operaciones INSERT y SELECT usando `PreparedStatement` y `ResultSet`, con cierre automático de recursos mediante try-with-resources.
* La interfaz gráfica fue integrada con la base de datos: `VentanaRegistroPedido` ahora guarda los pedidos en MySQL, y `VentanaListaPedidos` consulta y muestra los datos almacenados en la base de datos a través de `JTable`.

## Ejecución

Ejecutar la clase `Main` ubicada en `src/cl/speedfast/main/Main.java`. Al iniciar, se abre la ventana principal de la aplicación (`VentanaPrincipal`).

**Requisitos:** MySQL 8.0 instalado con la base de datos `speedfast_db` creada. Configurar usuario y contraseña en `ConexionDB.java`.

## Autor

Oscar Fuentes - DuocUC

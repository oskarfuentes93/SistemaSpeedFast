# SistemaSpeedFast

Sistema orientado a objetos para la empresa de reparto SpeedFast, desarrollado en Java con IntelliJ IDEA para el ramo Desarrollo Orientado a Objetos II (PRY2203) de DuocUC.

El proyecto ha evolucionado semana a semana, partiendo de un modelo de clases con herencia e interfaces, incorporando luego concurrencia con hilos, una interfaz gráfica de escritorio con Java Swing y persistencia de datos con MySQL mediante JDBC, hasta llegar a un sistema CRUD completo integrado con base de datos.

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
* `cl.speedfast.dao` : clases de acceso a datos (conexión y operaciones CRUD con JDBC).
* `cl.speedfast.vista` : interfaces gráficas Swing (ventanas CRUD).
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
* `Repartidor` : implementa `Runnable`; retira y entrega pedidos desde la zona de carga. Incluye campo `id` para persistencia en BD.
* `ZonaDeCarga` : cola de pedidos compartida (`BlockingQueue`) con acceso sincronizado.
* `ControladorDeEnvios` : implementa `Rastreable`; almacena el historial de pedidos en memoria.
* `Entrega` : representa la relación entre un pedido y un repartidor, con fecha y hora.

### Capa de acceso a datos (DAO)

* `ConexionDB` : clase utilitaria que centraliza la conexión JDBC a MySQL mediante `DriverManager`.
* `PedidoDAO` : operaciones CRUD completas (`create`, `readAll`, `update`, `delete`) sobre la tabla `pedidos`.
* `RepartidorDAO` : operaciones CRUD completas sobre la tabla `repartidores`.
* `EntregaDAO` : operaciones CRUD completas sobre la tabla `entregas`, con JOIN para mostrar datos legibles de pedido y repartidor.

### Interfaz gráfica (Swing)

* `VentanaPrincipal` : ventana de inicio con acceso a las tres gestiones del sistema.
* `VentanaRepartidores` : formulario CRUD para repartidores con JTable, validación de campos obligatorios y confirmación de eliminación.
* `VentanaPedidos` : formulario CRUD para pedidos con JComboBox para tipo (COMIDA/ENCOMIENDA/EXPRESS) y estado (PENDIENTE/EN_REPARTO/ENTREGADO).
* `VentanaEntregas` : formulario CRUD para entregas con JComboBox cargados desde BD (pedidos y repartidores), validación de formato de fecha (YYYY-MM-DD) y hora (HH:MM).

## Base de datos

Base de datos MySQL `speedfast_db` con tres tablas:

* `repartidores` (id, nombre)
* `pedidos` (id, direccion, tipo ENUM, estado ENUM)
* `entregas` (id, id_pedido FK, id_repartidor FK, fecha, hora)

## Evolución por semanas

### Herencia, polimorfismo e interfaces
Diseño de la jerarquía `Pedido` con sus subclases, aplicando clases abstractas, polimorfismo y las interfaces `Despachable`, `Cancelable` y `Rastreable`.

### Semana 4 - Concurrencia con hilos
Simulación de repartidores entregando pedidos de forma concurrente. La clase `Repartidor` implementa `Runnable` y la clase `Main` lanza varios repartidores en paralelo mediante un `ExecutorService` con un pool de hilos.

### Semana 5 - Sincronización de procesos
Manejo del acceso concurrente a la `ZonaDeCarga` compartida. Se usa una `BlockingQueue` y métodos sincronizados para que varios repartidores retiren pedidos sin conflictos, gestionando correctamente los estados de cada pedido (PENDIENTE → EN_REPARTO → ENTREGADO).

### Semana 6 - Interfaces gráficas con Swing
Interfaz gráfica de escritorio para la gestión de entregas:

* `VentanaPrincipal` (JFrame): ventana de inicio con botones para registrar pedidos, listar pedidos y asignar repartidor / iniciar entrega.
* `VentanaRegistroPedido` (JFrame): formulario con campos ID, dirección, distancia y tipo de pedido.
* `VentanaListaPedidos` (JFrame): muestra los pedidos en una `JTable` con opción de refrescar.

### Semana 7 - Conexión JDBC con MySQL
Persistencia de datos conectando la aplicación Java con una base de datos MySQL mediante JDBC:

* Base de datos `speedfast_db` con tablas `repartidor`, `pedido` y `entrega`.
* `ConexionDB` centraliza la conexión JDBC usando `DriverManager`.
* Clases DAO (`PedidoDAO`, `RepartidorDAO`, `EntregaDAO`) con operaciones INSERT y SELECT.
* Interfaz gráfica integrada con la base de datos.

### Semana 8 - CRUD completo y sistema integrado
Implementación del ciclo funcional completo con operaciones CRUD (Create, Read, Update, Delete):

* DAOs actualizados con métodos `create()`, `readAll()`, `update()` y `delete()` usando `PreparedStatement` y `ResultSet`.
* Tablas actualizadas a esquema con ENUM (`pedidos`, `repartidores`, `entregas`).
* Nuevas ventanas CRUD: `VentanaRepartidores`, `VentanaPedidos` y `VentanaEntregas` reemplazan las ventanas anteriores.
* JComboBox cargados desde BD para seleccionar pedidos y repartidores al registrar entregas, mostrando texto legible (id + nombre/dirección).
* Validaciones de entrada: campos obligatorios, formato de fecha (YYYY-MM-DD) y hora (HH:MM), existencia de registros relacionados.
* Manejo de excepciones SQL con mensajes claros al usuario mediante `JOptionPane`.
* Confirmación antes de eliminar registros.
* Código modularizado con comentarios explicativos en métodos clave.

## Ejecución

Ejecutar la clase `Main` ubicada en `src/cl/speedfast/main/Main.java`. Al iniciar, se abre la ventana principal de la aplicación (`VentanaPrincipal`).

**Requisitos:** MySQL 8.0 instalado con la base de datos `speedfast_db` creada. Configurar usuario y contraseña en `ConexionDB.java`.

## Autor

Oscar Fuentes - DuocUC

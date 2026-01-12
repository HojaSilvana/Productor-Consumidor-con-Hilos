import Tipos.EstadoPedido;

public class Main {

    public static void main(String[] args) {

        //Creo los monitores necesario para cada tarea de los hilos
        ColaPedidosPendientes_Monitor colaPendientes = new ColaPedidosPendientes_Monitor();
        ColaPedidosProcesados_Monitor colaProcesados = new ColaPedidosProcesados_Monitor();
        MonitorProductosExclusivos monitorExclusivos = new MonitorProductosExclusivos();

        //Hilo del "productor" con su tarea de Cliente productor y su nombre
        Thread productor = new Cliente_Productor(colaPendientes, monitorExclusivos);
        productor.setName("Cliente-Productor");

        //Hilo del "gestor" con su tarea del almacen donde necesita recibir los dos monitores de los pedidos
        Thread gestor = new Thread(new Gestor_Almacen_Consumidor(colaPendientes, colaProcesados), "Gestor del Almacen");

        //Hilo del "transportista" que saca los pedidos del monitor de los pedidos procesadores. En formato Lambda
        Thread transportista = new Thread(() -> {
            while (true) {
                Pedido pedido = colaProcesados.retirarProcesado();
                pedido.setEstado(EstadoPedido.ENVIADO);
                System.out.println(Thread.currentThread().getName() + " ha enviado el pedido. Estado del pedido: " + pedido.getEstado() + " | ID=" + pedido.getId());

                try {
                    Thread.sleep(200);
                } catch (InterruptedException ie) {
                    System.out.println("Se ha interrumpido al transportista");
                }
            }
        }, "Transportista");

        System.out.println("Abriendo Tienda Online Amadzon. Comenzando cadena de pedidos...");

        productor.start();
        gestor.start();
        transportista.start();
    }
}

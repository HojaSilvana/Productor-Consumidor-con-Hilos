import Tipos.EstadoPedido;

public class Gestor_Almacen_Consumidor implements Runnable {

    private ColaPedidosPendientes_Monitor colaPedidos;
    private ColaPedidosProcesados_Monitor colaPedidosProcesados;


    public Gestor_Almacen_Consumidor(ColaPedidosPendientes_Monitor colaPedidos, ColaPedidosProcesados_Monitor colaPedidosProcesados){
        this.colaPedidos = colaPedidos;
        this.colaPedidosProcesados = colaPedidosProcesados;

    }

    @Override
    public void run() {

        while (true){

            Pedido pedido = colaPedidos.retirar();

            pedido.setEstado(EstadoPedido.PROCESANDO);
            System.out.println(Thread.currentThread().getName() + " esta procesando el pedido. Estado del pedido: " + pedido.getEstado());

            colaPedidosProcesados.anadirProcesado(pedido);

            try {
                Thread.sleep(300);
            } catch (InterruptedException ie) {
                System.out.println("Se ha interrumpido el Gestor de procesamiento de pedidos");
            }

        }


    }

}

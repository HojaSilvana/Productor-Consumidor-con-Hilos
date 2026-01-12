import Tipos.EstadoPedido;

public class Transportista_Consumidor implements Runnable {

    private ColaPedidosProcesados_Monitor colaPedidosProcesados;

    public Transportista_Consumidor (ColaPedidosProcesados_Monitor colaPedidosProcesados){
        this.colaPedidosProcesados = colaPedidosProcesados;
    }

    @Override
    public void run() {

        while (true){
            Pedido pedido = colaPedidosProcesados.retirarProcesado();

            pedido.setEstado(EstadoPedido.ENVIADO);
            System.out.println(Thread.currentThread().getName() + " ha enviado el pedido. Estado del pedido: " + pedido.getEstado());
        }

    }
}

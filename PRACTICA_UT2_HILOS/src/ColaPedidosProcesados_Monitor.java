import java.util.ArrayList;
import java.util.List;

public class ColaPedidosProcesados_Monitor {

    List<Pedido> colaPedidosProcesados = new ArrayList<>();
    private final int limiteCola = 25;

    public synchronized void anadirProcesado (Pedido pedido){
        while (colaPedidosProcesados.size() >= limiteCola){
            try {
                wait();
            } catch (InterruptedException ie) {
                System.out.println("Se ha interrumpido la cola para añadir pedidos procesados " + ie.getMessage());
            }
        }

        colaPedidosProcesados.add(pedido);
        notifyAll();
    }

    public synchronized Pedido retirarProcesado(){
        while (colaPedidosProcesados.isEmpty()){
            try {
                wait();
            } catch (InterruptedException ie) {
                System.out.println("Se ha interrumpido la cola para retirar pedidos procesados" + ie.getMessage());
            }
        }

        Pedido pedido = colaPedidosProcesados.remove(0);
        notifyAll();
        return pedido;
    }
}

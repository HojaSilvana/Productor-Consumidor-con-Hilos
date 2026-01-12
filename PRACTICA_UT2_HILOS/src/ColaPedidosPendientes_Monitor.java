import java.util.ArrayList;
import java.util.List;

public class ColaPedidosPendientes_Monitor {

    /* Iba a usar una blocking en la version 1 pero me he dado cuenta que si hago eso me como toda la practica xD
    BlockingQueue<Producto> colaProductos = new ArrayBlockingQueue<>(10); */

    List<Pedido> colaPedidos = new ArrayList<>();
    private final int limiteCola = 25;

    public synchronized void anadir (Pedido pedido){
        while (colaPedidos.size() >= limiteCola){
            try {
                wait();
            } catch (InterruptedException ie) {
                System.out.println("Se ha interrumpido la cola para añadir pedidos " + ie.getMessage());
            }
        }

        colaPedidos.add(pedido);
        notifyAll();
    }

    public synchronized Pedido retirar(){
        while (colaPedidos.isEmpty()){
            try {
                wait();
            } catch (InterruptedException ie) {
                System.out.println("Se ha interrumpido la cola para retirar pedidos " + ie.getMessage());
            }
        }

        Pedido pedido = colaPedidos.removeFirst();
        notifyAll();
        return pedido;
    }

}

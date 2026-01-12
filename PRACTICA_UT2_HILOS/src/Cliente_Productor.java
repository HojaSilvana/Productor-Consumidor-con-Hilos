import Tipos.EstadoPedido;
import Tipos.TipoProducto;

import java.util.concurrent.ThreadLocalRandom;

public class Cliente_Productor extends Thread{

    private ColaPedidosPendientes_Monitor colapedidos;
    private MonitorProductosExclusivos monitorExclusivos;

    public Cliente_Productor(ColaPedidosPendientes_Monitor colapedidos, MonitorProductosExclusivos monitor){
        this.colapedidos = colapedidos;
        this.monitorExclusivos = monitor;
    }

    @Override
    public void run() {

        while (true){

            TipoProducto exclusivo1 = generarTipoAleatorio();
            TipoProducto exclusivo2;

            do {
                exclusivo2 = generarTipoAleatorio();

            } while (exclusivo1 == exclusivo2);

            monitorExclusivos.asegurarProducto(exclusivo1);

            if (!monitorExclusivos.intentarAsegurarSegundo(exclusivo2)) {
                monitorExclusivos.liberarProducto(exclusivo1);
                continue;
            }

            System.out.println(Thread.currentThread().getName() + " ha asegurado los productos exclusivos: P1=" + exclusivo1 + " y P2=" + exclusivo2 + " para poder crear su pedido exclusivo.");

            Pedido pedido = new Pedido(ThreadLocalRandom.current().nextInt(1000), new Producto("Exclusivo", exclusivo1), EstadoPedido.PENDIENTE);

            System.out.println(Thread.currentThread().getName() + " ha creado un pedido: " + pedido.getProducto() + ".Estado del pedido: " + pedido.getEstado() + " | ID=" + pedido.getId());

            colapedidos.anadir(pedido);

            monitorExclusivos.confirmarCompra(exclusivo1, exclusivo2);


            try {
                Thread.sleep(300);
            } catch (InterruptedException ie) {
                System.out.println("Se ha interrumpido la produccion de pedidos");
            }

        }
    }

    private TipoProducto generarTipoAleatorio() {
        TipoProducto[] valores = TipoProducto.values();
        return valores[ThreadLocalRandom.current().nextInt(valores.length)];
    }

}

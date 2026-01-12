import Tipos.TipoProducto;

import java.util.EnumMap;
import java.util.Map;

public class MonitorProductosExclusivos {

    private final Map<TipoProducto, Boolean> ocupado = new EnumMap<>(TipoProducto.class);

    public MonitorProductosExclusivos() {
        for (TipoProducto tipo : TipoProducto.values()) {
            ocupado.put(tipo, false);
        }
    }

    public synchronized void asegurarProducto(TipoProducto tipo) {
        while (ocupado.get(tipo)) {
            try {
                wait();
            } catch (InterruptedException ignored) {}
        }
        ocupado.put(tipo, true);
    }

    public synchronized boolean intentarAsegurarSegundo(TipoProducto tipo) {
        long inicio = System.currentTimeMillis();
        long limite = 2000;

        while (ocupado.get(tipo)) {
            long restante = limite - (System.currentTimeMillis() - inicio);
            if (restante <= 0) return false;

            try {
                wait(restante);
            } catch (InterruptedException ignored) {
                return false;
            }
        }

        ocupado.put(tipo, true);
        return true;
    }

    public synchronized void liberarProducto(TipoProducto tipo) {
        ocupado.put(tipo, false);
        notifyAll();
    }

    public synchronized void confirmarCompra(TipoProducto p1, TipoProducto p2) {
        ocupar(p1,false);
        ocupar(p2,false);
        notifyAll();
    }

    private void ocupar(TipoProducto t, boolean status) {
        ocupado.put(t, status);
    }
}

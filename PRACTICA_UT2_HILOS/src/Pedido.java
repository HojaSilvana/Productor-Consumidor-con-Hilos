    import Tipos.EstadoPedido;

    public class Pedido {

        private final int id;
        private Producto producto;
        private EstadoPedido estado;

        public Pedido (int id, Producto producto, EstadoPedido estado){

            this.id = id;
            this.producto = producto;
            this.estado = estado;

        }

        public int getId() {
            return id;
        }

        public Producto getProducto() {
            return producto;
        }

        public EstadoPedido getEstado() {
            return estado;
        }

        public void setEstado(EstadoPedido estado) {
            this.estado = estado;
        }

        @Override
        public String toString() {
            return "Pedido{" +
                    "id=" + id +
                    ", producto=" + producto +
                    ", estado=" + estado +
                    '}';
        }
    }

import Tipos.TipoProducto;

public class Producto {

    private String nombre;
    private TipoProducto tipo;

    public Producto(String nombre, TipoProducto tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "nombre='" + nombre + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }


}

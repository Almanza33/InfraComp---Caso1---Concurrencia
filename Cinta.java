import java.util.ArrayList;

public class Cinta {

    private boolean cargado;
    private ArrayList<Producto> buffer;


    public Cinta(){
        this.cargado = false;
        this.buffer = new ArrayList<Producto>();
    }

    public synchronized void almacenar(Producto producto){
        // Se almacena el producto
        buffer.add(producto);
        this.cargado = true;
        System.out.println("Se ha agregado un producto de tipo " + producto.getTipo() + " a la cinta. Ahora hay " + this.buffer.size() + " productos en la cinta.");
    }
    
    public synchronized Producto retirar(){
        // Se retira el producto
        Producto producto = buffer.remove(0);
        this.cargado = false;
        System.out.println("Se ha retirado un producto de categoria " + producto.getTipo() + " De la cinta");
        return producto;
    }
    public synchronized boolean cargado(){
        return this.cargado;
    }


    public synchronized int inventario(){
        return this.buffer.size();
    }

}

import java.util.ArrayList;

public class Cinta {

    private boolean cargado;
    private ArrayList<Producto> buffer;


    public Cinta(){
        this.cargado = false;
        this.buffer = new ArrayList<Producto>();
    }

    public synchronized void almacenar(Producto producto){
        // Si se desea almacenar un producto cuando la cinta ya tiene otro, espera
        while (this.cargado){
            try {
                wait();
            } catch (InterruptedException e) {
                
                e.printStackTrace();
            }
        }
        // Se almacena el producto
        buffer.add(producto);
        this.cargado = true;
        System.out.println("Se ha agregado un producto de tipo " + producto.getTipo() + " a la cinta. Ahora hay " + this.buffer.size() + " productos en la cinta.");
        notifyAll();
    }
    
    public synchronized Producto retirar(){
        // Si se desea retirar un producto de la cinta, cuando no hay productos por retirar, espera
        while(!cargado){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Se retira el producto
        Producto producto = buffer.remove(0);
        this.cargado = false;
        System.out.println("Se ha retirado un producto de categoria " + producto.getTipo() + " De la cinta");
        notifyAll();
        
        return producto;
    }

    public synchronized int inventario(){
        return this.buffer.size();
    }

}

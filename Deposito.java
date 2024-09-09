import java.util.ArrayList;

public class Deposito {

    private String tipo; //"prod" o "dist"
    private int capacidad;
    private int prodsTipoA;
    private int prodsTipoB;
    private ArrayList<Producto> buffer;


    public Deposito(String ptipo, int pcapacidad){
        this.tipo = ptipo;
        this.capacidad = pcapacidad;
        this.prodsTipoA = 0;
        this.prodsTipoB = 0;
        this.buffer = new ArrayList<Producto>();
    }


    public synchronized void almacenar(Producto producto){

        // Si el buffer está lleno, espera
        while (buffer.size() == capacidad){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Si el buffer tiene espacio, se agrega el producto
        buffer.add(producto);
        String tipoAgregado = producto.getTipo();

        // Se actualiza el numero de productos por tipo en el buffer
        if(tipoAgregado.equals("A")){
            this.prodsTipoA++;
            System.out.println("Hay " + this.prodsTipoA +  " producto(s) de tipo " + tipoAgregado + " en el deposito de " + this.tipo + ". En total hay " + this.buffer.size() + " producto(s).");
        }
        else{
            this.prodsTipoB++;
            System.out.println("Hay " + this.prodsTipoB +  " producto(s) de tipo " + tipoAgregado + " en el deposito de " + this.tipo + ". En total hay " + this.buffer.size() + " producto(s).");
        }
        
        notifyAll();
    }

    public synchronized Producto retirar(String tipoDeseado){
        Producto producto = null;
        // Si el buffer está vacío, espera
        while(buffer.size() == 0){
            
            try {
                System.out.println("El buffer está vacio");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Si se quiere retirar un producto de un ipo específico:
        if(tipoDeseado.equals("A") || tipoDeseado.equals("B")){
            // Si no se encuentra un producto del tipo, se pone en espera
            while(!hayProductoTipo(tipoDeseado)){
                System.out.println("No se ha podido encontrar un producto de ese tipo para retirar");
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }            
            int position = hallarProducto(tipoDeseado);
            producto = buffer.remove(position);
            String tipoRetirado = producto.getTipo();
            if(tipoRetirado.equals("A")){
                this.prodsTipoA--;
            }
            else{
                this.prodsTipoB--;
            }
            
        }
        // Si no se requiere de un tipo especifico:
        else{
            // Se retira un producto de cualquier categoría
            producto = buffer.remove(0);
            String tipoRetirado = producto.getTipo();
            if(tipoRetirado.equals("A")){
                this.prodsTipoA--;
            }
            else{
                this.prodsTipoB--;
            }
        }
        System.out.println("Se ha retirado un producto de categoria " + producto.getTipo() + " del deposito de " + this.tipo + ". Ahora hay " + this.buffer.size() + " productos en total.");
        notifyAll();
        return producto;
    }
    // Funcion para hallar un producto de un cierto tipo en el depósito
    public synchronized int hallarProducto(String tipo){
        int i = 0;
        boolean notFound = true;
        int position = -1; // Si no se encuentra un producto de la categoría deseada, se retorna -1
        while(buffer.size() > i && notFound){
            Producto producto = buffer.get(i);
            if(producto.getTipo().equals(tipo)){
                notFound = false;
                position = i;
            }
            i++;
        }
        return position;
    }
    // Funcion para saber si hay un producto de un cierto tipo en el depósito
    public synchronized boolean hayProductoTipo(String tipo){
        int i = 0;
        boolean notFound = true;
        boolean retorno = false;
        while(buffer.size() > i && notFound){
            Producto producto = buffer.get(i);
            if(producto.getTipo().equals(tipo)){
                notFound = false;
                retorno = true;
            }
            i++;
        }
        return retorno;
    }

    public synchronized int inventario(){
        return this.buffer.size();
    }

    public int capacidad(){
        return this.capacidad;
    }


    // Main de pruebas
    public static void main(String[] args){
        System.out.println("ola");
        Deposito deposito = new Deposito("produccion", 15);

        for(int i = 0; i < 15; i ++){
            Producto producto = new Producto("A", false);
            deposito.almacenar(producto);
        }

        
        deposito.retirar("A");
        deposito.retirar("B");


    } 
}

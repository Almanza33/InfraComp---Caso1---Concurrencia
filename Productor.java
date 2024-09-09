public class Productor extends Thread{
    private String tipo;
    private int numProductos;
    private Deposito deposito;

    public Productor(String ptipo, int numProductos, Deposito pDeposito){
        this.tipo = ptipo;
        this.numProductos = numProductos;
        this.deposito = pDeposito;
    }

    @Override
    public void run() {
        int i = 0;
        while(i < numProductos){
            crear();
            i++;
        }
        Producto productoF = new Producto(tipo,true);
        deposito.almacenar(productoF);
        System.out.println("Uno de los Productores ha creado el número de productos deseado, por lo que se ha detenido");
        
    }

    public void crear(){
        Producto producto = new Producto(tipo,false);
        deposito.almacenar(producto);  
    }

    // Main de pruebas
    public static void main(String[] args){
        Deposito deposito1 = new Deposito("produccion", 5);
        Deposito deposito2 = new Deposito("distribucion", 50);
        Cinta cinta = new Cinta();

        Operario operario1 = new Operario(deposito1, cinta, true);
        operario1.start();
        Operario operario2 = new Operario(deposito2, cinta, false);
        operario2.start();

        Productor productorA1 = new Productor("A", 4, deposito1);
        productorA1.start();
        Productor productorA2 = new Productor("A", 4, deposito1);
        productorA2.start();
        Productor productorB1 = new Productor("B", 4, deposito1);
        productorB1.start();
        Productor productorB2 = new Productor("B", 4, deposito1);
        productorB2.start();

        Distribuidor distribuidorA1 = new Distribuidor("A", deposito2);
        distribuidorA1.start();
        Distribuidor distribuidorA2 = new Distribuidor("A", deposito2);
        distribuidorA2.start();
        Distribuidor distribuidorB1 = new Distribuidor("B", deposito2);
        distribuidorB1.start();
        Distribuidor distribuidorB2 = new Distribuidor("B", deposito2);
        distribuidorB2.start();


    }


}
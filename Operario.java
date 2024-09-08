public class Operario extends Thread{
    private int stopsVistos;
    private Deposito deposito;
    private Cinta cinta;
    private boolean descargador;
    

    public Operario(Deposito pDeposito, Cinta pCinta, boolean pdescargador){
        this.stopsVistos = 0;
        this.deposito = pDeposito;
        this.cinta = pCinta;
        this.descargador = pdescargador;
    }

    @Override
    public void run() {
        // Descargar (o cargar) productos hasta ver 4 productos finales
        while(stopsVistos != 4){
            if(descargador){
                descargar();
            }
            else{
                cargar();
            }
        }
        System.out.println("Uno de los Operarios ha visto 4 stops y se ha detenido");
    }

    public void cargar(){
        // Carga de la cinta al depósito
        while(cinta.inventario() == 0){
            this.yield();
        }
        Producto producto = cinta.retirar();
        if(producto.esFinal()){
            this.stopsVistos++;
        }
        deposito.almacenar(producto);

    }

    public void descargar(){
        // Descarga del deposito a la cinta
        while (deposito.inventario() == 0){
            this.yield();
        }
        Producto producto = deposito.retirar("x");
        if(producto.esFinal()){
            this.stopsVistos++;
        }
        cinta.almacenar(producto);
    }

    // Main de pruebas
    public static void main(String[] args){
        Deposito deposito1 = new Deposito("produccion", 5);
        Deposito deposito2 = new Deposito("distribucion", 5);
        Cinta cinta = new Cinta();
        
        Producto producto1 = new Producto("B", false);


        deposito1.almacenar(producto1);

        Operario operario1 = new Operario(deposito1, cinta, true);
        Operario operario2 = new Operario(deposito2, cinta, false);
        operario1.start();
        operario2.start();
        
        System.out.println("hoa");


    }

}

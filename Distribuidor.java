public class Distribuidor extends Thread {
    private String tipo;
    private boolean detenido;
    private Deposito deposito;

    public Distribuidor(String ptipo, Deposito pDeposito){
        this.tipo = ptipo;
        this.detenido = false;
        this.deposito = pDeposito;
    }

    @Override
    public void run() {
        
        while (!detenido) {
            Producto producto = distribuir();
            if(producto.esFinal()){
                detenido = true;
                System.out.println("Uno de los distribuidores ha visto un producto de stop y se ha detenido");
            }
            try {
                this.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }   
        }
    }

    public Producto distribuir(){
        Producto producto = deposito.retirar(tipo);
        return producto;
    }
}

public class Producto {
    private String tipo;
    private boolean fin;

    public Producto(String ptipo, boolean pfin){
        this.tipo = ptipo;
        this.fin = pfin;
    }

    public String getTipo(){
        return this.tipo;
    }

    public boolean esFinal(){
        return this.fin;
    }
}

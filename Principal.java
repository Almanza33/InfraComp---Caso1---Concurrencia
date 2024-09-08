import java.util.Scanner;

public class Principal {

    public static void main(String[] args){

        Scanner input = new Scanner(System.in);
    	
        System.out.print("Ingrese el número de espacios para el depósito de produccion: ");
        int slotsProd = input.nextInt();
        
        System.out.print("Ingrese el número de espacios para el depósito de distribucion: ");
        int slotsDist = input.nextInt();

        System.out.print("Ingrese el número de productos a generar: ");
        int prodNum = input.nextInt();

        input.close();
        

        Deposito deposito1 = new Deposito("produccion", slotsProd);
        Deposito deposito2 = new Deposito("distribucion", slotsDist);
        Cinta cinta = new Cinta();

        Operario operario1 = new Operario(deposito1, cinta, true);
        operario1.start();
        Operario operario2 = new Operario(deposito2, cinta, false);
        operario2.start();

        Productor productorA1 = new Productor("A", prodNum, deposito1);
        productorA1.start();
        Productor productorA2 = new Productor("A", prodNum, deposito1);
        productorA2.start();
        Productor productorB1 = new Productor("B", prodNum, deposito1);
        productorB1.start();
        Productor productorB2 = new Productor("B", prodNum, deposito1);
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

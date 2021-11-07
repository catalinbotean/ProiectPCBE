package utils;

public class Imprimanta {

    public void print(int documentNumber, Ghiseu ghiseu){
        System.out.println("Printing document number " + documentNumber + " for " + ghiseu);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

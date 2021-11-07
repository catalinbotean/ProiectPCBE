package utils;

import java.util.List;
import java.util.Random;

public class PauzaCafea extends Thread {
    List<Ghiseu> ghisee;

    public PauzaCafea(List<Ghiseu> ghisee){
        this.ghisee = ghisee;
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(3000);
                Random r=new Random();
                int randomNumber=r.nextInt(ghisee.size());
                Ghiseu ghiseu = ghisee.get(randomNumber);
                System.out.println("*************** pauza");
                if(!ghiseu.isTaken()){
                    ghiseu.takeCoffeeBreak();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
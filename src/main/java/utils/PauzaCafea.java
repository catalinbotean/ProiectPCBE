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
                Thread.sleep(2500);
                Random r=new Random();
                int randomNumber=r.nextInt(ghisee.size());
                Ghiseu ghiseu = ghisee.get(randomNumber);
                if(!ghiseu.isTaken() && !ghiseu.isGhiseuInCoffeeBreak()){
                    System.out.println("Ghiseul " + ghiseu + " intra in pauza" );
                    ghiseu.takeCoffeeBreak();
                    Thread.sleep(2500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
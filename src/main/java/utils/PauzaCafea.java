package utils;

import java.util.List;
import java.util.Random;

public class PauzaCafea extends Thread {
    List<Ghiseu> ghisee;
    Birou[] birouri;
    public PauzaCafea(Birou[] birouri, List<Ghiseu> ghisee){
        this.birouri = birouri;
        this.ghisee = ghisee;
    }

    @Override
    public void run() {
        while(Client.getNumberOfClients()!= 0){
            Random r=new Random();
            int randomNumber = r.nextInt(birouri.length-1);
            Birou b = birouri[randomNumber];
            if(b!=null)
                b.puneInPauza();
        }
        for(Ghiseu g:ghisee){
            g.getFunctionar().interrupt();
        }
    }
}
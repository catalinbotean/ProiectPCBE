package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PauzaCafea extends Thread {
    private List<Ghiseu> ghisee;
    private ArrayList<Birou> birouri;
    private ArrayList<Thread> tasks = new ArrayList<>();

    public PauzaCafea(ArrayList<Birou> birouri, List<Ghiseu> ghisee){
        this.birouri = birouri;
        this.ghisee = ghisee;
    }

    public synchronized void add(Thread t){
        tasks.add(t);
    }

    @Override
    public void run() {
        while(Client.getNumberOfClients()!= 0){
            Random r=new Random();
            int randomNumber = r.nextInt(birouri.size());
            Birou b = birouri.get(randomNumber);
            if(b!=null)
                b.puneInPauza(this);
        }
        for(Thread t: tasks){
            t.interrupt();
        }
        for(Ghiseu g:ghisee){
            g.getFunctionar().interrupt();
        }
    }
}
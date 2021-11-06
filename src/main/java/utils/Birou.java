package utils;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Birou implements Runnable{
    private static int numarBirouri = 1;
    private int id;
    private BlockingQueue<Client> queue = new LinkedBlockingQueue<>();
    private ArrayList<Ghiseu>  ghisee;
    public Birou(ArrayList<Ghiseu> ghisee){
        this.ghisee = ghisee;
        this.id = numarBirouri++;
        for(Ghiseu g: this.ghisee) {
            g.setBirou(this);
        }
    }

    public Ghiseu getGhiseu1(){
        for(Ghiseu g: ghisee)
            return g;
        return null;
    }

    public int getId() {
        return id;
    }

    public synchronized void addClientToQueue(Client c)
    {
        try{
        queue.put(c);
        System.out.println(queue);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        while(true)
        {

        }
    }

    public BlockingQueue<Client> getQueue() {
        return queue;
    }
}

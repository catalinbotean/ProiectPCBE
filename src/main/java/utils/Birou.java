package utils;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Birou {
    private static int numarBirouri = 1;
    private int id;
    private BlockingQueue<Client> queue = new LinkedBlockingQueue<>();
    private ArrayList<Ghiseu> ghisee;
    private Imprimanta imprimanta;
    private POS pos;

    public Birou(ArrayList<Ghiseu> ghisee) {
        this.ghisee = ghisee;
        this.id = numarBirouri++;
        this.ghisee.forEach(ghiseu -> ghiseu.setBirou(this));
        this.imprimanta = new Imprimanta();
        this.pos = new POS();
    }


    public void addClientToQueue(Client c) {
        try {
            queue.put(c);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void goPrintDocument(int documentNumber, Ghiseu ghiseu) throws InterruptedException {
        Thread.sleep(500);
        synchronized (this) {
            this.imprimanta.print(documentNumber, ghiseu);
            Thread.sleep(500);
        }
        pay(1);
    }

    private void pay(int sum) throws InterruptedException {
        Thread.sleep(500);
        synchronized (this) {
            this.pos.paySum(sum);
            Thread.sleep(500);
        }
    }

    public BlockingQueue<Client> getQueue() {
        return queue;
    }

    public String toString(){
        return Integer.toString(id);
    }
}

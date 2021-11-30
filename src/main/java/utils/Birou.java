package utils;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Birou implements Runnable {
    private static int numarBirouri = 1;
    private int id;
    private BlockingQueue<Client> queue = new LinkedBlockingQueue<>();
    private ArrayList<Ghiseu> ghisee;
    private Imprimanta imprimanta;
    private POS pos;

    public Birou(ArrayList<Ghiseu> ghisee) {
        this.ghisee = ghisee;
        this.id = numarBirouri++;
        for (Ghiseu g : this.ghisee) {
            g.setBirou(this);
        }
        this.imprimanta = new Imprimanta();
        this.pos = new POS();
    }

    public Ghiseu getGhiseu1() {
        for (Ghiseu g : ghisee)
            return g;
        return null;
    }

    public int getId() {
        return id;
    }

    public synchronized void addClientToQueue(Client c) {
        try {
            queue.put(c);
//            System.out.println(queue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {

        }
    }

    public int getDocument(){
        return id;
    }

    public String toString(){
        return getGhiseu1().getName();
    }

    public synchronized void goPrintDocument(int documentNumber, Ghiseu ghiseu){
        this.imprimanta.print(documentNumber, ghiseu);
        System.out.println("s-a eliberat imprimanta");
        pay(1);
    }

    public void pay(int sum){
        this.pos.paySum(sum);
    }

    public BlockingQueue<Client> getQueue() {
        return queue;
    }
}

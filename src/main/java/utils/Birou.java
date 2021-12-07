package utils;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;


public class Birou {
    private static int numarBirouri = 1;
    private int id;
    private BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
    private ArrayList<Ghiseu> ghisee;
    private Imprimanta imprimanta;
    private POS pos;
    private final Semaphore ghiseeDisponibile;
    private final Semaphore mutex = new Semaphore(1);

    public Birou(ArrayList<Ghiseu> ghisee) {
        this.ghisee = ghisee;
        this.id = numarBirouri++;
        this.ghisee.forEach(ghiseu -> ghiseu.setBirou(this));
        this.imprimanta = new Imprimanta();
        this.pos = new POS();
        ghiseeDisponibile = new Semaphore(ghisee.size());
    }

    public void getDocument(Client c){
        try {

            ghiseeDisponibile.acquire();
            alegeGhiseu(c);
            ghiseeDisponibile.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void alegeGhiseu(Client c){
        try {
            mutex.acquire();
            Ghiseu g;
            do {
                System.out.println(c);
                g = ghisee.get((int) (Math.random() * ghisee.size()));
                System.out.println(g);

            }while(g.getTaken()==true);
            g.setTaken(true);
            mutex.release();
            g.generateDocument(c);
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

    public BlockingQueue<Integer> getQueue() {
        return queue;
    }

    public String toString(){
        return Integer.toString(id);
    }
}
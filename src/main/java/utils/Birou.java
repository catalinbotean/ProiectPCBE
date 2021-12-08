package utils;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;


public class Birou {
    private static int numarBirouri = 1;
    private int id;
    private int currentNumberOfOrder = 1;
    private int currentClient = 1;
    private BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
    private ArrayList<Integer> numberOfOrder = new ArrayList<>();
    private ArrayList<Ghiseu> ghisee;
    private Imprimanta imprimanta;
    private POS pos;
    private final Semaphore ghiseeDisponibile;
    private final Semaphore mutex = new Semaphore(1);
    private final Semaphore numberOfOrderMutex = new Semaphore(1);

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
            getNumberOfOrder(c);
            goNow(c);
            alegeGhiseu(c);
            ghiseeDisponibile.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void goNow(Client c){
        while(c.getNumberOfOrder()!=currentClient){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        currentClient++;
        System.out.println(c+ " e urmatorul client la "+this);
        notifyAll();
    }

    public void getNumberOfOrder(Client c){
        try {
            numberOfOrderMutex.acquire();
            numberOfOrder.add(currentNumberOfOrder);
            c.setNumberOfOrder(currentNumberOfOrder);
            currentNumberOfOrder++;
            numberOfOrderMutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void alegeGhiseu(Client c){
        try {
            mutex.acquire();
            System.out.println(c+ " "+c.getNumberOfOrder() + " isi alege Ghiseul");
            Ghiseu g;
            ghisee.forEach(ghiseu -> System.out.println(ghiseu+ " " + ghiseu.getTaken()));
            do {
                g = ghisee.get((int) (Math.random() * ghisee.size()));
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

    public String toString(){
        return Integer.toString(id);
    }
}
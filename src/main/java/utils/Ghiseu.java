package utils;

import java.util.concurrent.Semaphore;

public class Ghiseu extends Thread {

    private String name;
    private Birou b;
    private int documentNumberToPrint;
    private boolean taken = false;
    private boolean isCoffeeBreak = false;
    private final Semaphore ghiseuS = new Semaphore(0);
    private final Semaphore clientS = new Semaphore(1);

    public Ghiseu(String n, int documentNumberToPrint) {
        name = n;
        this.documentNumberToPrint  = documentNumberToPrint;
    }

    @Override
    public void run() {
        while (true) {
            createDocument();
        }
    }

    public boolean getTaken(){
        return taken;
    }

    public void setTaken(boolean taken){
        this.taken = taken;
    }

    public void setBirou(Birou b) {
        this.b = b;
    }

    public void generateDocument(Client c){
        try {
            clientS.acquire();
            System.out.println("Clientul "+c+" a primit documentul "+ documentNumberToPrint);
            setTaken(false);
            ghiseuS.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void createDocument(){
        try {
            ghiseuS.acquire();
            b.goPrintDocument(this.documentNumberToPrint, this);
            clientS.release();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public boolean isTaken(){
        return taken;
    }

    public void takeCoffeeBreak(){
        try {
            this.isCoffeeBreak = true;
            System.out.println("n-ar mai trebui sa avem vreun client luat in " + name);
            Thread.sleep(5000);
            this.isCoffeeBreak = false;
            System.out.println("am iesit din pauza de masa");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isGhiseuInCoffeeBreak(){
        return isCoffeeBreak;
    }

    public String toString() {
        return name;
    }
}
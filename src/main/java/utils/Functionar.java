package utils;

import java.util.concurrent.Semaphore;

public class Functionar extends Thread {

    private Ghiseu ghiseu;
    private int documentNumberToPrint;
    private boolean taken = false;
    private volatile StatusGhiseu status = StatusGhiseu.NEOCUPAT;
    private final Semaphore ghiseuSemafor = new Semaphore(0);
    private final Semaphore clientSemafor = new Semaphore(0);

    public Functionar(Ghiseu ghiseu) {
        this.ghiseu = ghiseu;
    }

    @Override
    public void run() {
        while (true) {
            ghiseu.createDocument(this);
        }
    }

    public Ghiseu getGhiseu(){
        return ghiseu;
    }
}
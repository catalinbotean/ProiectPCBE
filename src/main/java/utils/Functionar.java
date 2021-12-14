package utils;

public class Functionar extends Thread {

    private Ghiseu ghiseu;

    public Functionar(Ghiseu ghiseu) {
        this.ghiseu = ghiseu;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                ghiseu.createDocument(this);              }
        } catch (InterruptedException e) {
            System.out.println("Finished");
        }
    }

    public Ghiseu getGhiseu(){
        return ghiseu;
    }
}
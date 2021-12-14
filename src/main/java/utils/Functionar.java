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
                ghiseu.createDocument(this);
            }
        } catch (InterruptedException e) {
            System.out.println("Functionarul a plecat");
        }
    }

    public Ghiseu getGhiseu(){
        return ghiseu;
    }
}
package utils;

import java.util.concurrent.Semaphore;

public class Ghiseu {

    private String name;
    private Birou b;
    private Functionar f;
    private int documentNumberToPrint;
    private final Semaphore ghiseuSemafor = new Semaphore(0);
    private final Semaphore clientSemafor = new Semaphore(0);

    public Ghiseu(String n, int documentNumberToPrint) {
        name = n;
        this.documentNumberToPrint = documentNumberToPrint;
        f = new Functionar(this);
        f.start();
    }

    public void setBirou(Birou b) {
        this.b = b;
    }

    public void generateDocument(Client c) {
        try {
            ghiseuSemafor.release();
            clientSemafor.acquire();
            System.out.println("Clientul " + c + " a primit documentul " + documentNumberToPrint);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void createDocument(Functionar f) throws InterruptedException {
        ghiseuSemafor.acquire();
        Thread.sleep(500);
        b.printDocument(f);
        b.makePayment(f);
        clientSemafor.release();
    }

    public String toString(){
        return name;
    }

    public Functionar getFunctionar(){
        return f;
    }
}
package utils;

import java.util.concurrent.Semaphore;

public class Ghiseu {

    private String name;
    private Birou b;
    private int documentNumberToPrint;
    private volatile StatusGhiseu status = StatusGhiseu.NEOCUPAT;
    private final Semaphore ghiseuSemafor = new Semaphore(0);
    private final Semaphore clientSemafor = new Semaphore(0);
    private final Semaphore changeStatus = new Semaphore(1);

    public Ghiseu(String n, int documentNumberToPrint) {
        name = n;
        this.documentNumberToPrint = documentNumberToPrint;
    }

    public StatusGhiseu getStatus() {
        return status;
    }

    public void setStatus(StatusGhiseu status) {
        this.status = status;
    }

    public void setBirou(Birou b) {
        this.b = b;
    }

    public boolean isTaken() {
        try {
            changeStatus.acquire();
            if (status == StatusGhiseu.NEOCUPAT) {
                setStatus(StatusGhiseu.OCUPAT);
                changeStatus.release();
                return true;
            }
            changeStatus.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void generateDocument(Client c) {
        try {
            ghiseuSemafor.release();
            clientSemafor.acquire();
            System.out.println("Clientul " + c + " a primit documentul " + documentNumberToPrint);
            setStatus(StatusGhiseu.NEOCUPAT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void createDocument(Functionar f) {
        try {
            ghiseuSemafor.acquire();
            Thread.sleep(500);
            b.printDocument(f);
            b.makePayment(f);
            clientSemafor.release();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void takeCoffeeBreak(PauzaCafea p) {
        try {
            changeStatus.acquire();
            setStatus(StatusGhiseu.PAUZA);
            changeStatus.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String toString(){
        return name;
    }
}



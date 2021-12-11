

package utils;

import java.util.Vector;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;


public class Birou {
    private static int numarBirouri = 1;
    private int id;
    private int currentNumberOfOrder = 1;
    private int currentClient = 1;
    private Vector<Integer> numberOfOrder = new Vector<>();
    private ArrayList<Ghiseu> ghisee;
    private final Semaphore waitingRoomClients = new Semaphore(6);
    private final Semaphore completeForm = new Semaphore(3);
    private final Semaphore ghiseeDisponibile;
    private final Semaphore numberOfOrderMutex = new Semaphore(1);
    private final Semaphore usePrinter = new Semaphore(1);
    private final Semaphore usePOS = new Semaphore(1);

    public Birou(ArrayList<Ghiseu> ghisee) {
        this.ghisee = ghisee;
        this.id = numarBirouri++;
        this.ghisee.forEach(ghiseu -> ghiseu.setBirou(this));
        ghiseeDisponibile = new Semaphore(ghisee.size());
    }

    public void getDocument(Client c) {
        try {
            waitingRoomClients.acquire();
            completeForm.acquire();
            getNumberOfOrder(c);
            completeForm.release();
            goNow(c);
            alegeGhiseu(c);
            ghiseeDisponibile.release();
            System.out.println(c + " a plecat de la ghiseu");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void goNow(Client c) {
        while (c.getNumberOfOrder() != currentClient) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        currentClient++;
        System.out.println(c + " e urmatorul client la " + this);
        try {
            ghiseeDisponibile.acquire();
            numberOfOrderMutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notifyAll();
    }

    public void getNumberOfOrder(Client c) {
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

    public void alegeGhiseu(Client c) {
        System.out.println(c + " cu numar de ordine " + c.getNumberOfOrder() + " isi alege Ghiseul");
        Ghiseu g;
        do {
            g = ghisee.get((int) (Math.random() * ghisee.size()));
        } while (g.isTaken() == false);
        System.out.println(c + " si-a ales ghiseul " + g);
        waitingRoomClients.release();
        numberOfOrderMutex.release();
        g.generateDocument(c);
    }

    public void printDocument(Functionar f) {
        try {
            usePrinter.acquire();
            System.out.println("Functionarul de la ghiseul " + f.getGhiseu() + " a utilizat imprimanta");
            usePrinter.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void makePayment(Functionar f) {
        try {
            usePOS.acquire();
            System.out.println("A fost efectuata plata la ghiseul " + f.getGhiseu());
            usePOS.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public String toString() {
        return Integer.toString(id);
    }
}

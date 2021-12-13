package utils;

import java.util.Vector;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Birou {
    private static int numarBirouri = 1;
    private int id;
    private int currentNumberOfOrder = 1;
    private int currentClient = 1;
    private Vector<Integer> numberOfOrder = new Vector<>();
    private List<Ghiseu> ghisee;
    private List<Ghiseu> ghiseeFrecventate;
    private final Semaphore ghiseeDisponibile;
    private final Semaphore waitingRoomClients = new Semaphore(6);
    private final Semaphore completeForm = new Semaphore(3);
    private final Semaphore numberOfOrderMutex = new Semaphore(1);
    private final Semaphore ghiseuSemafor = new Semaphore(1);
    private final Semaphore usePrinter = new Semaphore(1);
    private final Semaphore usePOS = new Semaphore(1);

    public Birou(ArrayList<Ghiseu> ghisee) {
        this.ghisee = ghisee;
        this.id = numarBirouri++;
        this.ghisee.forEach(ghiseu -> ghiseu.setBirou(this));
        ghiseeDisponibile = new Semaphore(ghisee.size());
        ghiseeFrecventate = new ArrayList<>();
    }

    public void getDocument(Client c) {
        try {
            waitingRoomClients.acquire();
            completeForm.acquire();
            getNumberOfOrder(c);
            completeForm.release();
            goNow(c);
            alegeGhiseu(c);
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
            ghiseuSemafor.acquire();
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
        Ghiseu g = ghisee.remove((int) (Math.random() * ghisee.size()));
        ghiseeFrecventate.remove(g);
        System.out.println(c + " si-a ales ghiseul " + g);
        waitingRoomClients.release();
        ghiseuSemafor.release();
        g.generateDocument(c);
        try {
            ghiseuSemafor.acquire();
            ghisee.add(g);
            ghiseeFrecventate.add(g);
            ghiseuSemafor.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ghiseeDisponibile.release();
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

    public void puneInPauza(){
        try {
            ghiseeDisponibile.acquire();
            ghiseuSemafor.acquire();
            Ghiseu g=null;
            if(ghiseeFrecventate.size()!=0) {
                g = ghiseeFrecventate.remove((int) (Math.random() * ghiseeFrecventate.size()));
                ghisee.remove(g);
            }
            ghiseuSemafor.release();
            if(g!=null) {
                System.out.println(g + " va intra in pauza de cafea");
                Task t = new Task(this, g);
                new Thread(t).start();
            }else{
                ghiseeDisponibile.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void revineDinPauza(Ghiseu g){
        try {
            ghiseuSemafor.acquire();
            System.out.println(g+ " a iesit din pauza de cafea");
            ghisee.add(g);
            ghiseuSemafor.release();
            ghiseeDisponibile.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Task implements Runnable {
    private Birou birou;
    private Ghiseu ghiseu;
    public Task(Birou b, Ghiseu g){
        birou = b;
        ghiseu = g;
    }

    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        birou.revineDinPauza(ghiseu);
    }
}
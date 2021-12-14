package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Birou {
    private static int numarBirouri = 1;
    private int id;
    private int sumaBani = 0;
    private int currentNumberOfOrder = 1;
    private int currentClient = 1;
    private List<Ghiseu> ghisee;
    private List<Ghiseu> ghiseeFrecventate;
    private List<Ghiseu> ghiseeInPauza;
    private final Semaphore ghiseeDisponibile;
    private final Semaphore waitingRoomClients;
    private final Semaphore completeForm;
    private final Semaphore numberOfOrderMutex = new Semaphore(1);
    private final Semaphore ghiseuSemafor = new Semaphore(1);
    private final Semaphore imprimanta = new Semaphore(1);
    private final Semaphore pos = new Semaphore(1);

    public Birou(ArrayList<Ghiseu> ghisee, int capacitateWaitingRoom, int numarMese) {
        this.ghisee = ghisee;
        this.id = numarBirouri++;
        this.ghisee.forEach(ghiseu -> ghiseu.setBirou(this));
        ghiseeDisponibile = new Semaphore(ghisee.size());
        waitingRoomClients = new Semaphore(capacitateWaitingRoom);
        completeForm = new Semaphore(numarMese);
        ghiseeFrecventate = new ArrayList<>();
        ghiseeInPauza = new ArrayList<>();
    }

    public int getDocument(Client c) {
            ajungeInWaitingRoom(c);
            completeazaFormular(c);
            obtineNumarDeOrdine(c);
            Ghiseu g = asteaptaLaCoada(c);
            int document = g.generateDocument(c);
            elibereazaGhiseu(c,g);
            return document;
    }

    public void ajungeInWaitingRoom(Client c){
        try {
            waitingRoomClients.acquire();
            System.out.println(c+" a ajuns in sala de asteptare de la Biroul "+ this);
            waitingRoomClients.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void completeazaFormular(Client c){
        try {
            completeForm.acquire();
            System.out.println(c+" a completat formularul pentru Biroul "+ this);
            completeForm.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void obtineNumarDeOrdine(Client c){
        try {
            numberOfOrderMutex.acquire();
            c.setNumberOfOrder(currentNumberOfOrder);
            System.out.println(c+" a obtinut numarul de ordine "+currentNumberOfOrder+ " in coada de la Biroul "+ this);
            currentNumberOfOrder++;
            numberOfOrderMutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized Ghiseu asteaptaLaCoada(Client c) {
        while (c.getNumberOfOrder() != currentClient) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            System.out.println(c + " e urmatorul client la rand la Biroul " + this);
            ghiseeDisponibile.acquire();
            currentClient++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Ghiseu g = alegeGhiseu(c);
        notifyAll();
        return g;
    }

    public Ghiseu alegeGhiseu(Client c){
        Ghiseu g = null;
        try {
            ghiseuSemafor.acquire();
            g = ghisee.remove((int) (Math.random() * ghisee.size()));
            ghiseeFrecventate.remove(g);
            System.out.println(c + " si-a ales ghiseul " + g);
            ghiseuSemafor.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return g;
    }

    public void elibereazaGhiseu(Client c, Ghiseu g){
        try {
            ghiseuSemafor.acquire();
            ghisee.add(g);
            ghiseeFrecventate.add(g);
            System.out.println(c+ " pleaca de la ghiseul "+g);
            ghiseuSemafor.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ghiseeDisponibile.release();
    }

    public int tiparesteDocument(Functionar f) {
        int costDocument = 0;
        try {
            imprimanta.acquire();
            System.out.println("Functionarul de la ghiseul " + f.getGhiseu() + " a utilizat imprimanta");
            costDocument = (int) ((Math.random() * (30 - 20)) + 20);
            imprimanta.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return costDocument;
    }

    public void efectueazaPlata(Functionar f, int costDocument) {
        try {
            pos.acquire();
            System.out.println("A fost efectuata plata la ghiseul " + f.getGhiseu() + " in valoare de "+ costDocument+ "lei");
            sumaBani = sumaBani + costDocument;
            pos.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return Integer.toString(id);
    }

    public void puneInPauza(PauzaCafea p){
        try {
            ghiseeDisponibile.acquire();
            Ghiseu g = obtineGhiseu();
            if(g!=null) {
                System.out.println(g + " va intra in pauza de cafea");
                Task t = new Task(this, g);
                Thread r = new Thread(t);
                p.add(r);
                r.start();
            }else{
                ghiseeDisponibile.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Ghiseu obtineGhiseu(){
        Ghiseu g = null;
        try {
            ghiseuSemafor.acquire();
            if(ghiseeFrecventate.size()>0 && ghiseeInPauza.size()<ghisee.size()/2) {
                g = ghisee.remove((int) (Math.random() * ghisee.size()));
                ghiseeFrecventate.remove(g);
                ghiseeInPauza.add(g);
            }
            ghiseuSemafor.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return g;
    }

    public void revineDinPauza(Ghiseu g){
        try {
            ghiseuSemafor.acquire();
            System.out.println(g+ " a iesit din pauza de cafea");
            ghisee.add(g);
            ghiseeInPauza.remove(g);
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
            Thread.sleep(1000);
            birou.revineDinPauza(ghiseu);
        } catch (InterruptedException e) {
            System.out.println("Ghiseul s-a inchis");
        }
    }
}
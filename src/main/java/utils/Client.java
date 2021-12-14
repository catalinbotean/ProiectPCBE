package utils;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Client extends Thread {
    private static AtomicInteger numberOfClients = new AtomicInteger(0);
    private String nume;
    private int numberOfOrder = 0;
    private List<Birou> birouri;

    public Client(String nume, List<Birou> birouri) {
        this.nume = nume;
        this.birouri = birouri;
        numberOfClients.incrementAndGet();
    }

    @Override
    public void run() {
        mergeLaBirouri();
        numberOfClients.decrementAndGet();
    }

    private void mergeLaBirouri(){
        for(Birou b : birouri){
            b.getDocument(this);
        }
    }

    public static int getNumberOfClients(){
        return numberOfClients.get();
    }

    public String toString() {
        return nume;
    }

    public void setNumberOfOrder(int numberOfOrder){
        this.numberOfOrder = numberOfOrder;
    }

    public int getNumberOfOrder(){
        return numberOfOrder;
    }

}
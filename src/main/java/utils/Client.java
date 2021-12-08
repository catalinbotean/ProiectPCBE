package utils;

import java.util.ArrayList;
import java.util.List;

public class Client extends Thread {
    private static int numberOfClients = 0;
    private int id;
    private String nume;
    private int numberOfOrder = 0;
    private List<Birou> birouri;
    private List<Integer> documentePrimite = new ArrayList<>();

    public Client(String nume, List<Birou> birouri) {
        this.nume = nume;
        this.birouri = birouri;
        this.id = numberOfClients;
        numberOfClients++;
    }

    @Override
    public void run() {
        goToDesks();
    }

    private void goToDesks(){
        for(Birou b : birouri){
            b.getDocument(this);
        }
    }

    public int getClientId(){ return id; }

    public String toString() {
        return nume;
    }

    public String getNume() {
        return nume;
    }

    public void setNumberOfOrder(int numberOfOrder){
        this.numberOfOrder = numberOfOrder;
    }

    public int getNumberOfOrder(){
        return numberOfOrder;
    }

}
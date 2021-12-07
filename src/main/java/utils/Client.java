package utils;

import java.util.ArrayList;
import java.util.List;

public class Client extends Thread {
    private static int numberOfClients = 0;
    private int id;
    private String nume;
    private List<Birou> birouri;
    private List<Integer> documentePrimite = new ArrayList<>();
    private volatile boolean clientSent = false;

    public Client(String nume, List<Birou> birouri) {
        this.nume = nume;
        this.birouri = birouri;
        this.id = numberOfClients;
        numberOfClients++;
    }

    @Override
    public void run() {
        while(this.documentePrimite.size() != 3){
            if(!clientSent){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sendClientToBirou();
            }
        }
    }

    private void sendClientToBirou(){
        clientSent = true;
        if(birouri.size() != 0){
            birouri.get(0).getDocument(this);
        }
    }


    public void primesteDocument(int nrDocument){
        this.documentePrimite.add(nrDocument);
        if(birouri.size() != 0){
            birouri.remove(0);
            System.out.println("Documentele clientului " + nume + this.documentePrimite);
            clientSent = false;
        }
    }

    public int getClientId(){ return id; }

    public String toString() {
        return nume;
    }

    public String getNume() {
        return nume;
    }
}
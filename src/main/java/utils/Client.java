package utils;

import java.util.ArrayList;

public class Client extends Thread {
    String nume;
    ArrayList<Birou> birouri;

    public Client(String nume, ArrayList<Birou> birouri) {
        this.nume = nume;
        this.birouri = birouri;
    }

    public void run() {
        for (Birou b : birouri) {
            if(b!=null)
                b.addClientToQueue(this);
        }

    }

    public String toString() {
        return nume;
    }
}
package utils;

import java.util.ArrayList;

public class Client extends Thread {
    String nume;
    Birou[] birouri = new Birou[5];
    int[] documente = new int[5];

    public Client(String nume, Birou[] birouri) {
        this.nume = nume;
        this.birouri = birouri;
        for(int i=0;i<= birouri.length;i++){
            if(birouri[i] != null)
                documente[i] = birouri[i].getDocument();
        }
    }

    public void run() {
        for (int i = 0; i <= birouri.length; i++)
            if(birouri[i] != null)
                birouri[i].addClientToQueue(this);
    }

    public String toString() {
        return nume;
    }
}
package utils;

import java.util.ArrayList;

public class Client extends Thread{
    String nume;
    ArrayList<Birou> birouri;
    //lista de documente  => lista de birouri la care trebuie sa mearga

    public Client(String nume, ArrayList<Birou> b) {
        this.nume = nume;
        //metoda citire documente json
        birouri = b;
    }

    public void getDocument(Ghiseu g){
        System.out.println(nume+ " a fost la " + g);
    }

    public void run() {
        for(Birou b: birouri){
            getDocument(b.getGhiseu1());
        }
    }

    public String toString(){
        return nume;
    }
}
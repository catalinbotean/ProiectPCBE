package utils;

public class Client extends Thread{
    String nume;
    //lista de documente


    public Client(String nume) {
        this.nume = nume;
        //metoda citire documente json
    }

    public void run() {
        System.out.println(nume+" is running");
    }

    public String toString(){
        return nume;
    }
}
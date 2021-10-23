package utils;

public class Functionar extends Thread{
    String nume;
    //ghiseu

    public Functionar(String nume) {
        this.nume = nume;
    }

    public void run() {
        System.out.println(nume+" is running");
    }
}

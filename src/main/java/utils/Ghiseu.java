package utils;

public class Ghiseu extends Thread{

    String name;
    public Ghiseu(String n){
        name = n;
    }

    @Override
    public void run() {
    }

    public String toString(){
        return name;
    }
}

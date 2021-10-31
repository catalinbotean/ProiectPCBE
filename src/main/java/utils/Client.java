package utils;

public class Client extends Thread{
    String nume;
    Document document;
    //lista de documente


    public Client(String nume,Document document) {
        this.nume = nume;
        this.document = document;
        //metoda citire documente json
    }

    public void run() {
        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                document.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String toString(){
        return nume;
    }
}
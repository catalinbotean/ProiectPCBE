package utils;

import java.util.ArrayList;

public class Client extends Thread{
    String nume;
    ArrayList<Birou> birouri;

    public Client(String nume, ArrayList<Birou> b) {
        this.nume = nume;
        birouri = new ArrayList<>();
        //metoda citire documente json
        getBirouFromDocument("Document 1 2", b);
    }

    public void run() {
        for(Birou b: birouri){
           b.addClientToQueue(this);
//           try {
//               Thread.sleep(200);
//           }catch(InterruptedException e)
//           {
//               e.printStackTrace();
//           }
        }

    }

    public void getBirouFromDocument(String numeDocument, ArrayList<Birou> b){
        String[] array = numeDocument.split(" ");
        for(int i = 1; i < array.length; i++){
            for(Birou birou : b){
                if(birou.getId() == Integer.parseInt(array[i])){
                    birouri.add(birou);
                }
            }
        }
    }

    public String toString(){
        return nume;
    }
}
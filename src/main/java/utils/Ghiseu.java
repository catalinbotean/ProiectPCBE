package utils;

public class Ghiseu extends Thread{
    Functionar functionar;
    Document document;

    public Ghiseu(Document document){
       // this.functionar = functionar;
        this.document = document;
    }

    @Override
    public void run() {
        int i = 0;
        while(true)
            document.put(i++);
    }
}

package utils;

public class Ghiseu extends Thread {

    private String name;
    private Birou b;
    private int documentNumberToPrint;
    private boolean taken = false;

    public Ghiseu(String n, int documentNumberToPrint) {
        name = n;
        this.documentNumberToPrint  = documentNumberToPrint;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this){
                if (!taken) {
                    taken = true;
                    takeClient(b);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    taken = false;
                }
            }
        }
    }

    public void setBirou(Birou b) {
        this.b = b;
    }

    public void takeClient(Birou b) {
        try {
            System.out.println(b.getQueue().take() + " " + name);
            b.goPrintDocument(this.documentNumberToPrint, this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return name;
    }
}

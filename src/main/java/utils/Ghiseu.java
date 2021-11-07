package utils;

public class Ghiseu extends Thread {

    private String name;
    private Birou b;
    private int documentNumberToPrint;
    private boolean taken = false;
    private boolean isCoffeeBreak = false;

    public Ghiseu(String n, int documentNumberToPrint) {
        name = n;
        this.documentNumberToPrint  = documentNumberToPrint;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this){
                if (!taken && !isCoffeeBreak) {
                    taken = true;
                    takeClient(b);
                    taken = false;
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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

    public boolean isTaken(){
        return taken;
    }

    public void takeCoffeeBreak(){
        try {
            this.isCoffeeBreak = true;
            System.out.println("n-ar mai trebui sa avem vreun client luat in " + name);
            Thread.sleep(10000);
            this.isCoffeeBreak = false;
            System.out.println("am iesit din pauza de masa");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return name;
    }
}
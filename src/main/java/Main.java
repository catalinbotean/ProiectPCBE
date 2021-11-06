import utils.Client;
import utils.Ghiseu;
import utils.Birou;
import java.util.ArrayList;

public class Main {

    public static void main(String args[]) {
       Ghiseu g1b1 = new Ghiseu("Ghiseu 1 Birou 1");
       Ghiseu g2b1 = new Ghiseu("Ghiseu 2 Birou 1");
       ArrayList<Ghiseu> ghiseeb1 = new ArrayList<>();
       ghiseeb1.add(g1b1);
       ghiseeb1.add(g2b1);
       Birou b1 = new Birou(ghiseeb1);
       g1b1.start();
       //g2b1.start();
        Ghiseu g1b2 = new Ghiseu("Ghiseu 1 Birou 2");
        Ghiseu g2b2 = new Ghiseu("Ghiseu 2 Birou 2");
        ArrayList<Ghiseu> ghiseeb2 = new ArrayList<>();
        ghiseeb2.add(g1b2);
        ghiseeb2.add(g2b2);
        Birou b2 = new Birou(ghiseeb2);

        ArrayList<Birou> birouri = new ArrayList<>();
        birouri.add(b1);
//        birouri.add(b2);

        for(int i=0;i<20;i++){
            new Client("Client "+i, birouri).start();
        }

    }
}
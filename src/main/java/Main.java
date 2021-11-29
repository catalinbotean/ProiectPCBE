import utils.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String args[]) {
        Ghiseu g1b1 = new Ghiseu("Ghiseu 1 Birou 1", 1);
        Ghiseu g2b1 = new Ghiseu("Ghiseu 2 Birou 1",2);
        Ghiseu g3b1 = new Ghiseu("Ghiseu 3 Birou 1",1);
        Ghiseu g4b1 = new Ghiseu("Ghiseu 4 Birou 1",1);
        Ghiseu g5b1 = new Ghiseu("Ghiseu 5 Birou 1",2);
        Ghiseu g6b1 = new Ghiseu("Ghiseu 6 Birou 1",2);
        ArrayList<Ghiseu> ghiseeb1 = new ArrayList<>(List.of(g1b1, g2b1));
//        ghiseeb1.add(g1b1);
//        ghiseeb1.add(g2b1);
        Birou b1 = new Birou(ghiseeb1);
        PauzaCafea pauzaCafea = new PauzaCafea(ghiseeb1);
        g1b1.start();
        g2b1.start();
        pauzaCafea.start();
//        g3b1.start();
//        g4b1.start();
//        g5b1.start();
//        g6b1.start();
        Ghiseu g1b2 = new Ghiseu("Ghiseu 1 Birou 2", 3);
        Ghiseu g2b2 = new Ghiseu("Ghiseu 2 Birou 2", 3);
        ArrayList<Ghiseu> ghiseeb2 = new ArrayList<>();
        ghiseeb2.add(g1b2);
        ghiseeb2.add(g2b2);
        Birou b2 = new Birou(ghiseeb2);

        ArrayList<Birou> birouri = new ArrayList<>();
        birouri.add(b1);
//        birouri.add(b2);

//        for (int i = 0; i < 40; i++) {
//            new Client("Client " + i).start();
//        }
        CitireJson.getClient();
    }
}
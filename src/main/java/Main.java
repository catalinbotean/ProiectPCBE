import utils.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String args[]) {
        Ghiseu g1b1 = new Ghiseu("Ghiseu 1 Birou 1", 1);
        Ghiseu g2b1 = new Ghiseu("Ghiseu 2 Birou 1",1);
        Ghiseu g3b1 = new Ghiseu("Ghiseu 3 Birou 1", 1);
        Ghiseu g4b1 = new Ghiseu("Ghiseu 4 Birou 1",1);
        ArrayList<Ghiseu> ghiseeb1 = new ArrayList<>(List.of(g1b1, g2b1, g3b1, g4b1));
        Birou b1 = new Birou(ghiseeb1);
        g1b1.start();
        g2b1.start();
        g3b1.start();
        g4b1.start();

        PauzaCafea pauzaCafea = new PauzaCafea(ghiseeb1);
        pauzaCafea.start();

        Ghiseu g1b2 = new Ghiseu("Ghiseu 1 Birou 2", 2);
        Ghiseu g2b2 = new Ghiseu("Ghiseu 2 Birou 2", 2);
        g1b2.start();
        g2b2.start();

        ArrayList<Ghiseu> ghiseeb2 = new ArrayList<>();
        ghiseeb2.add(g1b2);
        ghiseeb2.add(g2b2);
        Birou b2 = new Birou(ghiseeb2);

        Ghiseu g1b3 = new Ghiseu("Ghiseu 1 Birou 3", 3);
        Ghiseu g2b3 = new Ghiseu("Ghiseu 2 Birou 3", 3);
        Ghiseu g3b3 = new Ghiseu("Ghiseu 3 Birou 3", 3);
        g1b3.start();
        g2b3.start();
        g3b3.start();

        ArrayList<Ghiseu> ghiseeB3 = new ArrayList<>();
        ghiseeB3.add(g1b3);
        ghiseeB3.add(g2b3);
        ghiseeB3.add(g3b3);
        Birou b3 = new Birou(ghiseeB3);

        Ghiseu g1b4 = new Ghiseu("Ghiseu 1 Birou 4", 4);
        Ghiseu g2b4 = new Ghiseu("Ghiseu 2 Birou 4", 4);
        Ghiseu g3b4 = new Ghiseu("Ghiseu 3 Birou 4", 4);
        g1b4.start();
        g2b4.start();
        g3b4.start();
        ArrayList<Ghiseu> ghiseeB4 = new ArrayList<>();
        ghiseeB4.add(g1b4);
        ghiseeB4.add(g2b4);
        ghiseeB4.add(g3b4);
        Birou b4 = new Birou(ghiseeB4);

        Ghiseu g1b5 = new Ghiseu("Ghiseu 1 Birou 5", 5);
        Ghiseu g2b5 = new Ghiseu("Ghiseu 2 Birou 5", 5);
        Ghiseu g3b5 = new Ghiseu("Ghiseu 3 Birou 5", 5);
        g1b5.start();
        g2b5.start();
        g3b5.start();

        ArrayList<Ghiseu> ghiseeB5 = new ArrayList<>();
        ghiseeB5.add(g1b5);
        ghiseeB5.add(g2b5);
        ghiseeB5.add(g3b5);
        Birou b5 = new Birou(ghiseeB5);

        Birou [] birouri = new Birou [10];
        birouri[0]= b1;
        birouri[1]= b2;
        birouri[2] = b3;
        birouri[3] = b4;
        birouri[4] = b5;


        CitireJson.getClient(birouri);

    }
}
import utils.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String args[]) {
        Ghiseu g1b1 = new Ghiseu("Ghiseu 1 Birou 1", 1);
        Ghiseu g2b1 = new Ghiseu("Ghiseu 2 Birou 1",2);
        ArrayList<Ghiseu> ghiseeb1 = new ArrayList<>(List.of(g1b1, g2b1));
        Birou b1 = new Birou(ghiseeb1);
        PauzaCafea pauzaCafea = new PauzaCafea(ghiseeb1);
        g1b1.start();
        g2b1.start();
        pauzaCafea.start();
        Ghiseu g1b2 = new Ghiseu("Ghiseu 1 Birou 2", 3);
        Ghiseu g2b2 = new Ghiseu("Ghiseu 2 Birou 2", 3);
        ArrayList<Ghiseu> ghiseeb2 = new ArrayList<>();
        ghiseeb2.add(g1b2);
        ghiseeb2.add(g2b2);
        Birou b2 = new Birou(ghiseeb2);

        Birou [] birouri = new Birou [10];
        birouri[0]= b1;
        birouri[1]= b2;

        CitireJson.getClient(birouri);
    }
}
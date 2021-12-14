import utils.*;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<Birou> birouri = CitireJson.getBirouri();
        CitireJson.getClient(birouri);
        PauzaCafea pauzaCafea = new PauzaCafea(birouri,CitireJson.getGhisee());
        pauzaCafea.start();
    }
}
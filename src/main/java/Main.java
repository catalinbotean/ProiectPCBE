import utils.Client;
import utils.Document;
import utils.Functionar;
import utils.Ghiseu;

public class Main {

    public static void main(String args[]) {
        Document document = new Document();
        Ghiseu ghiseu = new Ghiseu(document);
        Client client = new Client("alex",document);
        Client client2 = new Client("adi",document);
        Client client3 = new Client("cata",document);
        Client client4 = new Client("bia",document);
        Client client5 = new Client("cristi",document);

        client.setName("alex");
        client2.setName("adi");
        client3.setName("cata");
        client4.setName("bia");
        client5.setName("cristi");

        ghiseu.start();
        client.start();
        client2.start();
        client3.start();
        client4.start();
        client5.start();

//        Functionar functionar = new Functionar("F");
//        functionar.start();
//        for(int i=0;i<20;i++){
//            new Client("Client "+i).start();
//        }
    }
}
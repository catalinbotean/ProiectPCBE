import utils.Client;
import utils.Functionar;

public class Main {
    public static void main(String args[]) {
        Functionar functionar = new Functionar("F");
        functionar.start();
        for(int i=0;i<20;i++){
            new Client("Client "+i).start();
        }
    }
}
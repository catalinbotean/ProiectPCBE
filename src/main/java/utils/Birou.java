package utils;
import java.util.ArrayList;

public class Birou {
    private static int numarBirouri = 1;
    private int id;
    private ArrayList<Ghiseu>  ghisee;
    public Birou(ArrayList<Ghiseu> ghisee){
        this.ghisee = ghisee;
        this.id = numarBirouri++;
    }

    public Ghiseu getGhiseu1(){
        for(Ghiseu g: ghisee)
            return g;
        return null;
    }

    public int getId() {
        return id;
    }
}

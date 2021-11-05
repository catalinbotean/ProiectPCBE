package utils;
import java.util.ArrayList;

public class Birou {
    private ArrayList<Ghiseu>  ghisee = new ArrayList<>();
    public Birou(ArrayList<Ghiseu> ghisee){
        this.ghisee = ghisee;
    }

    public Ghiseu getGhiseu1(){
        for(Ghiseu g: ghisee)
            return g;
        return null;
    }
}

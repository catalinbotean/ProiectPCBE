package utils;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.io.Reader;
import java.io.FileReader;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class CitireJson
{
    private static ArrayList <Ghiseu> ghiseeSistem = new ArrayList<>();

    public static JSONArray readFromFiles(String fileName, String jsonObjName)
    {
        JSONParser parser=new JSONParser();
        JSONObject list=new JSONObject();
        try{
            Reader reader=new FileReader(fileName);
            list= (JSONObject) parser.parse(reader);
        }catch(IOException e){
            e.printStackTrace();
        }catch(ParseException e){
            e.printStackTrace();
        }
        return (JSONArray) list.get(jsonObjName);
    }

    public static ArrayList<Birou> getBirouri(){
        JSONArray list=CitireJson.readFromFiles("src/main/resources/Birouri.json","birouri");
        ArrayList <Birou> birouri = new ArrayList();
        Iterator<JSONObject> it = list.iterator();
        while(it.hasNext()) {
            JSONObject obj = it.next();
            ArrayList<Ghiseu> ghisee = new ArrayList<>();
            int numarGhisee = Integer.parseInt((String)obj.get("numarGhisee"));
            int capacitateWaitingRoom = Integer.parseInt((String)obj.get("capacitateWaitingRoom"));
            int numarMese = Integer.parseInt((String)obj.get("numarMese"));
            for(int i=0;i<numarGhisee;i++){
                String nume = "Ghiseu "+ (i+1) +" "+ obj.get("nume");
                ghisee.add(new Ghiseu(nume, Integer.parseInt((String)obj.get("document"))));
            }
            Birou b = new Birou(ghisee, capacitateWaitingRoom, numarMese);
            birouri.add(b);
            CitireJson.ghiseeSistem.addAll(ghisee);
        }
        return birouri;
    }

    public static ArrayList<Client> getClient(ArrayList<Birou> birouri){
        JSONArray list=CitireJson.readFromFiles("src/main/resources/ClientiSiDocumente.json","clienti");
        ArrayList <Client> clienti = new ArrayList();
        Iterator<JSONObject> it = list.iterator();
        while(it.hasNext()) {
            JSONObject obj = it.next();
            List<Birou> birou = new ArrayList<>();
            JSONArray listaDocumente = (JSONArray) obj.get("documente");
            Iterator<JSONObject> itDoc = listaDocumente.iterator();
            while(itDoc.hasNext()){
                birou.add(birouri.get(Integer.parseInt((String)itDoc.next().get("birou"))-1));
            }
            Client c = new Client((String)obj.get("nume"), birou);
            c.start();
            clienti.add(c);
        }
        return clienti;
    }

    public static ArrayList<Ghiseu> getGhisee(){
        return ghiseeSistem;
    }

}

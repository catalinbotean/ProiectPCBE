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


public class CitireJson
{

    public static JSONArray readFromFiles(String fileName)
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
        return (JSONArray) list.get("clienti");
    }

    public static ArrayList<Client> getClient(Birou[] birouri){
        JSONArray list=CitireJson.readFromFiles("src/main/resources/ClientiSiDocumente.json");
        ArrayList <Client> clienti = new ArrayList();
        Iterator<JSONObject> it = list.iterator();
        while(it.hasNext()) {
            JSONObject obj = it.next();
            Birou[] birou = new Birou[5];
            birou[0] = birouri[Integer.parseInt((String)obj.get("doc1"))-1];
            System.out.println(birou[0]);

            birou[1] = birouri[Integer.parseInt((String)obj.get("doc2"))-1];
            System.out.println(birou[1]);
            birou[2] = birouri[Integer.parseInt((String)obj.get("doc3"))-1];
            System.out.println(birou[2]);
            Client c = new Client((String)obj.get("name"), birou);
            c.start();
            clienti.add(c);
        }
        return clienti;
    }

}

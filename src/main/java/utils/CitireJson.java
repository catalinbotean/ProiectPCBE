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
    public static ArrayList<Client> getClient(){
        JSONArray list=CitireJson.readFromFiles("src/main/resources/ClientiSiDocumente.json");
        ArrayList <Client> clienti = new ArrayList();
        Iterator<JSONObject> it = list.iterator();
        while(it.hasNext()) {
            JSONObject obj = it.next();
            Client c = new Client((String)obj.get("name"));
            System.out.println(c);
            clienti.add(c);
        }
        return clienti;
    }

}

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
            List<Birou> birou = new ArrayList<>();
            birou.add(birouri[Integer.parseInt((String)obj.get("doc1"))-1]);
            birou.add(birouri[Integer.parseInt((String)obj.get("doc2"))-1]);
            birou.add(birouri[Integer.parseInt((String)obj.get("doc3"))-1]);
            Client c = new Client((String)obj.get("name"), birou);
            c.start();
            clienti.add(c);
        }
        return clienti;
    }

}

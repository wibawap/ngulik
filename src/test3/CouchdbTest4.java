package test3;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.lightcouch.CouchDbClient;
import org.lightcouch.DesignDocument;
import org.lightcouch.Response;

import com.google.gson.JsonObject;

public class CouchdbTest4 {
	
	public static void main(String[] args) {
        CouchDbClient dbClient = new CouchDbClient("coba", true, "http", "127.0.0.1", 5984, "admin", "admin123");
        DesignDocument designDoc;
        designDoc = dbClient.design()
                .getFromDesk("example");
        Response response; 
        response = dbClient.design()
                .synchronizeWithDb(designDoc);
        
        Scanner sc = new Scanner(System.in);
        System.out.print("First name: ");
        String firstName = sc.nextLine();
        System.out.print("Middle name: ");
        String middleName = sc.nextLine();
        System.out.print("Last name: ");
        String lastName = sc.nextLine();
        
        String[] keys = {firstName, lastName};
        int x;
        x = dbClient.view("example/by_name")
            .key((Object[])keys)
                .query(JsonObject.class).size();
        
        if (x > 0) {
            System.out.println("Found a copy");
        }
        Integer i = 0;
       
       // querying without key values gets all the
       // documents of the given type 
       int count = dbClient.view("example/by_name")
               .query(JsonObject.class).size();
       
       System.out.println("count = " + count);
       i = count + 1;
       
       if (x == 0) {
        Map<String, Object> map;
        map = new HashMap<>();
        map.put("_id", i.toString());
        map.put("first", firstName);
        map.put("middle", middleName);
        map.put("last", lastName);
        dbClient.save(map);
        }
        System.out.println("Found " + (i - 1));
        
        for(Integer j = 1; 
                dbClient.contains(j.toString()); j++) {
            JsonObject json;
            json = dbClient
                    .find(JsonObject.class, 
                    j.toString());
            System.out.println(json.get("first") + " "
                    + json.get("middle") + " " 
                    + json.get("last"));
            
        }
        
        dbClient.shutdown();
    }

}

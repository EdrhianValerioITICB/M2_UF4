package cat.iticbcn.edrhian;

import java.util.Scanner;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;

import static com.mongodb.client.model.Sorts.ascending;

public class JocDeCartes {
	static Scanner sc = new Scanner(System.in);
	static String url = "mongodb://localhost:27017";
    static MongoClient mongoClient = MongoClients.create(url);

    public static void insertCarta(MongoClient mongoClient, String name, String type, String rarity, String ability, int power, int cost) {
    	MongoDatabase database = mongoClient.getDatabase("Edrhian");
        MongoCollection<Document> collection = database.getCollection("Magic");
        
        InsertOneResult result = collection.insertOne(new Document() 
        		.append("_id", new ObjectId())
        		.append("name", name)
        		.append("type", type)
        		.append("rarity", rarity)
        		.append("ability", ability)
        		.append("power", power)
        		.append("cost", cost));
        
        System.out.println("Id del document insertat: " + result.getInsertedId());
    }
    
    public static void getCartesTipus(MongoClient mongoClient, String tipus) {
        MongoDatabase database = mongoClient.getDatabase("Edrhian");
        MongoCollection<Document> collection = database.getCollection("Magic");

        
        FindIterable<Document> doc = collection.find(Filters.eq("type",tipus)).sort(ascending("name"));

        for (Document dc:doc) {
            if (dc != null) {
                for (String key:dc.keySet()){
                    System.out.println(key + ": "  + dc.get(key));
                }
                System.out.println("--------------------------------");
            } else System.out.println("No matching documents found.");
        }
    }

    public static void main( String[] args ) {
        int opcio = -1;
        
        while(opcio != 3) {
        	System.out.print("\nEscull una d'aquestes opcions: \n"
                    + "1-Llistar cartes \n"
                    + "2-Insertar nova carta \n"
                    + "3-Sortir del programa \n");
            opcio = sc.nextInt();
            
            if(opcio == 1) {
            	System.out.println("Escriu el tipus de carta: ");
            	String tipus = sc.next();
            	if(tipus != null) {
                	getCartesTipus(mongoClient, tipus);
            	} else {
            		System.out.println("Has d'escriure un tipus de carta");
            	}
            	
            } else if(opcio == 2) {
            	String name;
            	String type;
            	String rarity;
            	String ability;
            	int power;
            	int cost;
            	System.out.println("Afageix una carta");
            	
            	System.out.println("Nom: ");
            	name = sc.next();
            	
            	System.out.println("Tipus: ");
            	type = sc.next();
            	
            	System.out.println("Raresa: ");
            	rarity = sc.next();
            	
            	System.out.println("Habilitat: ");
            	ability = sc.next();
            	
            	System.out.println("Poder: ");
            	power = sc.nextInt();
            	
            	System.out.println("Cost: ");
            	cost = sc.nextInt();
            	
            	insertCarta(mongoClient, name, type, rarity, ability, power, cost);
            	
            } else if(opcio == 3) {
            	break;
            } else {
            	
            }
        }
        
        System.out.println("Has sortit del programa");        
    }
}

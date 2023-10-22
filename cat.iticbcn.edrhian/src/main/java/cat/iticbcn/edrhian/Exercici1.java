package cat.iticbcn.edrhian;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import static javax.management.Query.eq;

public class Exercici1 {
    static String uri = "mongodb://localhost:27017";
    static MongoClient mongoClient = MongoClients.create(uri);

    public static void getPelisAny(MongoClient mongoClient, int any) {
        MongoDatabase database = mongoClient.getDatabase("moviedb");
        MongoCollection<Document> collection = database.getCollection("movies");

        FindIterable<Document> doc = collection.find(Filters.eq("year",any));

        for (Document dc:doc) {
            if (dc != null) {
                for (String key:dc.keySet()){
                    System.out.println(key + ": "  + dc.get(key));
                }
            } else System.out.println("No matching documents found.");
        }
    }

    public static void main( String[] args ) {
        final int any = 1990;
        getPelisAny(mongoClient,any);

    }
}
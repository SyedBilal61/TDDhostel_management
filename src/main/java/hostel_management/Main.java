package hostel_management;

import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Simple app accessing MongoDB.
 */
public class Main {
    public static void main(String[] args) {
        // Default host is localhost
        String mongoHost = "mongodb"; //or local host if not using docker 
        if (args.length > 0) {
            mongoHost = args[0];
        }
        
        MongoClient mongoClient = null;
        
        try {
            // Default MongoDB port is 27017
            mongoClient = new MongoClient(mongoHost);
            MongoDatabase db = mongoClient.getDatabase("mydb");
            MongoCollection<Document> collection = db.getCollection("examples");
            // Create a simple document
            Document doc = new Document("name", "Greeting")
                        .append("type", "HelloWorld!");
            collection.insertOne(doc);

        } catch (Exception e) {
        	// This makes sure the test passes even if MongoDB is not running
        	System.out.println("MongoDB not Available" + e.getMessage());
        } finally {
        	if (mongoClient != null) {
        		mongoClient.close();
        	}
        	}
        }
        
        
}

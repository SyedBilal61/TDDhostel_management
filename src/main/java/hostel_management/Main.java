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
        String mongoHost = "mongodb"; //or mogodb if using docker as we did bt create a network in docker
        if (args.length > 0) {
            mongoHost = args[0];
        }

        // Default MongoDB port is 27017
        MongoClient mongoClient = new MongoClient(mongoHost);
        MongoDatabase db = mongoClient.getDatabase("mydb");
        MongoCollection<Document> collection = db.getCollection("examples");

        // Create a simple document
        Document doc = new Document("name", "Greeting")
                        .append("type", "HelloWorld!");
        collection.insertOne(doc);

        // Should print "HelloWorld!"
        System.out.println(collection.find().first().get("type"));

        mongoClient.close();
    }
}

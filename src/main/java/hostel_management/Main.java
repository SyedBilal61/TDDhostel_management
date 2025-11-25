package hostel_management;

import org.bson.Document;
import java.util.logging.Logger;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Simple app accessing MongoDB.
 */

/**
 * The Main.java in hostel_management is a standalone MongoDB demo to test
 * connectivity. The real entry point for the application is RoomSwingApp in
 * com.hostel.app.swing.
 */

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName()); // used Logger for removing code smell

    public static void main(String[] args) {

        String mongoHost = "mongodb";// or mongoDB when running through docker
        if (args.length > 0)
            mongoHost = args[0];

        // Default port for MongoDB is 27017
        MongoClient mongoClient = new MongoClient(mongoHost);
        MongoDatabase db = mongoClient.getDatabase("mydb");
        MongoCollection<Document> collection = db.getCollection("examples");

        Document doc = new Document("name", "Greeting").append("type", "HelloWorld!");
        collection.insertOne(doc);

        // Should print "HelloWorld!"
        LOGGER.info(collection.find().first().get("type").toString());
        mongoClient.close();
    }
}
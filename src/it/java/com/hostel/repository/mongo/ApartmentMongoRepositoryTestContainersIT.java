package com.hostel.repository.mongo;

import static org.junit.Assert.assertNotNull;

import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.MongoDBContainer;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class ApartmentMongoRepositoryTestContainersIT {

	// Start a temporary MongoDB container for this test
    
	
    @ClassRule
    public static final MongoDBContainer mongo = new MongoDBContainer("mongo:4.4.3");

    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> apartmentCollection;

    @Before
    public void setup() {
        // Connect to the MongoDB container
        client = new MongoClient(
                new ServerAddress(
                        mongo.getHost(),
                        mongo.getFirstMappedPort()
        )
        );
        
     // Use a test database
        database = client.getDatabase("hostel_db");
        database.drop(); // ensure clean start

        // Use a collection for apartments
        apartmentCollection = database.getCollection("apartments");
    }
    
    @After
    public void tearDown() {
        // Close MongoDB connection after test
        if (client != null) {
            client.close();
        }
    }


    @Test
    public void simpleTestToCheckConnectivity() {
    	assertNotNull(database);
    	assertNotNull(apartmentCollection);
    	
    }
    
}

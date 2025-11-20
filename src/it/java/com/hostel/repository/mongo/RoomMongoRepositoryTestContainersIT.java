package com.hostel.repository.mongo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.List;

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

import hostel_management.Room;

public class RoomMongoRepositoryTestContainersIT {

	// Start a temporary MongoDB container for this test
    
	
    @ClassRule
    public static final MongoDBContainer mongo = new MongoDBContainer("mongo:4.4.3");

    private MongoClient client;
    private RoomMongoRepository roomRepository;
    private MongoCollection<Document> roomCollection;
    private MongoDatabase database;

    
    
    private static final String ROOM_DB_NAME = "hosteldb";
    private static final String ROOM_COLLECTION_NAME= "rooms";
    
    
    
    
    
    @Before
    
    public void setup() {
        // Connect to the MongoDB container
        client = new MongoClient(
                new ServerAddress(
                        mongo.getContainerIpAddress(),
                        mongo.getFirstMappedPort()
        )
        );
        
        roomRepository = new RoomMongoRepository(client);
        database =client.getDatabase(ROOM_DB_NAME);
        database.drop();
        roomCollection = database.getCollection(ROOM_COLLECTION_NAME);
    
    }
        
           
        
        
    @After
    
    public void tearDown() {
        // Close MongoDB connection after test
            client.close();
    }


    private void addTestRoomToDataBase(String roomNumber, String tenant) {
    	
    	roomCollection.insertOne(new Document()
    			.append("roomNumber", roomNumber)
    			.append("tenant", tenant));
    
    }
        
        
        
        
    @Test
    public void simpleTestToCheckConnectivity() {
    	assertNotNull(database);
    	assertNotNull(roomCollection);
    	
    }
    
    
    
    
    
    @Test 
    public void testFindAll() {
    	addTestRoomToDataBase("A1", "Zain");
    	addTestRoomToDataBase("A2", "Ali");
    	
    	List <Room> rooms = roomRepository.findAll();
    	assertThat(rooms).hasSize(2);
    	
    	Room r1 = rooms.get(0);
    	assertThat(r1.getRoomNumber()).isEqualTo("A1");
    	assertThat(r1.getTenant()).isEqualTo("Zain");
    	
    	Room r2 = rooms.get(1);
    	assertThat(r2.getRoomNumber()).isEqualTo("A2");
    	assertThat(r2.getTenant()).isEqualTo("Ali");
    	}
    
    
    
    @Test 
    public void testFindByRoomNumber() {
        
    	addTestRoomToDataBase("A1", "Zain");
        Room result = roomRepository.findByRoomNumber("A1");
    	
    	
    	assertThat(result.getRoomNumber()).isEqualTo("A1");
    	assertThat(result.getTenant()).isEqualTo("Zain");
    	
        }
    
    
    
    
    @Test 
    public void testSave() {
    	
    	Room r1 = new Room("A1");
    	r1.assignTenant("Zain");
    	
    	roomRepository.save(r1);
    	
    	Room saved = roomRepository.findByRoomNumber("A1");    	
    	
    	
    	assertThat(saved.getRoomNumber()).isEqualTo("A1");
    	assertThat(saved.getTenant()).isEqualTo("Zain");
    }
    
    
    
    
    @Test 
    public void testvacate () {
    	addTestRoomToDataBase("A1", "Zain");
    	roomRepository.vacate("A1");
    	
    	
       Room r = roomRepository.findByRoomNumber("A1");
       assertThat(r).isNotNull();       //the room still exists
       assertThat(r.getTenant()).isNull();   //tenant should be null
       assertThat(r.isAvailable()).isTrue();  // room should be availble 
    		  		   
    }
    
    //The Following Tests are introduced to Improve the code Coverage
    
    @Test
    public void testFindByRoomNumberNotFound() {
        Room result = roomRepository.findByRoomNumber("NON_EXISTENT");
        assertThat(result).isNull();  
    }
    
   
    
    @Test
    public void testFromDocumentToRoomVacateBranches() {
        // Empty tenant
        addTestRoomToDataBase("B1", "");
        Room r1 = roomRepository.findByRoomNumber("B1");
        assertThat(r1.getTenant()).isNull();  

        // Null tenant
        addTestRoomToDataBase("B2", null);
        Room r2 = roomRepository.findByRoomNumber("B2");
        assertThat(r2.getTenant()).isNull();  
    }

   
    
    @Test
    public void testVacateNonExistentRoom() {
        // Should do nothing, just safe execution
        roomRepository.vacate("NON_EXISTENT");  
    }

    

    
  
    
    
    
    
    }

    

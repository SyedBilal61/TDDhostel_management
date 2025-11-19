package com.hostel.repository.mongo;

import static com.mongodb.client.model.Filters.eq;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.Document;

import com.hostel.repository.RoomRepository;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;

import hostel_management.Room;

public class RoomMongoRepository  implements RoomRepository
{

    private static final String ROOM_DB_NAME = "hosteldb";
    private static final String ROOM_COLLECTION_NAME = "rooms";

    private final MongoCollection<Document> roomCollection;

    public RoomMongoRepository(MongoClient client) {
        MongoDatabase database = client.getDatabase(ROOM_DB_NAME);
        this.roomCollection = database.getCollection(ROOM_COLLECTION_NAME);
    }
    
    
    
    
    public void dropAll() {
       
            roomCollection.drop();
    }
    
    
    
    @Override
    public List<Room> findAll() {
        return StreamSupport.stream(roomCollection.find().spliterator(), false)
                .map(this::fromDocumentToRoom)
                .collect(Collectors.toList());
    }

    
    
    
    @Override
    public Room findByRoomNumber(String roomNumber) {
        Document doc = roomCollection.find(eq("roomNumber", roomNumber)).first();
        if (doc != null) {
            return fromDocumentToRoom(doc);
        }
        return null;
    }

    
    
    @Override
    public void save(Room room) {
        Document doc = new Document()
                .append("roomNumber", room.getRoomNumber())
                .append("tenant", room.getTenant());
        
     // Use upsert: insert new or replace existing
        roomCollection.replaceOne(eq("roomNumber", room.getRoomNumber()), doc, new ReplaceOptions().upsert(true));
    }

    
    

    // 

    private Room fromDocumentToRoom(Document doc) {
        
    	Room room = new Room(doc.getString("roomNumber"));
        String tenant = doc.getString("tenant");
        
        
        if (tenant != null) {
        	if(!tenant.isEmpty()) {
        		room.assignTenant(tenant);
        	} else {
        		room.vacate();
        	}
        
        
        
        }else 
        room.vacate();
        
        return room;
    }



	@Override
	public void vacate(String roomNumber) {
		
		Room room = findByRoomNumber(roomNumber);
		if (room != null) {
			room.vacate();
			save(room);
		}
		
		
		
		
	}
}

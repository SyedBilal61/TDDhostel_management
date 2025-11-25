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

/* MongoDB implementation of RoomRepository */
public class RoomMongoRepository implements RoomRepository {

    /* MongoDB collection for rooms */
    private final MongoCollection<Document> roomCollection;
    
    //Created this constant to remove code smells to use multiple times in the code
    private static final String ROOM_NUMBER_FIELD = "roomNumber";
    
    
    

    /* Constructor: get the collection from MongoDB database */
    public RoomMongoRepository(MongoClient client, String databaseName, String collectionName) {
        MongoDatabase database = client.getDatabase(databaseName);
        this.roomCollection = database.getCollection(collectionName);
    }

    @Override
    public List<Room> findAll() {
        return StreamSupport.stream(roomCollection.find().spliterator(), false).map(this::fromDocumentToRoom)
                .toList();
    }

    @Override
    public Room findByRoomNumber(String roomNumber) {
        Document doc = roomCollection.find(eq(ROOM_NUMBER_FIELD, roomNumber)).first();
        if (doc != null) {
            return fromDocumentToRoom(doc);
        }
        return null;
    }

    @Override
    public void save(Room room) {
        Document doc = new Document().append(ROOM_NUMBER_FIELD, room.getRoomNumber()).append("tenant", room.getTenant());

        // Use upsert: insert new or replace existing
        roomCollection.replaceOne(eq(ROOM_NUMBER_FIELD, room.getRoomNumber()), doc, new ReplaceOptions().upsert(true));
    }

    /* Convert MongoDB document to Room object */

    private Room fromDocumentToRoom(Document doc) {

        Room room = new Room(doc.getString(ROOM_NUMBER_FIELD));
        String tenant = doc.getString("tenant");

        if (tenant != null) {
            if (!tenant.isEmpty()) {
                room.assignTenant(tenant);
            } else {
                room.vacate();
            }

        } else
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

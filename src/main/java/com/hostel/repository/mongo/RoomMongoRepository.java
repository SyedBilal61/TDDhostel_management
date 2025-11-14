package com.hostel.repository.mongo;

import static com.mongodb.client.model.Filters.eq;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import hostel_management.Room;

public class RoomMongoRepository {

    private static final String ROOM_DB_NAME = "hosteldb";
    private static final String ROOM_COLLECTION_NAME = "rooms";

    private final MongoCollection<Document> roomCollection;

    public RoomMongoRepository(MongoClient client) {
        MongoDatabase database = client.getDatabase(ROOM_DB_NAME);
        this.roomCollection = database.getCollection(ROOM_COLLECTION_NAME);
    }

    
    public List<Room> findAll() {
        return StreamSupport.stream(roomCollection.find().spliterator(), false)
                .map(this::fromDocumentToRoom)
                .collect(Collectors.toList());
    }

    public Room findByRoomNumber(String roomNumber) {
        Document doc = roomCollection.find(eq("roomNumber", roomNumber)).first();
        if (doc != null) {
            return fromDocumentToRoom(doc);
        }
        return null;
    }

    public void save(Room room) {
        Document doc = new Document()
                .append("roomNumber", room.getRoomNumber())
                .append("tenant", room.getTenant());
        roomCollection.insertOne(doc);
    }

    public void delete(String roomNumber) {
        roomCollection.deleteOne(eq("roomNumber", roomNumber));
    }

    // 

    private Room fromDocumentToRoom(Document doc) {
        Room room = new Room(doc.getString("roomNumber"));
        room.assignTenant(doc.getString("tenant"));
        return room;
    }
}

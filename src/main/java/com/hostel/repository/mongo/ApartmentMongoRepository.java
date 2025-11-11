/*
 * // this whole file is a draft for creating in memory data base and i left it
 * becuase may be we don;t use i am jumping to next phase so nee we can come
 * again and modify it package com.hostel.repository.mongo;
 * 
 * import java.util.ArrayList; import java.util.List;
 * 
 * import org.bson.Document;
 * 
 * import com.hostel.repository.ApartmentRepository; import
 * com.mongodb.MongoClient; import com.mongodb.client.MongoCollection; import
 * com.mongodb.client.MongoCursor;
 * 
 * import hostel_management.Apartment;
 * 
 * public class ApartmentMongoRepository implements ApartmentRepository {
 * 
 * public static final String HOSTEL_DB_NAME = "hostelDB"; public static final
 * String APARTMENT_COLLECTION_NAME = "apartment";
 * 
 * private MongoCollection<Document> apartmentCollection;
 * 
 * public ApartmentMongoRepository(MongoClient client) { apartmentCollection =
 * client .getDatabase(HOSTEL_DB_NAME)
 * .getCollection(APARTMENT_COLLECTION_NAME); }
 * 
 * @Override public List<Apartment> findAll() { List<Apartment> apartments = new
 * ArrayList<>(); try (MongoCursor<Document> cursor =
 * apartmentCollection.find().iterator()) { while (cursor.hasNext()) { Document
 * doc = cursor.next(); apartments.add(toApartment(doc)); } } return apartments;
 * }
 * 
 * @Override public Apartment findById(String apartmentName) { Document query =
 * new Document("name", apartmentName); Document doc =
 * apartmentCollection.find(query).first(); return doc != null ?
 * toApartment(doc) : null; }
 * 
 * @Override public void save(Apartment apartment) { Document doc = new
 * Document("name", apartment.getName()) .append("rooms", apartment.getRooms());
 * apartmentCollection.insertOne(doc); }
 * 
 * @Override public void delete(String apartmentName) { Document query = new
 * Document("name", apartmentName); apartmentCollection.deleteOne(query); }
 * 
 * // Helper to convert MongoDB document → Apartment object private Apartment
 * toApartment(Document doc) { Apartment apartment = new Apartment();
 * apartment.setName(doc.getString("name"));
 * apartment.setRooms(doc.getInteger("rooms", 0)); return apartment; } }
 */
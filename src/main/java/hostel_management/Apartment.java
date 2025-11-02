package hostel_management;

import java.util.ArrayList;
import java.util.List;

public class Apartment {
	
	private List<Room> rooms = new ArrayList<>();
	
	public Apartment(String apartmentName) {
		for (int i=0 ; i<7 ; i++) {
			
			rooms.add(new Room(apartmentName + i));
		}
	}
	public List<Room> getrooms(){
		return rooms;
	}
	public void assignRoom(int index, String tenantName) {
	    rooms.get(index).assignTenant(tenantName);
	}
	public void vacateRoom(int index) {
		rooms.get(index).vacate();
	}
	public int  countAvailableRooms() {
	int count=0;
	for (Room r: rooms) {
		if(r.isAvailable()) {
			count++;
	}
		}
	return count;
	}
	
	public boolean isEmpty() {
		for (Room r:rooms) {
			if (!r.isAvailable()) {
				return false ;
			}
		}
		return true;
	}
	public boolean isFull() {
		for (Room r: rooms ) {
			if(r.isAvailable()) {
				return false;
		}
		
	}
		return true;
	}
	
	
}


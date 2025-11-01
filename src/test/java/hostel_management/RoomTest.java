package hostel_management;

import org.junit.Test;
import static org.junit.Assert.*;
public class RoomTest {
	// Room is intially Availble 
	@Test 
	public void testRoomInitiallyAvailable() {
		Room room = new Room("A1");
		assertTrue(room.isAvailable());
	}
	//Assign room and mark room as occupied 
	@Test
	public void testAssignRoom() {
		Room room = new Room("A1");
		room.assignTenant("Ali");
		assertFalse(room.isAvailable());
	}
}

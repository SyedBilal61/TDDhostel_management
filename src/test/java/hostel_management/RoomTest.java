package hostel_management;

import org.junit.Test;
import static org.junit.Assert.*;
public class RoomTest {

	@Test
	public void testRoomInitiallyAvailable() {
		Room room = new Room("A1");
		assertTrue(room.isAvailable());
	}
}

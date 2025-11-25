package hostel_management;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RoomTest {
    // Room is intially Availble
    @Test
    public void testRoomInitiallyAvailable() {
        Room room = new Room("A1");
        assertTrue(room.isAvailable());
    }

    // Assign room and mark room as occupied
    @Test
    public void testAssignRoom() {
        Room room = new Room("A1");
        room.assignTenant("Ali");
        assertFalse(room.isAvailable());
    }

    // vacate/empty the room
    @Test
    public void testVacateRoom() {
        Room room = new Room("A1");
        room.assignTenant("Ali");
        room.vacate();
        assertTrue(room.isAvailable()); // Room free again
    }

    // Prevent Double Assignment
    @Test(expected = IllegalStateException.class)
    public void testPreventDoubleAssigment() {
        Room room = new Room("A1");
        room.assignTenant("Ali"); // 1st
        room.assignTenant("Zain"); // 2nd

    }

    @Test
    public void testToString() {
        Room emptyRoom = new Room("A1");                 // tenant is null
        assertEquals("A1 (Empty)", emptyRoom.toString());

        Room emptyTenantRoom = new Room("B1");           // tenant is empty string
        emptyTenantRoom.assignTenant("");
        assertEquals("B1 (Empty)", emptyTenantRoom.toString());

        Room occupiedRoom = new Room("C1");              // tenant is assigned
        occupiedRoom.assignTenant("Zain");
        assertEquals("C1 Zain", occupiedRoom.toString());
    }

}
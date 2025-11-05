package hostel_management;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class ApartmentTest {
	// check intially apartment has seven rooms 
	@Test
	public void testApartmentHasSevenRooms() {
		Apartment apt = new Apartment("A"); //create aparment
		assertEquals(7,	apt.getrooms().size());	
	}
	//Assign tenant To Room
	@Test
	public void testAssignTenantToRoom() {
		Apartment apt = new Apartment("A"); //create aparment
		apt.assignRoom(0, "Ali");
		assertEquals("Ali", apt.getrooms().get(0).getTenant());
	}
	//Vacate room through apartment 
	@Test
	public void testVacateRoomThroughApartment() {
		Apartment apt = new Apartment("A"); //create aparment
		apt.assignRoom(0, "Ali"); //first assign 
		apt.vacateRoom(0); //vacate 
		assertTrue(apt.getrooms().get(0).isAvailable());
	}
	// Prevent Double assigment through Apartment 
	@Test(expected= IllegalStateException.class)
	public void testPreventDoubleAssignmentThroughApartment() {
		Apartment apt = new Apartment("A"); //create aparment
		apt.assignRoom(0, "Ali");
		apt.assignRoom(0, "zain"); //throw exception
	}
	//count Availble Rooms
	@Test
	public void testCountAvailableRoomsInApartment() {
		Apartment apt = new Apartment("A"); //create aparment
		apt.assignRoom(0, "Ali");	//first assign 
		apt.assignRoom(1, "zain"); //second assign
		assertEquals(5, apt.countAvailableRooms());
	}
	
	//check if apartment is empty
	@Test
	public void testApartmentIsEmpty() {
		Apartment apt = new Apartment("A"); //create aparment
		assertTrue(apt.isEmpty());
		// New: assign a room to cover "return false" branch for coverage
		apt.assignRoom(0, "Ali");
		assertFalse(apt.isEmpty());
	}
	//check if apartment is full 
	@Test
	public void testApartmentIsFull() {
		Apartment apt = new Apartment("A");
		apt.assignRoom(0, "Ali");	//first assign 
		apt.assignRoom(1, "zain");
		assertFalse(apt.isFull()); // for coverage to execute return false
		apt.assignRoom(2, "musa");
		apt.assignRoom(3, "zaki");
		apt.assignRoom(4, "bilal");
		apt.assignRoom(5, "syed");
		apt.assignRoom(6, "Naji");
		
		assertTrue(apt.isFull());
	}


	
	
	
	//Test for Practicing Mocking 
	
	// Test  Verify method call (assignTenant)
    // This test checks that Apartment correctly delegates the call to Room.assignTenant()
    // We're not testing Room itself — only that Apartment calls the method.

	@Test
    public void testAssignRoom_CallsAssignTenantOnRoom() {
        // Arrange: create an Apartment and mock a Room
        Apartment apt = new Apartment("A");
        Room mockRoom = mock(Room.class);

        // Replace the real first room with our mock Room
        apt.getrooms().set(0, mockRoom);

        // Act: assign a tenant to the first room
        apt.assignRoom(0, "Ali");

        // Assert: verify that assignTenant("Ali") was called on the mock Room
        verify(mockRoom).assignTenant("Ali");
    }
	
	//  Mock behavior and test countAvailableRooms()
    // Here we control what isAvailable() returns and verify Apartment uses it.
    @Test
    public void testCountAvailableRooms_UsesIsAvailable() {
        // Arrange: create Apartment and mock two Room objects
        Apartment apt = new Apartment("A");
        Room mockRoom1 = mock(Room.class);
        Room mockRoom2 = mock(Room.class);

        // Define their behavior — one available, one not
        when(mockRoom1.isAvailable()).thenReturn(true);
        when(mockRoom2.isAvailable()).thenReturn(false);

        // Replace real rooms with our mocks
        apt.getrooms().set(0, mockRoom1);
        apt.getrooms().set(1, mockRoom2);

        // Act: count available rooms
        int count = apt.countAvailableRooms();

        // Assert: verify Apartment actually called isAvailable() on both
        verify(mockRoom1).isAvailable();
        verify(mockRoom2).isAvailable();

        // One unavailable mock ⇒ less than 7 rooms available
        assertTrue(count < 7);
    }
    
    
    
    // Test isEmpty() logic with mocks
    // We simulate a single occupied room and ensure isEmpty() returns false.
    @Test
    public void testIsEmpty_WithMockedRooms() {
        // Arrange: create Apartment and mock one Room
        Apartment apt = new Apartment("A");
        Room mockRoom = mock(Room.class);

        // Fake that this room is not available (occupied)
        when(mockRoom.isAvailable()).thenReturn(false);

        // Replace one real room with our mock
        apt.getrooms().set(0, mockRoom);

        // Act + Assert: Apartment should detect it's not empty
        assertFalse(apt.isEmpty());

        // Verify that Apartment checked room availability
        verify(mockRoom).isAvailable();
    }
}


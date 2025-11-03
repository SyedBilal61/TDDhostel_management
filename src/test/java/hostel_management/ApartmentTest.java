package hostel_management;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
	
}

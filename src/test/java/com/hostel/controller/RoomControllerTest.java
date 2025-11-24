package com.hostel.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hostel.repository.RoomRepository;
import com.hostel.view.RoomView;

import hostel_management.Room;

public class RoomControllerTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private RoomView roomView;

    @InjectMocks
    private RoomController roomController;

    private AutoCloseable closeable;

    @Before
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @After
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void testAllRooms() {
        List<Room> rooms = Arrays.asList(new Room("A1"));
        when(roomRepository.findAll()).thenReturn(rooms);

        roomController.allRooms();

        verify(roomView).showRoom(rooms.get(0));
    }

    @Test
    public void testAssignTenantSuccessfully() {
        Room room = new Room("B1");
        when(roomRepository.findByRoomNumber("B1")).thenReturn(room);

        roomController.assignTenant("B1", "Ali");

        InOrder inOrder = inOrder(roomRepository, roomView);
        inOrder.verify(roomRepository).save(room);
        inOrder.verify(roomView).tenantAssigned(room, "Ali");

        assertThat(room.getTenant()).isEqualTo("Ali");
    }

    @Test
    public void testAssignTenantToOccupiedRoomShowsError() {
        Room room = new Room("R1");
        room.assignTenant("Ali"); // occupy the room
        when(roomRepository.findByRoomNumber("R1")).thenReturn(room);

        roomController.assignTenant("R1", "Zain");

        verify(roomView).showError("Room already occupied", room);
    }

    @Test
    public void testVacateRoomSuccessfully() {
        Room room = new Room("R1");
        room.assignTenant("Ali");
        when(roomRepository.findByRoomNumber("R1")).thenReturn(room);

        roomController.vacateRoom("R1");

        InOrder inOrder = inOrder(roomRepository, roomView);
        inOrder.verify(roomRepository).save(room);
        inOrder.verify(roomView).roomVacated(room);

        assertThat(room.isAvailable()).isTrue();
    }

    @Test
    public void testAssignTenantToNonExistingRoom() {
        when(roomRepository.findByRoomNumber("A1")).thenReturn(null);

        roomController.assignTenant("A1", "Ali");

        verify(roomView).showError("Room not found", null);
    }

    @Test
    public void testVacateRoomAndRoomNotFound() {
        when(roomRepository.findByRoomNumber("A1")).thenReturn(null);

        roomController.vacateRoom("A1");

        verify(roomView).showError("Room not found", null);
    }

    // Test created to Improve the Code Coverage of the If-else section of
    // AutoCreateRooms

    @Test
    public void testAssignAutoCreateRoom() {
    	 //// Make repository return null so room is "not found"
    	when(roomRepository.findByRoomNumber("D1")).thenReturn(null);
    	
    	// Enable auto-create to enter the 'if(autoCreateRooms)' branch
    	
    	roomController.setAutoCreateRooms(true);
    	
    	
    	
    	// Call the method to trigger the lines
    	roomController.assignTenant("D1", "TestTenant");
    	
    	
    	
    }
}

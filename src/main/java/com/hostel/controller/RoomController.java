package com.hostel.controller;

import java.util.List;
import com.hostel.repository.RoomRepository;
import com.hostel.view.RoomView;
import hostel_management.Room;

public class RoomController {

    private RoomView roomView;
    private RoomRepository roomRepository;

    public RoomController(RoomView roomView, RoomRepository roomRepository) {
        this.roomView = roomView;
        this.roomRepository = roomRepository;
    }

    // Show all Rooms
    public void allRooms() {
        List<Room> rooms = roomRepository.findAll();
        rooms.forEach(roomView::showRoom);
    }

    // Assign a tenant to a room
    public void assignTenant(String roomNumber, String tenantName) {

    	
    	//try to find the room in the repository
    	Room room = roomRepository.findByRoomNumber(roomNumber);
    	
    	//if does not exists create this 

        if (room == null) {
            roomView.showError("Room not found", null);
            return;
        }

        try {
            room.assignTenant(tenantName);
            roomRepository.save(room);
            roomView.tenantAssigned(room, tenantName);
        } catch (IllegalStateException e) {
            roomView.showError(e.getMessage(), room);
        }
    }

    // Vacate a room
    public void vacateRoom(String roomNumber) {
        Room room = roomRepository.findByRoomNumber(roomNumber);

        if (room == null) {
            roomView.showError("Room not found", null);
            return;
        }

        room.vacate();
        roomRepository.save(room);
        roomView.roomVacated(room);
    }
}

package com.hostel.controller;
	
import java.util.List;

import com.hostel.view.RoomView;
import com.hostel.repository.RoomRepository;
import hostel_management.Room;
	
	public class RoomController {
	
	    private RoomView roomView;
	    private RoomRepository roomRepository;
	
	    public RoomController(RoomView Roomview, RoomRepository RoomRepository) {
	        this.Roomview = Roomview;
	        this.RoomRepository= RoomRepository;
	    }
	
	    // Show all Rooms
	    public void allRooms() {
	        List<Room> Rooms = RoomRepository.findAll();
	        RoomView.showAllRooms(Rooms);
	    }
	
	    // Assign a tenant to a room
	    public void assignTenant(String RoomId, int roomIndex, String tenantName) {
	        Room Room = RoomRepository.findById(RoomId);
	
	        if (Room == null) {
	            RoomView.showError("Room not found", null);
	            return;
	        }
	
	        try {
	            Room.assignRoom(roomIndex, tenantName);
	            RoomRepository.save(Room);
	            RoomView.tenantAssigned(Room, roomIndex, tenantName);
	        } catch (IllegalStateException e) {
	            RoomView.showError(e.getMessage(), Room);
	        }
	    }
	
	    // Vacate a room
	    public void vacateRoom(String RoomId, int roomIndex) {
	        Room Room = RoomRepository.findById(RoomId);
	
	        if (Room == null) {
	            RoomView.showError("Room not found", null);
	            return;
	        }
	
	        Room.vacateRoom(roomIndex);
	        RoomRepository.save(Room);
	        RoomView.roomVacated(Room, roomIndex);
	    }
	}
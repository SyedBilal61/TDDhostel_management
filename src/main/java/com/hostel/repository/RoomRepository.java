package com.hostel.repository;

import java.util.List;

import hostel_management.Room;

public interface RoomRepository {

	List<Room> findAll();
    
	Room findByRoomNumber(String roomNumber);
    
	void save(Room room);
  
    void vacate(String roomNumber);
    
  }

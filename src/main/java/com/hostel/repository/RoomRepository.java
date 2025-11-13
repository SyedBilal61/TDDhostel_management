package com.hostel.repository;

import java.util.List;

import hostel_management.Room;

public interface RoomRepository {

	List<Room> findAll();
    
	Room findById(String roomName);
    
	void save(Room room);
  
    void delete(String roomName); 
  }

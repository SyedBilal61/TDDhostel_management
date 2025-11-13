package com.hostel.repository;

import hostel_management.Room;

public interface RoomRepository {

	
	
  Return all rooms List<Room> findAll();
  
  Find an room by its name Room findById(String roomName);
  
  Save or update an room void save(room room);
  
  Delete an room by its name void delete(String roomName); 
  }

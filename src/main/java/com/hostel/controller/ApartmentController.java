//	package com.hostel.controller;
//	
//	import java.util.List;
//	
//	import com.hostel.repository.ApartmentRepository;
//	import com.hostel.view.ApartmentView;
//	
//	import hostel_management.Apartment;
//	
//	public class ApartmentController {
//	
//	    private ApartmentView hostelView;
//	    private ApartmentRepository apartmentRepository;
//	
//	    public ApartmentController(ApartmentView hostelView, ApartmentRepository apartmentRepository) {
//	        this.hostelView = hostelView;
//	        this.apartmentRepository = apartmentRepository;
//	    }
//	
//	    // Show all apartments
//	    public void allApartments() {
//	        List<Apartment> apartments = apartmentRepository.findAll();
//	        hostelView.showAllApartments(apartments);
//	    }
//	
//	    // Assign a tenant to a room
//	    public void assignTenant(String apartmentId, int roomIndex, String tenantName) {
//	        Apartment apartment = apartmentRepository.findById(apartmentId);
//	
//	        if (apartment == null) {
//	            hostelView.showError("Apartment not found", null);
//	            return;
//	        }
//	
//	        try {
//	            apartment.assignRoom(roomIndex, tenantName);
//	            apartmentRepository.save(apartment);
//	            hostelView.tenantAssigned(apartment, roomIndex, tenantName);
//	        } catch (IllegalStateException e) {
//	            hostelView.showError(e.getMessage(), apartment);
//	        }
//	    }
//	
//	    // Vacate a room
//	    public void vacateRoom(String apartmentId, int roomIndex) {
//	        Apartment apartment = apartmentRepository.findById(apartmentId);
//	
//	        if (apartment == null) {
//	            hostelView.showError("Apartment not found", null);
//	            return;
//	        }
//	
//	        apartment.vacateRoom(roomIndex);
//	        apartmentRepository.save(apartment);
//	        hostelView.roomVacated(apartment, roomIndex);
//	    }
//	}
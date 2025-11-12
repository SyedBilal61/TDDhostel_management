//package com.hostel.controller;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.inOrder;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.InOrder;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import com.hostel.repository.ApartmentRepository;
//import com.hostel.view.ApartmentView;
//
//import hostel_management.Apartment;
//public class ApartmentControllerTest {
//
//    @Mock
//    private ApartmentRepository apartmentRepository;
//
//    @Mock
//    private ApartmentView hostelView;
//
//    @InjectMocks
//    private ApartmentController apartmentController;
//
//    private AutoCloseable closeable;
//
//    @Before
//    public void setUp() {
//        closeable = MockitoAnnotations.openMocks(this);
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        closeable.close();
//    }
//
//    @Test
//    public void testAllApartments() {
//        List<Apartment> apartments = Arrays.asList(new Apartment("A"));
//        when(apartmentRepository.findAll()).thenReturn(apartments);
//
//        apartmentController.allApartments();
//
//        verify(hostelView).showAllApartments(apartments);
//    }
//
//    @Test
//    public void testAssignTenantSuccessfully() {
//        Apartment apartment = new Apartment("A");
//        when(apartmentRepository.findById("A")).thenReturn(apartment);
//
//        apartmentController.assignTenant("A", 0, "Ali");
//
//        InOrder inOrder = inOrder(apartmentRepository, hostelView);
//        inOrder.verify(apartmentRepository).save(apartment);
//        inOrder.verify(hostelView).tenantAssigned(apartment, 0, "Ali");
//
//        assertThat(apartment.getrooms().get(0).getTenant()).isEqualTo("Ali");
//    }
//
//    @Test
//    public void testAssignTenantToOccupiedRoomShowsError() {
//        Apartment apartment = new Apartment("A");
//        apartment.assignRoom(0, "Ali");
//        when(apartmentRepository.findById("A")).thenReturn(apartment);
//
//        apartmentController.assignTenant("A", 0, "Zain");
//
//        verify(hostelView).showError("Room already occupied", apartment);
//    }
//
//    @Test
//    public void testVacateRoomSuccessfully() {
//        Apartment apartment = new Apartment("A");
//        apartment.assignRoom(0, "Ali");
//        when(apartmentRepository.findById("A")).thenReturn(apartment);
//
//        apartmentController.vacateRoom("A", 0);
//
//        InOrder inOrder = inOrder(apartmentRepository, hostelView);
//        inOrder.verify(apartmentRepository).save(apartment);
//        inOrder.verify(hostelView).roomVacated(apartment, 0);
//
//        assertThat(apartment.getrooms().get(0).isAvailable()).isTrue();
//    }
//
//    @Test
//    public void testAssignTenantToNonExistingApartment() {
//        when(apartmentRepository.findById("X")).thenReturn(null);
//
//        apartmentController.assignTenant("X", 0, "Ali");
//
//        verify(hostelView).showError("Apartment not found", null);
//    }
//    //Test Added for improve the coverage 
//    @Test
//    public void testVacateRoomAndApartmentNotFound() {
//    	
//       when(apartmentRepository.findById("X")).thenReturn(null);
//       apartmentController.vacateRoom("X", 0);
//       
//       verify(hostelView).showError("Apartment not found",null);
//    }
//    	
//    
//
//}
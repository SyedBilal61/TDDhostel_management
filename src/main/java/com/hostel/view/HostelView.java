package com.hostel.view;

import java.util.List;
import hostel_management.Apartment;

public interface HostelView {

    // Show all apartments with their rooms and tenants
    void showAllApartments(List<Apartment> apartments);

    // Show error message for an apartment operation
    void showError(String message, Apartment apartment);

    // Notify that a tenant was assigned successfully
    void tenantAssigned(Apartment apartment, int roomIndex, String tenantName);

    // Notify that a room was vacated successfully
    void roomVacated(Apartment apartment, int roomIndex);
}

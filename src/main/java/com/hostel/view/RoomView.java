
package com.hostel.view;

import hostel_management.Room;

public interface RoomView {

    // Show details of a single room
    void showRoom(Room room);

    // Show error message for a room operation
    void showError(String message, Room room);

    // Notify that a tenant was assigned successfully
    void tenantAssigned(Room room, String tenantName);

    // Notify that the room was vacated successfully
    void roomVacated(Room room);
}



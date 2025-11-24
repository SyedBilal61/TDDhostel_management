package com.hostel.view.swing;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hostel.controller.RoomController;

import hostel_management.Room;

//This is the  Unit tests for the UI frame’s logic by mocking the Control, it will helpful in integration test also 

public class RoomSwingViewControllerTest {

    private RoomSwingView roomSwingView;
    private FrameFixture window;
    private RoomController roomController;

    @Before
    public void setup() {
        roomController = mock(RoomController.class);
        roomSwingView = GuiActionRunner.execute(() -> new RoomSwingView());
        roomSwingView.setRoomController(roomController);

        window = new FrameFixture(roomSwingView);
        window.show();
    }

    // For Cleanup earliar test messup bcz if not then then the UI frame freezed
    @After
    public void teardown() {
        window.cleanUp();
    }

    @Test
    public void testAddButtonCallsAssignTenant() {

        window.textBox("roomIdTextBox").enterText("A1");
        window.textBox("nameTextBox").enterText("Zain");
        window.button("addButton").click();

        verify(roomController).assignTenant("A1", "Zain");

    }

    @Test
    public void testDeleteButtonCallsVacateRoom() {

        Room room = new Room("A1");
        room.assignTenant("Zain");
        GuiActionRunner.execute(() -> roomSwingView.showRoom(room));

        window.list("roomList").selectItem(0);
        window.button("deleteButton").click();

        verify(roomController).vacateRoom("A1");

    }

}

package com.hostel.view.swing;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import javax.swing.DefaultListModel;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

import hostel_management.Room;


	
@RunWith(GUITestRunner.class)
public class RoomSwingViewTest extends AssertJSwingJUnitTestCase {

    private FrameFixture window;
    private RoomSwingView roomSwingView;
    
    
    /**
     * @wbp.parser.entryPoint
     */
    @Override
    public void onSetUp() {
    	GuiActionRunner.execute(() -> {
    		roomSwingView = new RoomSwingView();
    		return roomSwingView;	
    		
    	});
    	window = new FrameFixture(robot(), roomSwingView);
    	window.show();   //show the frame to test 
    	
    }
    
    @Test @GUITest
    public void testInitialSetup ( ) {
        window.label(JLabelMatcher.withText("RoomId"));
        window.textBox("roomIdTextBox").requireEnabled();
        window.label(JLabelMatcher.withText("Tenant Name"));
        window.textBox("nameTextBox").requireEnabled();
        window.button(JButtonMatcher.withText("Add")).requireDisabled();
        window.list("roomList");
        window.button(JButtonMatcher.withText("Delete Selected")).requireDisabled();
        window.label("errorMessageLabel").requireText(" ");
        
    	
    }
    
    @Test 
    public void testWhenRoomIdAndTenantNameAreNotEmptyThenAddButtonShouldBeEnabled() {
    	
        window.textBox("roomIdTextBox").enterText("A1");
        window.textBox("nameTextBox").enterText("Zain");
        window.button(JButtonMatcher.withText("Add")).requireEnabled();
    	
    	
    }
     
    @Test
    public void testWhenEitherIdOrNameContainsOnlySpacesThenAddButtonShouldBeDisabled() {
        JTextComponentFixture idTextBox = window.textBox("roomIdTextBox");
        JTextComponentFixture nameTextBox = window.textBox("nameTextBox");

        // Case 1: Roomid filled, name is spaces
        idTextBox.enterText("A1");
        nameTextBox.enterText("   "); // three spaces
        window.button(JButtonMatcher.withText("Add")).requireDisabled();

        // Reset text fields
        idTextBox.setText("");
        nameTextBox.setText("");

        // Case 2: ROOM id is spaces/blank, name filled
        idTextBox.enterText("   "); // spaces
        nameTextBox.enterText("ZAIN");
        window.button(JButtonMatcher.withText("Add")).requireDisabled();

        // Case 3: both fields are spaces
        idTextBox.setText("  ");
        nameTextBox.setText(" ");
        window.button(JButtonMatcher.withText("Add")).requireDisabled();
    }


    //check when the Delete Selected button only enabled after a room is selected 
    
    
    @Test
    public void testTheButtonDeleteShouldBeEnabledOnlyAfterSelectionARoomIsSelectedFromList() {
    	
    	Room room = new Room("A1");
    	room.assignTenant("Zain");
    	
    	
    	
    	GuiActionRunner.execute(() ->
    	   roomSwingView.getRoomListModel().addElement(room)
    	   
    	   );
    	//select a room
    	
    	window.list("roomList").selectItem(0);
    	
    	
    	
    	//get delete button fixutre 
    	
    	JButtonFixture deleteButton = window.button(JButtonMatcher.withText("Delete Selected"));
    	
    	
    	//verify enable
    	deleteButton.requireEnabled();
    	
    	
    	window.list("roomList").clearSelection();
    	
    	
    	//verfiy disabled
    	deleteButton.requireDisabled();
    	
    }
    //Interface Implementation Tests 
    
   
    @Test
    public void testShowAllRoomsShouldAddRoomDescriptionsToTheList() {
    	
    	
    	Room room1 = new Room ("A1");
    	Room room2 = new Room ("A2");
    	
    	GuiActionRunner.execute(() -> 
    	   roomSwingView.showAllRooms(Arrays.asList(room1 , room2))
    	   
    	   );
    	
    	String[] listContents = window.list("roomList").contents();
    	
    	assertThat(listContents)
    	    .containsExactly(room1.toString(),room2.toString());
    			
    	
    }
    	
    
    
    @Test
    public void testErrorLabelShouldShowTheMessage() {
    	
    	Room room1 = new Room ("A1");
    	GuiActionRunner.execute(
    			() -> roomSwingView.showError("Error Occured", room1)
    	);
    	
    	window.label("errorMessageLabel")
    	    .requireText("Error Occured: " + room1);
    	
    	
    	
    }
    
    @Test
    public void testRoomAddedShouldAddRoomToTheListAndResetErrorLabel() {
    	
    	Room room = new Room ("A1");
    	
    	GuiActionRunner.execute(
    			() -> roomSwingView.showRoom(room)
    	);
    	
    	String[] listContents = window.list("roomList").contents();
    	assertThat(listContents).containsExactly(room.toString());
    	
    	window.label("errorMessageLabel").requireText(" "); //error reset
    	
    	
    	
    	
    }
    
    
    @Test
    public void testTenantAssignedShouldUpdateListAndResetErrorLabel() {
    	
    	Room room = new Room ("A2");
    	
    	GuiActionRunner.execute(
    	    	() -> roomSwingView.tenantAssigned(room , "Ali")
    	    	);
    	
    	
    	String[] listContents = window.list("roomList").contents();
    	assertThat(listContents).containsExactly(room.toString());
    	
    	window.label("errorMessageLabel").requireText(" "); //error reset
    	
    	
    }
    	
    @Test
    public void testRoomVacatedShouldUpdateListAndResetErrorLabel() {
    	
        Room room1 = new Room ("A2");
        Room room2 = new Room ("A3");
        
     // Add rooms to the list model in the EDT
  	
    	GuiActionRunner.execute(() -> 
    	    	
    	    	{
    	    	DefaultListModel<Room> model = roomSwingView.getRoomListModel();
    	    	model.addElement(room1);
    	    	model.addElement(room2);
    	    
    	    	});
    	    	// Execute: vacate room1 (removes it from the list)
    	    	GuiActionRunner.execute(
    	    	    	() -> roomSwingView.roomVacated(new Room ("A2")));
    	  	
    	   
    	    	// Verify: only room2 remains in the list                   
    	
    	String[] listContents = window.list("roomList").contents();
    	assertThat(listContents).containsExactly(room2.toString());
    	
    	window.label("errorMessageLabel").requireText(" "); //error reset
    	
    	
    	
    	
    	
    }
    }

    	
    

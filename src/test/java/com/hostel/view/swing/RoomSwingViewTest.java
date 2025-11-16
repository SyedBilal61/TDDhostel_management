package com.hostel.view.swing;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;


	
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


}

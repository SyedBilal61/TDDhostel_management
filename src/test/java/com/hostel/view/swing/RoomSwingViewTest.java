package com.hostel.view.swing;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;


	
@RunWith(GUITestRunner.class)
public class RoomSwingViewTest extends AssertJSwingJUnitTestCase {

    private FrameFixture window;
    private RoomSwingView roomSwingView;
    
    
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
}

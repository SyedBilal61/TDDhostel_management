package com.hostel.view.swing;

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
    protected void onSetUp() {
    roomSwingView = GuiActionRunner.execute(() -> new RoomSwingView());
    window = new FrameFixture(robot(), roomSwingView);
    window.show();  //show the frame to test 
	
	}
    
    
    
    @Test
    public void testSetUp() {
    	
    	
    	
    }



}

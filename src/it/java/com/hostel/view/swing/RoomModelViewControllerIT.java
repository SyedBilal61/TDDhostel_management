package com.hostel.view.swing;

import org.assertj.swing.junit.runner.GUITestRunner;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.testcontainers.containers.MongoDBContainer;

@RunWith(GUITestRunner.class)
public class RoomModelViewControllerIT {

	
	
 	
	//Start a MongoDb Container for testing in testcontainers 
	@ClassRule
	
	public static final MongoDBContainer mongo = new MongoDBContainer("mongo:4.4.3");
	
	
	
	
	
	
	
	
}

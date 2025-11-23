package com.hostel.app.swing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.launcher.ApplicationLauncher.application;
import static org.mockito.ArgumentMatchers.contains;

import java.util.regex.Pattern;

import javax.swing.JFrame;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.bson.Document;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testcontainers.containers.MongoDBContainer;

import com.mongodb.MongoClient;
import com.mongodb.client.model.Filters;

@RunWith(GUITestRunner.class)
public class HotelSwingAppE2E extends AssertJSwingJUnitTestCase {

	@ClassRule
	public static final MongoDBContainer mongo = new MongoDBContainer("mongo:4.4.3");
	
	
	private static final String DB_NAME = "test-db";
	
	private static final String  COLLECTION_NAME = "test-collection";
	
	private MongoClient mongoClient;
	
	private FrameFixture window;
	
	
	
	private static final String ROOM_FIXTURE_1_NUMBER = "A1";
	private static final String ROOM_FIXTURE_1_TENANT = "Ali";

	private static final String ROOM_FIXTURE_2_NUMBER = "A2";
	private static final String ROOM_FIXTURE_2_TENANT = "Zain";

	
	
	
	
	
	@Override 
	protected void onSetUp() {
		String containerIpAddress = mongo.getContainerIpAddress();
		Integer mappedPort = mongo.getFirstMappedPort();
		
		mongoClient = new MongoClient(containerIpAddress, mappedPort);
		
		
		//always start with an empty database 
		mongoClient.getDatabase(DB_NAME).drop();
		
		
		//add some test rooms to the Database
		addTestRoomToDatabase(ROOM_FIXTURE_1_NUMBER, ROOM_FIXTURE_1_TENANT);
		addTestRoomToDatabase(ROOM_FIXTURE_2_NUMBER, ROOM_FIXTURE_2_TENANT);

		
		
		//Start the Swing Application
		
		application("com.hostel.app.swing.HostelSwingApp")
		    .withArgs(
		    	 "--mongo-host=" + containerIpAddress,
		         "--mongo-port=" + mappedPort.toString(),
		         "--db-name=" + DB_NAME,
		         "--db-collection=" + COLLECTION_NAME
	   )
		 .start();

		// get a reference to its JFrame
        window = org.assertj.swing.finder.WindowFinder.findFrame(new GenericTypeMatcher<JFrame>(JFrame.class) {
            @Override
            protected boolean isMatching(JFrame frame) {
                return "HostelView".equals(frame.getTitle()) && frame.isShowing();
            }
        }).using(robot());
    }

    @Override
    protected void onTearDown() {
        mongoClient.close();
    }

    private void addTestRoomToDatabase(String roomNumber, String tenant) {
        mongoClient
            .getDatabase(DB_NAME)
            .getCollection(COLLECTION_NAME)
            .insertOne(new Document()
            		.append("roomNumber", roomNumber)
            		.append("tenant", tenant));
    }
    

    

    
    
    
    
    
    //Verfies when your app starts the rooms from Databacse are correctly shown in GUI
    
    @Test
    @GUITest
    public void testOnStartAllDatabaseElementsAreShown() {
        assertThat(window.list().contents())
            .anySatisfy(e -> assertThat(e)
                .contains(ROOM_FIXTURE_1_NUMBER, ROOM_FIXTURE_1_TENANT))
            .anySatisfy(e -> assertThat(e)
                .contains(ROOM_FIXTURE_2_NUMBER, ROOM_FIXTURE_2_TENANT));
    }

    
    //Test adding new room successfully added 
 
    
    
    
    
    @Test
    @GUITest
    public void testAddNewRoomSuccess() {

        // Step 1: Pre-create the room in the database (tenant is null initially)
        addTestRoomToDatabase("C1", null);

        // Step 2: Enter the tenant info in the UI
        window.textBox("roomIdTextBox").enterText("C1");
        window.textBox("nameTextBox").enterText("Alice");

        // Step 3: Click the Add button
        window.button(JButtonMatcher.withText("Add")).click();

        // Step 4: Verify the tenant appears in the list
        assertThat(window.list().contents())
            .anySatisfy(e -> assertThat(e).contains("C1", "Alice"));
    }

    
    
    
    
    
    
    
    //Test for the button of Add not functioning  
    
    @Test
    @GUITest
    public void testErrorShowingOnAddButton () {
    	
    	//enter a duplicate room info
    	window.textBox("roomIdTextBox").enterText(ROOM_FIXTURE_1_NUMBER);
    	window.textBox("nameTextBox").enterText("Someone Else");
    	
    	
    	
    	//click add button
    	window.button(JButtonMatcher.withText("Add")).click();
    	
    	
    	
    	//verify the Error Message Shown
    	assertThat(window.label("errorMessageLabel").text())
    	     .contains(ROOM_FIXTURE_1_NUMBER, ROOM_FIXTURE_1_TENANT);
    }
    
    
    
    
   //on Delete Button 
    
    
    @GUITest
    @Test
    
    public void testFunctionalityOfDeleteButton() {
    	
    	//select the room from list by matching tenant Name
    	
    	window.list("roomList")
    	      .selectItem(Pattern.compile(".*" + ROOM_FIXTURE_1_NUMBER + ".*"));
    	//press delete button
    	window.button(JButtonMatcher.withText("Delete Selected")).click();
    	
    	
    	//verify 
    	
    	assertThat(window.list().contents())
    			.noneMatch(e -> e.contains(ROOM_FIXTURE_1_NUMBER));
    
    }
    
    
    
    @GUITest
    @Test
    
    public void testDeleteRoomErrorShowMessage() {
    	
    	//Select room in list
    	window.list("roomList")
    	  .selectItem(Pattern.compile(".*" + ROOM_FIXTURE_1_NUMBER + ".*"));
    	
    	  
    	  
    	 //Maunually remove the room from the database
    	  
    	removeTestRoomFromDatabase(ROOM_FIXTURE_1_NUMBER);
    	
    	
    	//press delete button 
    	
    	window.button(JButtonMatcher.withText("Delete Selected")).click();
    	
    	
    	
    	//verify that error message is shown
    	assertThat(window.label("errorMessageLabel").text())
    	    .contains("Room not found: null");
    	       	  
    	  
    }
    
    private void removeTestRoomFromDatabase(String roomNumber) {
    	mongoClient
    	  .getDatabase(DB_NAME)
    	  .getCollection(COLLECTION_NAME)
    	  .deleteOne(Filters.eq("roomNumber", roomNumber));
    }
    
    	
    	
    	
    	
    
    
    	
    	
    	
    	
    }    		
		    		
		    		
		    		
		    		
		    		
		
		
		
		
		
		
	
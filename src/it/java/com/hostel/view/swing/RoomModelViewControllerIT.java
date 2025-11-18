package com.hostel.view.swing;


import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testcontainers.containers.MongoDBContainer;

import com.hostel.controller.RoomController;
import com.hostel.repository.mongo.RoomMongoRepository;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import hostel_management.Room;





@RunWith(GUITestRunner.class)
public class RoomModelViewControllerIT extends AssertJSwingJUnitTestCase {

	
	
 	
	//Start a MongoDb Container for testing in testcontainers 
	@ClassRule
	
	public static final MongoDBContainer mongo = new MongoDBContainer("mongo:4.4.3");
	
	private MongoClient mongoClient;
	private RoomMongoRepository roomRepository;
	
	private RoomController roomController;
	private FrameFixture window;
	
	
	@Override
	protected void onSetUp() {
		
		//Connect to MongoDBContainer 
		mongoClient = new MongoClient (
				new ServerAddress(
						mongo.getHost(),
						mongo.getFirstMappedPort()));
						
		//Intialize Repository
		roomRepository = new RoomMongoRepository(mongoClient);
		
		
		
		
		//clean dataBase for each test 
		
		for (Room r : roomRepository.findAll()) {
			roomRepository.delete(r.getRoomNumber());
		}
		
		
		//pre-insert/Create rooms first needed for the tests 
		roomRepository.save(new Room("A1"));
		roomRepository.save(new Room ("B2"));
		
		
		
		
		//Create a Swing UI
		
		window = new FrameFixture(robot(),
				GuiActionRunner.execute(() -> {
					RoomSwingView view = new RoomSwingView();
					roomController = new RoomController(view, roomRepository);
					view.setRoomController(roomController);
					return view;
					
				})
					
				);	
				
       window.show();	
	}
	
	
	
	//close the database after using 
	
	@Override 
	protected void onTearDown() {
		mongoClient.close();
	}
				
	
	
	
	
	//Tests 
	@GUITest
	@Test
	public void testAssignTenantShouldUpdateDataBase() {
		//use of the UI frame to add a tenant 
		
		window.textBox("roomIdTextBox").enterText("A1");
		window.textBox("nameTextBox").enterText("zain");
		window.button(JButtonMatcher.withText("Add")).click();
		
		
		//verify that it has been added to database 
		Room r = roomRepository.findByRoomNumber("A1");
		assertThat(r).isNotNull();
		assertThat(r.getTenant()).isEqualTo("zain");
				
	}
	
	
	@GUITest
	@Test
	public void testVacateRoom () {
		
		//add a room for test 
		Room existing = new Room("A2");
		existing.assignTenant("ali");
		
		roomRepository.save(existing);
		
		
		GuiActionRunner.execute(() -> roomController.allRooms());
		
		//select the exist room
		
		window.list("roomList").selectItem(0);
		
		
		
		//click the button 
		
		window.button(JButtonMatcher.withText("Delete Selected")).click();
		
		
		
		//verify that room has been updated in the database
		
		
		Room r = roomRepository.findByRoomNumber("A2");
		assertThat(r).isNotNull();
		assertThat(r.getTenant()).isNull();
		assertThat(r.isAvailable()).isTrue();
		
        		
	}
			
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

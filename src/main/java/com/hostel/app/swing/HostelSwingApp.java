package com.hostel.app.swing;

import java.awt.EventQueue;

import com.hostel.controller.RoomController;
import com.hostel.repository.mongo.RoomMongoRepository;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import com.hostel.view.swing.RoomSwingView;


 /* This sets up the MongoDB connection, repository, controller, and view.
 */



public class HostelSwingApp {

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            try {
                // Default MongoDB host and port
                String mongoHost = "localhost";
                int mongoPort = 27017;

                // Override host and port if provided as command-line arguments
                if (args.length > 0) {
                    mongoHost = args[0];
                }
                if (args.length > 1) {
                    mongoPort = Integer.parseInt(args[1]);
                }

                // Create Mongo client
                MongoClient mongoClient = new MongoClient(new ServerAddress(mongoHost, mongoPort));

                // Create repository with database and collection names
                RoomMongoRepository roomRepository = new RoomMongoRepository(
                        mongoClient, "hosteldb", "rooms");

                // Create the Swing view
                RoomSwingView roomView = new RoomSwingView();

                // Create controller and wire it with view and repository
                RoomController roomController = new RoomController(roomView, roomRepository);

                // Set controller in the view
                roomView.setRoomController(roomController);

                // Make the view visible
                roomView.setVisible(true);

                // Load all rooms initially
                roomController.allRooms();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

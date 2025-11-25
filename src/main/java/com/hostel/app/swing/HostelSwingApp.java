package com.hostel.app.swing;

import java.awt.EventQueue;
import java.util.concurrent.Callable;

import com.hostel.controller.RoomController;
import com.hostel.repository.mongo.RoomMongoRepository;
import com.hostel.view.swing.RoomSwingView;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(mixinStandardHelpOptions = true)
public class HostelSwingApp implements Callable<Void> {
    
    // --- Command-line options for MongoDB configuration ---

    @Option(names = { "--mongo-host" }, description = "MongoDB host address")
    private String mongoHost = "localhost";

    @Option(names = { "--mongo-port" }, description = "MongoDB host port")
    private int mongoPort = 27017;

    @Option(names = { "--db-name" }, description = "Database name")
    private String databaseName = "hostel";

    @Option(names = { "--db-collection" }, description = "Collection name")
    private String collectionName = "rooms";

    //Entry Point of the Application
    
    
    public static void main(String[] args) {
        new CommandLine(new HostelSwingApp()).execute(args);
    }

    @Override
    public Void call() throws Exception {
        EventQueue.invokeLater(() -> {
            try {
                MongoClient client = new MongoClient(new ServerAddress(mongoHost, mongoPort));

                RoomMongoRepository repository = new RoomMongoRepository(client, databaseName, collectionName);

                RoomSwingView view = new RoomSwingView();
                RoomController controller = new RoomController(view, repository);
                controller.setAutoCreateRooms(true); // for UI frame,rooms automatically when assigning a tenant
                                                     // without pre-existing room in the database. Needed for GUI/E2E to
                                                     // work.

                view.setRoomController(controller);
                view.setVisible(true);
                controller.allRooms();

            } catch (Exception e) {
                /* Print stack trace for any initialization errors */
                e.printStackTrace();
            }
        });
        return null;
    }
}

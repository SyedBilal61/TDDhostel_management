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

    @Option(names = {"--mongo-host"}, description = "MongoDB host address")
    private String mongoHost = "localhost";

    @Option(names = {"--mongo-port"}, description = "MongoDB host port")
    private int mongoPort = 27017;

    @Option(names = {"--db-name"}, description = "Database name")
    private String databaseName = "hostel";

    @Option(names = {"--db-collection"}, description = "Collection name")
    private String collectionName = "rooms";

    public static void main(String[] args) {
        new CommandLine(new HostelSwingApp()).execute(args);
    }

    @Override
    public Void call() throws Exception {
        EventQueue.invokeLater(() -> {
            try {
                MongoClient client =
                        new MongoClient(new ServerAddress(mongoHost, mongoPort));

                RoomMongoRepository repository =
                        new RoomMongoRepository(client, databaseName, collectionName);

                RoomSwingView view = new RoomSwingView();
                RoomController controller =
                        new RoomController(view, repository);

                view.setRoomController(controller);
                view.setVisible(true);
                controller.allRooms();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return null;
    }
}

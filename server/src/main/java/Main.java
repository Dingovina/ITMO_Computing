import Models.Server;
import Models.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        int port = -1;
        Scanner input = new Scanner(System.in);
        ConnectionManager connectionManager = null;
        DumpManager dumpManager = null;
        CollectionManager collectionManager = null;
        try{
            port = Integer.parseInt(args[0]);
        } catch (Exception e){
            System.out.println("Invalid argument.");
            System.out.println("You must specify server port as an argument.");
            System.exit(0);
        }
        try {
            Properties info = new Properties();
            info.load(new FileInputStream("db.cfg"));
            connectionManager = new ConnectionManager("jdbc:postgresql://localhost:5432/studs", info);
            connectionManager.create_connection();
            dumpManager = new DumpManager(connectionManager.getConnection());
            collectionManager = new CollectionManager(dumpManager);
        } catch (Exception e){
            System.out.println("Error while starting the server.");
            System.out.println(e.getMessage());
            System.exit(0);
        }

        System.out.println("Ready to run!");
        Server server = new Server(port, dumpManager, collectionManager, input);
        server.run();
    }
}

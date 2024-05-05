import Models.Server;
import Models.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        int port = -1;
        Scanner input = new Scanner(System.in);
        DumpManager dumpManager = null;
        CollectionManager collectionManager = null;
        try{
            port = Integer.parseInt(args[0]);
            dumpManager = new DumpManager(args[1], input);
            collectionManager = new CollectionManager(dumpManager);
        } catch (Exception e){
            System.out.println("Invalid argument.");
            System.out.println("You must specify server port and data filename as arguments.");
            System.exit(0);
        }

        System.out.println("Ready to run!");
        Server server = new Server(port, dumpManager, collectionManager, input);
        server.run();
    }
}

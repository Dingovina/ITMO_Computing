package Models;

import SharedModels.MusicBand;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.PriorityQueue;
import java.util.Scanner;

public class DumpManager {

private final Connection connection;

    public DumpManager(Connection c){
        connection = c;
    }

    public PriorityQueue<MusicBand> readCollection(){
        PriorityQueue<MusicBand> collection = new PriorityQueue<>();
        try {
            Statement st = connection.createStatement();
            ResultSet data = st.executeQuery("SELECT * FROM MusicBand;");
            while (data.next()) {
                StringBuilder str_band = new StringBuilder();
                for (int i = 1; i < 18; ++i){
                    str_band.append(data.getString(i)).append("\n");
                }
                Scanner in = new Scanner(str_band.toString());
                MusicBand band = new MusicBand();
                if (band.load_data(in)){
                    collection.add(band);
                }
                else{
                    System.out.println("Error while loading MusicBand.");
                }
            }

            data.close();
            st.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error while reading the database");
            System.exit(0);
        }
        return collection;
    }

    public Connection getConnection(){
        return connection;
    }
}

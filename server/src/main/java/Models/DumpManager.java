package Models;

import SharedModels.MusicBand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;

public class DumpManager {

private Connection connection;

    public DumpManager(Connection c){
        connection = c;
    }

    public PriorityQueue<MusicBand> readCollection(){
        PriorityQueue<MusicBand> collection = new PriorityQueue<>();
        try {
            Statement st = connection.createStatement();
            ResultSet data = st.executeQuery("SELECT * FROM MusicBand;");
            while (data.next()) {
                String str_band = "";
                for (int i = 1; i < 18; ++i){
                    str_band += data.getString(i) + "\n";
                }
                Scanner in = new Scanner(str_band);
                MusicBand band = new MusicBand();
                if (!band.load_data(in)){
                    System.out.println("Error while loading MusicBand.");
                }
                else{
                    collection.add(band);
                }
            }

            data.close();
            st.close();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Error while reading the database");
            System.exit(0);
        }
        return collection;
    }

    public Connection getConnection(){
        return connection;
    }
}

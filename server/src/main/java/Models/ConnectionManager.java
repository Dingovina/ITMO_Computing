package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionManager {
    private String table;
    private Properties properties;
    private Connection connection;

    public ConnectionManager(String table, Properties properties){
        this.table = table;
        this.properties = properties;
    }

    public boolean create_connection(){
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(table, properties);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    public Connection getConnection(){
        return connection;
    }
}

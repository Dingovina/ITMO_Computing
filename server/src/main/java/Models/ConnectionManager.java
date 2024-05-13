package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private final String table;
    private final Properties properties;
    private Connection connection;

    public ConnectionManager(String table, Properties properties){
        this.table = table;
        this.properties = properties;
    }

    public void create_connection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(table, properties);
    }

    public Connection getConnection(){
        return connection;
    }
}

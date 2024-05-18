package Commands;

import SharedModels.Response;
import SharedUtility.ResponseStatus;
import Utility.Command;
import SharedUtility.CommandType;
import Utility.OwnerCommand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Clear extends OwnerCommand {

    private final Connection connection;
    public Clear(Connection connection) {
        super("clear", CommandType.CLEAR);
        this.connection = connection;
    }

    @Override
    public Response execute(ArrayList<Object> args, String username) {
        try {
            PreparedStatement st = connection.prepareStatement("""
                    DELETE FROM MusicBand
                    WHERE creator = ?;
                    """);
            st.setString(1, username);
            st.execute();
        } catch (Exception e){
            return new Response(ResponseStatus.ERROR, e.getMessage());
        }

        return new Response(ResponseStatus.OK, "Коллекция отчищена.");
    }
}

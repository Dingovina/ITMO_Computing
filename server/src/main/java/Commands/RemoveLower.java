package Commands;

import Models.CollectionManager;
import SharedModels.MusicBand;
import SharedModels.Response;
import SharedUtility.ResponseStatus;
import Utility.Command;
import SharedUtility.CommandType;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

public class RemoveLower extends Command {
    private Connection connection;
    public RemoveLower(Connection connection) {
        super("remove_lower", CommandType.REMOVE_LOWER);
        this.connection = connection;
    }

    @Override
    public Response execute(ArrayList<Object> args) {
        try {
            Statement st = connection.createStatement();
            st.execute("DELETE FROM MusicBand;");
        } catch (Exception e){
            return new Response(ResponseStatus.ERROR, e.getMessage());
        }

        return new Response(ResponseStatus.OK, "Меньшие группы удалены.");
    }
}

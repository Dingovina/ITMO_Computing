package Commands;

import SharedModels.Response;
import SharedUtility.ResponseStatus;
import SharedUtility.CommandType;
import Utility.OwnerCommand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class RemoveLower extends OwnerCommand {
    private final Connection connection;
    public RemoveLower(Connection connection) {
        super("remove_lower", CommandType.REMOVE_LOWER);
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

        return new Response(ResponseStatus.OK, "Меньшие группы удалены.");
    }
}

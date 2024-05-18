package Commands;

import SharedModels.Response;
import SharedUtility.ResponseStatus;
import Utility.Command;
import SharedUtility.CommandType;
import Utility.OwnerCommand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RemoveById extends OwnerCommand {
    private final Connection connection;
    public RemoveById(Connection connection) {
        super("remove_by_id", CommandType.REMOVE_BY_ID);
        this.connection = connection;
    }

    @Override
    public Response execute(ArrayList<Object> args, String username) {

        try {
            long id = (long) args.get(0);
            PreparedStatement st = connection.prepareStatement(
                    """
                            DELETE FROM MusicBand
                            WHERE id = ? AND  creator = ?
                            RETURNING id;
                        """);
            st.setLong(1, id);
            st.setString(2, username);
            ResultSet rs = st.executeQuery();
            if (rs.next() && rs.getLong(1) == id){
                return new Response(ResponseStatus.OK, "Группа была удалена.");
            }
        } catch (SQLException e){
            return new Response(ResponseStatus.ERROR, e.getMessage());
        }

        return new Response(ResponseStatus.ERROR, "Ошибка при выполнении запроса.");
    }
}

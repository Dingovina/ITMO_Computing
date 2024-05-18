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

public class RemoveFirst extends OwnerCommand {
    private final Connection connection;
    public RemoveFirst(Connection connection) {
        super("remove_first", CommandType.REMOVE_FIRST);
        this.connection = connection;
    }

    @Override
    public Response execute(ArrayList<Object> args, String username) {
        try {
            PreparedStatement st = connection.prepareStatement("""
                            DELETE FROM MusicBand
                            WHERE id = (SELECT MIN(id) FROM MusicBand)
                            AND creator = ?
                            RETURNING id;
                            """);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next() && rs.getLong(1) > 0){
                return new Response(ResponseStatus.OK, "Группа удалена.");
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new Response(ResponseStatus.ERROR, e.getMessage());
        }
        return new Response(ResponseStatus.ERROR, "Ошибка при выполнении запроса.");
    }
}

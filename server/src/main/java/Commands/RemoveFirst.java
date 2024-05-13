package Commands;

import Models.CollectionManager;
import SharedModels.Response;
import SharedUtility.ResponseStatus;
import Utility.Command;
import SharedUtility.CommandType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class RemoveFirst extends Command {
    private Connection connection;
    public RemoveFirst(Connection connection) {
        super("remove_first", CommandType.REMOVE_FIRST);
        this.connection = connection;
    }

    @Override
    public Response execute(ArrayList<Object> args) {
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(
                    "DELETE FROM MusicBand\n" +
                    "WHERE id = (SELECT MIN(id) FROM MusicBand)\n" +
                    "RETURNING id;");
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

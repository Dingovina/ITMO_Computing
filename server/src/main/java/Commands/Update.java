package Commands;

import SharedModels.MusicBand;
import SharedModels.Response;
import SharedUtility.ResponseStatus;
import Utility.Command;
import SharedUtility.CommandType;
import Utility.OwnerCommand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Update extends OwnerCommand {
    private final Connection connection;
    public Update(Connection connection) {
        super("update", CommandType.UPDATE);
        this.connection = connection;
    }

    @Override
    public Response execute(ArrayList<Object> args, String username) {
        try {
            long id = (Long) args.get(0);
            MusicBand band = (MusicBand) args.get(1);
            PreparedStatement st = connection.prepareStatement(
                    """
                            UPDATE MusicBand SET name = ?,     coordinate_x = ?,    coordinate_y = ?,    creationDate = ?,    numberOfParticipants = ?,    albumsCount = ?,    description = ?,    genre = ?,    frontMan_name = ?,    frontMan_weight = ?,    frontMan_eyeColor = ?,    frontMan_hairColor = ?,    frontMan_nationality = ?,    frontMan_location_x = ?,    frontMan_location_y = ?,    frontMan_location_name = ?
                            WHERE id = ?
                            AND creator = ?
                            RETURNING id;"""
            );
            st.setString(1, band.getName());
            st.setDouble(2, band.getCoordinates().getX());
            st.setDouble(3, band.getCoordinates().getY());
            st.setTimestamp(4, Timestamp.valueOf(band.getCreationDate().toLocalDateTime()));
            st.setInt(5, band.getNumberOfParticipants());
            st.setLong(6, band.getAlbumsCount());
            st.setString(7, band.getDescription());
            st.setString(8, band.getGenre().name());
            st.setString(9, band.getFrontMan().getName());
            st.setDouble(10, band.getFrontMan().getWeight());
            st.setString(11, band.getFrontMan().getEyeColor().name());
            st.setString(12, band.getFrontMan().getHairColor().name());
            st.setString(13, band.getFrontMan().getNationality().name());
            st.setLong(14, band.getFrontMan().getLocation().getX());
            st.setInt(15, band.getFrontMan().getLocation().getY());
            st.setString(16, band.getFrontMan().getLocation().getName());
            st.setLong(17, id);
            st.setString(18, username);
            System.out.println(st);

            ResultSet rs = st.executeQuery();
            if (rs.next()){
                return new Response(ResponseStatus.OK, "Данные о группе обновлены.");
            }

        } catch (NumberFormatException e){
            return new Response(ResponseStatus.ERROR, "Неверный id.");
        } catch (Exception e){
            return new Response(ResponseStatus.ERROR, e.getMessage());
        }
        return new Response(ResponseStatus.ERROR, "Ошибка при выполнении запроса.");
    }
}

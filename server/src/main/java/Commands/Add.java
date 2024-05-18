package Commands;

import SharedModels.MusicBand;
import SharedModels.Response;
import SharedUtility.ResponseStatus;
import SharedUtility.CommandType;
import Utility.OwnerCommand;

import java.sql.*;
import java.util.ArrayList;

public class Add extends OwnerCommand {
    Connection connection;
    public Add(Connection connection) {
        super("add", CommandType.ADD);
        this.connection =  connection;
    }

    @Override
    public Response execute(ArrayList<Object> args, String username) {
        try {
            MusicBand band = (MusicBand) args.get(0);

            PreparedStatement st = connection.prepareStatement(
                    """
                            INSERT INTO MusicBand(name,
                                coordinate_x,
                                coordinate_y,
                                creationDate,
                                numberOfParticipants,
                                albumsCount,
                                description,
                                genre,
                                frontMan_name,
                                frontMan_weight,
                                frontMan_eyeColor,
                                frontMan_hairColor,
                                frontMan_nationality,
                                frontMan_location_x,
                                frontMan_location_y,
                                frontMan_location_name,
                                creator)
                            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
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
            st.setString(17, username);

            int new_id = -1;
            ResultSet rs = st.executeQuery();
            if (rs.next()){
                new_id = rs.getInt(1);
            }
            if (new_id == -1){
                return new Response(ResponseStatus.ERROR, "Не удалось добавить объект");
            }
            return new Response(ResponseStatus.OK, "Новый объект добавлен");
        } catch (Exception e){
            return new Response(ResponseStatus.ERROR, e.getMessage());
        }
    }
}

package Commands;

import Models.CollectionManager;
import SharedModels.MusicBand;
import SharedModels.Response;
import SharedUtility.ResponseStatus;
import Utility.Command;
import SharedUtility.CommandType;

import java.sql.*;
import java.util.ArrayList;

public class Add extends Command {
    Connection connection;
    public Add(Connection connection) {
        super("add", CommandType.ADD);
        this.connection =  connection;
    }

    @Override
    public Response execute(ArrayList<Object> args) {
        try {
            MusicBand band = (MusicBand) args.get(0);

            PreparedStatement st = connection.prepareStatement(
                    "INSERT INTO MusicBand(name,\n" +
                    "    coordinate_x,\n" +
                    "    coordinate_y,\n" +
                    "    creationDate,\n" +
                    "    numberOfParticipants,\n" +
                    "    albumsCount,\n" +
                    "    description,\n" +
                    "    genre,\n" +
                    "    frontMan_name,\n" +
                    "    frontMan_weight,\n" +
                    "    frontMan_eyeColor,\n" +
                    "    frontMan_hairColor,\n" +
                    "    frontMan_nationality,\n" +
                    "    frontMan_location_x,\n" +
                    "    frontMan_location_y,\n" +
                    "    frontMan_location_name)\n" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)\n" +
                    "RETURNING id;"
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

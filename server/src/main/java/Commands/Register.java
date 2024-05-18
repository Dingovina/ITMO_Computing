package Commands;

import SharedModels.Response;
import SharedUtility.CommandType;
import SharedUtility.ResponseStatus;
import Utility.OwnerCommand;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Register extends OwnerCommand {
    private Connection connection;
    public Register(Connection connection) {
        super("register", CommandType.REGISTER);
        this.connection = connection;
    }

    private String encode(String pass) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] messageDigest = md.digest(pass.getBytes());

        BigInteger no = new BigInteger(1, messageDigest);
        return no.toString(16);
    }


    @Override
    public Response execute(ArrayList<Object> args, String user) {
        try {
            PreparedStatement st = connection.prepareStatement("""
                    SELECT * FROM users
                    WHERE username = ?;
                    """);
            st.setString(1, user);
            ResultSet rs = st.executeQuery();
            if (rs.next()){
                return new Response(ResponseStatus.ERROR, "Пользователь с таким именем уже зарегистрирован.");
            }
            else{
                PreparedStatement reg = connection.prepareStatement("""
                    INSERT INTO users(username, password)
                    VALUES(?, ?);
                    """);
                String password = encode(args.get(0).toString());
                reg.setString(1, user);
                reg.setString(2, password);
                reg.execute();
                return new Response(ResponseStatus.OK, "Пользователь зарегистрирован");
            }
        }catch (Exception e){
            return new Response(ResponseStatus.ERROR, e.getMessage());
        }

    }
}

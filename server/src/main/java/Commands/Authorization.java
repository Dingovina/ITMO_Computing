package Commands;

import SharedModels.Response;
import SharedUtility.CommandType;
import SharedUtility.ResponseStatus;
import Utility.OwnerCommand;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Objects;
import java.security.MessageDigest;

public class Authorization extends OwnerCommand {
    private final Connection connection;
    public Authorization(Connection connection) {
        super("authorization", CommandType.AUTHORIZE);
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
            String password = encode(args.get(0).toString());
            PreparedStatement st = connection.prepareStatement("""
                    SELECT password FROM users
                    WHERE username = ?;
                    """
            );
            st.setString(1, user);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String hash_pass = rs.getString(1);
                if (Objects.equals(hash_pass, password)) {
                    return new Response(ResponseStatus.OK, "Авторизация успешна");
                }
            }
            return new Response(ResponseStatus.ERROR, "Неверное имя пользователя или пароль");
        } catch (Exception e){
            return new Response(ResponseStatus.ERROR, e.getMessage());
        }
    }
}

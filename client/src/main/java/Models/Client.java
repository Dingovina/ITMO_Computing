package Models;

import SharedModels.Request;
import SharedModels.Response;
import SharedUtility.ResponseStatus;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Objects;

public class Client {
    private final InetAddress host;
    private final int port;
    private Socket socket;
    private ObjectOutputStream serverWriter = null;
    private ObjectInputStream serverReader = null;

    public Client(InetAddress host, int port){
        this.host = host;
        this.port = port;
    }
    public void connectToServer() throws IOException {
        socket = new Socket(host, port);
        serverWriter = new ObjectOutputStream(socket.getOutputStream());
    }

    public void disconnectFromServer() throws IOException {
        socket.close();
        serverWriter.close();
        serverReader.close();
        serverWriter = null;
        serverReader = null;
    }

    public Response sendRequest(Request request){
        if (Objects.isNull(request))
            return new Response(ResponseStatus.ERROR, "Неверный запрос.");
        try {
            if (Objects.isNull(serverWriter)) {
                connectToServer();
            }
            serverWriter.writeObject(request);
            serverWriter.flush();
            serverReader = new ObjectInputStream(socket.getInputStream());

            Response response = (Response) serverReader.readObject();
            disconnectFromServer();
            return response;
        } catch (Exception e){
            System.out.println(e);
            return new Response(ResponseStatus.ERROR, "Ошибка при отправке запроса.");
        }
    }
}
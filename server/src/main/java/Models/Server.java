package Models;

import SharedModels.Request;
import SharedModels.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Logger;


public class Server {
    final int port;
    Selector selector;
    ServerSocketChannel serverSocketChannel;
    DumpManager dumpManager;
    CollectionManager collectionManager;
    private BufferedReader input = new BufferedReader(new InputStreamReader(new BufferedInputStream(System.in)));
    public Server(int port, DumpManager dumpManager, CollectionManager collectionManager, Scanner input){
        this.port = port;
        this.dumpManager = dumpManager;
        this.collectionManager = collectionManager;
    }

    public void run() throws IOException, ClassNotFoundException, InterruptedException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Server started!");

        while (true) {
            if (input.ready()){
                String line = input.readLine();
                if (Objects.equals(line.strip(), "save")){
                    collectionManager.save();
                }
                else {
                    System.out.println("Unknown server command.");
                }
            }
            selector.select(50);
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();

                if (key.isAcceptable()) {
                    register(selector, serverSocketChannel);
                }

                if (key.isReadable()) {
                    Thread.sleep(50);
                    answer(key);
                }
                iter.remove();
            }
        }
    }

    private void answer(SelectionKey key)
            throws IOException, ClassNotFoundException {

        SocketChannel client = (SocketChannel) key.channel();
        client.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        int r = client.read(buffer);
        if (r == -1) {
            client.close();
        }
        else {
            buffer.flip();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            Object obj = ois.readObject();
            ois.close();
            Request request = (Request) obj;

            Response response = collectionManager.call(request);

            var baos = new ByteArrayOutputStream();
            var oos = new ObjectOutputStream(baos);
            oos.writeObject(response);
            oos.flush();
            var responseData = baos.toByteArray();
            client.write(ByteBuffer.wrap(responseData));
            buffer.clear();
        }
    }

    private static void register(Selector selector, ServerSocketChannel serverSocketChannel)
            throws IOException {

        SocketChannel client = serverSocketChannel.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }
}
package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private static Socket client;
    public static Socket getClient() {
        return client;
    }

    public void startServer() {
        startServer(ServerSettings.getPORT());
    }

    public void startServer(int port) {
        boolean running = true;
        while (running) {
            try {
                ServerSocket server = new ServerSocket(port);
                client = server.accept();
                Runnable clientHandler = new ClientHandler(client);
                new Thread(clientHandler).start();
            } catch (IOException ignored) {

            }
        }
    }

    @Override
    public void run() {
        startServer();
    }
}
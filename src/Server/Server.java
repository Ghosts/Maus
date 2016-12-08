package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server: represents the outermost layer of a clients connection to the program.
 */
public class Server implements Runnable {
    private static Socket client;

    public static Socket getClient() {
        return client;
    }

    /* Default to port 22122. */
    public void startServer() {
        startServer(ServerSettings.getPORT());
    }

    /**
     * Start server and allow for outside connections to be routed.
     */
    public void startServer(int port) {
        try (ServerSocket server = new ServerSocket(port)) {
            boolean running = true;
            while (running) {
                /* Passes output for each method requiring output access, removed need for class variables */
                try {
                    client = server.accept();
                    Runnable clientHandler = new ClientHandler(client);
                    new Thread(clientHandler).start();
                } catch (IOException e) {
                    //Serves to break out of while, exception throw accomplishes the same task.
                    running = false;
                }
            }
        } catch (IOException e) {
            System.exit(0);
        }
    }

    @Override
    public void run() {
        startServer();
    }
}
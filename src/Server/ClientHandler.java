package Server;

import GUI.Controller;
import GUI.Views.NotificationView;
import Server.Data.PseudoBase;
import Server.Data.Repository;
import javafx.application.Platform;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientHandler implements Runnable, Repository {
    private Socket socket;
    private ClientObject client;

    ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            String ip = (((InetSocketAddress) Server.getClient().getRemoteSocketAddress()).getAddress()).toString().replace("/", "");
            /* Check to ensure there's room left via Max Connections setting. */
            if (CONNECTIONS.size() < ServerSettings.getMaxConnections()) {
                if (!CONNECTIONS.containsKey(ip)) {
                    client = new ClientObject(socket, "Maus Machine " + (PseudoBase.getMausData().size() + 1), ip);
                } else {
                    client = new ClientObject(socket, CONNECTIONS.get(ip).getNickName(), ip);
                }
                Platform.runLater(() -> PseudoBase.getMausData().put(ip, client));
            }
            Controller.updateStats();
            /* Notification on new client connection */
            Platform.runLater(() -> NotificationView.openNotification(client));
            InputStream is = client.getClient().getInputStream();

            /* Begin listening for client commands via ProcessCommands */
            ProcessCommands.processCommands(is, client);
        } catch (IOException e) {
            client.setOnlineStatus("Offline");
        }
    }
}
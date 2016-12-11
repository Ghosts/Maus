package Server;

import GUI.Controller;
import GUI.Views.NotificationView;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientHandler implements Runnable, Repository {
    private Socket socket;
    private PrintWriter clientOutput;
    private ClientObject client;

    ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream())) {
            clientOutput = out;
            String ip = (((InetSocketAddress) Server.getClient().getRemoteSocketAddress()).getAddress()).toString().replace("/", "");
            client = new ClientObject(socket, "Maus Machine " + (PseudoBase.getMausData().size() + 1), ip);
            Controller.updateStats();
            Platform.runLater(() -> PseudoBase.getMausData().put(ip, client));
            Platform.runLater(() -> {
                Stage stage = new Stage();
                stage.setWidth(300);
                stage.setHeight(100);
                Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
                NotificationView notificationView = new NotificationView();
                stage.setScene(new Scene(notificationView.getNotificationView(), 300, 100));
                stage.setResizable(false);
                stage.setAlwaysOnTop(true);
                stage.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - 300);
                stage.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() - 100);
                stage.initStyle(StageStyle.UNDECORATED);
                notificationView.getNotificationText().setText("New Connection: " + client.getIP());
                stage.show();
                PauseTransition delay = new PauseTransition(Duration.seconds(5));
                delay.setOnFinished(event -> stage.close());
                delay.play();
            });
            requestHandler(in);
        } catch (IOException | ClassNotFoundException e) {
            client.setOnlineStatus("Offline");
        }
    }

    private void requestHandler(BufferedReader clientInput) throws IOException, ClassNotFoundException {
        String inp;
        do {

            inp = clientInput.readLine();
            assert inp != null;
            client.clientCommunicate("e");
            if (inp.equals("1")) {
                client.clientCommunicate("1");
            } else if (inp.equals("forciblyclose")) {
                client.clientCommunicate("forciblyclose");
            }
        } while (!inp.contains("forciblyclose"));
    }
}
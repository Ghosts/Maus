package GUI.Views;

import GUI.Components.BottomBar;
import GUI.Components.TopBar;
import Server.ClientObject;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sun.awt.PlatformFont;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static java.lang.Thread.sleep;


public class RemoteDesktopView {
    private static ClientObject client;

    private static void setClient(ClientObject client) {
        RemoteDesktopView.client = client;
    }

    public BorderPane getRemoteDesktopView(ClientObject client, Stage stage) {
        setClient(client);
        BorderPane borderPane = new BorderPane();
        borderPane.getStylesheets().add(getClass().getResource("/css/global.css").toExternalForm());
        ImageView imageView = new ImageView();
        borderPane.setTop(new TopBar().getTopBarSansOptions(stage));
        borderPane.setCenter(imageView);
        borderPane.setBottom(new BottomBar().getBottomBar());
        return borderPane;
    }
}

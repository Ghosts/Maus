package GUI.Components;

import Maus.Maus;
import Server.ClientObject;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;

import java.io.*;

public class FileContextMenu {
    public static ContextMenu getFileContextMenu(HBox fileIcon, String fileName, MouseEvent e, ClientObject client) {
        ContextMenu cm = new ContextMenu();
        MenuItem sb1 = new MenuItem("Delete File");
        MenuItem sb2 = new MenuItem("Download File");
        sb2.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select download location");
            File selectedDirectory =
                    directoryChooser.showDialog(Maus.getPrimaryStage());
            try {
                client.clientCommunicate("DOWNLOAD");
                client.clientCommunicate(fileName);

                DataInputStream dis = new DataInputStream(client.getClient().getInputStream());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        cm.getItems().addAll(sb1, sb2);
        cm.show(fileIcon, e.getScreenX(), e.getScreenY());
        return cm;
    }
}

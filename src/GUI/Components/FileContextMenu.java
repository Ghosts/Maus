package GUI.Components;

import Maus.Maus;
import Server.ClientObject;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

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
                BufferedOutputStream bos = new BufferedOutputStream(client.getClient().getOutputStream());
                DataOutputStream dos = new DataOutputStream(bos);
                dos.writeUTF(fileName);
                dos.writeUTF(selectedDirectory.toString());
                dos.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        cm.getItems().addAll(sb1, sb2);
        cm.show(fileIcon, e.getScreenX(), e.getScreenY());
        return cm;
    }
}

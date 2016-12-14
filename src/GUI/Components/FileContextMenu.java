package GUI.Components;

import GUI.Controller;
import GUI.Views.SendCommandView;
import Logger.Logger;
import Server.ClientObject;
import Server.Data.PseudoBase;
import Server.Data.Repository;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileContextMenu {
        public static ContextMenu getFileContextMenu(HBox fileIcon, String fileName, MouseEvent e) {
            ContextMenu cm = new ContextMenu();
            MenuItem sb1 = new MenuItem("Delete File");
            MenuItem sb2 = new MenuItem("Download File");
            cm.getItems().addAll(sb1,sb2);
            cm.show(fileIcon, e.getScreenX(), e.getScreenY());
            return cm;
        }
}

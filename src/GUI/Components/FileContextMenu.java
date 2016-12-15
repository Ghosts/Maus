package GUI.Components;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

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

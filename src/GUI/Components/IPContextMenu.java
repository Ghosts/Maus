package GUI.Components;

import GUI.Styler;
import Server.ServerSettings;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;

/**
 * Created by caden on 12/7/2016.
 */
public class IPContextMenu {
    public ContextMenu getIPContextMenu(TableCell n, MouseEvent e) {
        ContextMenu cm = new ContextMenu();
        Menu mi1 = new Menu("Perform Action...");
        MenuItem sb1 = new MenuItem("File Explorer");
        MenuItem sb2 = new MenuItem("Open Terminal");
        Menu sb3 = new Menu("Beacon...");
        String bStatus = ServerSettings.isBeaconStatus() ? "On" : "Off";
        MenuItem beaconStatus = new MenuItem("Status: " + bStatus);
        beaconStatus = Styler.styleAdd(beaconStatus, "beacon");
        beaconStatus.setDisable(true);
        MenuItem ssm1 = new MenuItem("Start Beacon");
        MenuItem ssm2 = new MenuItem("Stop Beacon");
        sb3.getItems().addAll(beaconStatus, ssm1, ssm2);
        mi1.getItems().addAll(sb1, sb2, sb3);
        MenuItem mi2 = new MenuItem("Copy IP");
        mi2.setOnAction(event -> {
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(n.getText());
            clipboard.setContent(content);
        });
        MenuItem mi3 = new MenuItem("Uninstall Server");
        cm.getItems().addAll(mi1, mi2, mi3);
        cm.show(n, e.getScreenX(), e.getScreenY());
        return cm;
    }
}

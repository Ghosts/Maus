package GUI.Components;

import Client.ClientObject;
import GUI.Controller;
import GUI.Styler;
import GUI.Views.SendCommandView;
import Logger.Level;
import Logger.Logger;
import Server.Data.PseudoBase;
import Server.Data.Repository;
import Server.ServerSettings;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class IPContextMenu implements Repository {
    ContextMenu getIPContextMenu(TableCell n, MouseEvent e) {
        ContextMenu cm = new ContextMenu();
        Menu mi1 = new Menu("Perform Action...");
        MenuItem sb1 = new MenuItem("File Explorer");
        MenuItem sb2 = new MenuItem("Send Command");
        sb2.setOnAction(event -> {
            ClientObject clientObject = ((ClientObject) n.getTableView().getSelectionModel().getSelectedItem());
            Stage stage = new Stage();
            SendCommandView sendCommandView = new SendCommandView();
            stage.setTitle("Send a command...");
            stage.setScene(new Scene(sendCommandView.getSendCommandView(), 300, 200));
            stage.show();
            sendCommandView.getsendCommandButton().setOnAction(a -> {
                clientObject.clientCommunicate("CMD " + sendCommandView.getTextField().getText());

//                Platform.runLater(() -> {
//                    try {
//                        String comm;
//                        BufferedR eader in = new BufferedReader(new InputStreamReader(clientObject.getClient().getInputStream()));
//                        clientObject.clientCommunicate("CMD " + sendCommandView.getTextField().getText());
//                        do {
//                            comm = in.readLine();
//                            sendCommandView.getConsole().appendText(comm);
//                        } while (!comm.contains("end"));
//                    } catch (IOException e1) {
//                        e1.printStackTrace();
//                    }
//                });
            });
        });
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
        mi2.setOnAction(evnt -> {
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(n.getText());
            clipboard.setContent(content);
        });
        MenuItem mi3 = new MenuItem("Uninstall Server");
        mi3.setOnAction(evnt -> {
            ClientObject clientObject = ((ClientObject) n.getTableView().getSelectionModel().getSelectedItem());
            try {
                if (clientObject.getClient() != null) {
                    if (clientObject.getClient().isConnected()) {
                        clientObject.getClient().close();
                    }
                }
                PseudoBase.getMausData().remove(clientObject);
                ClientObject.setCOUNT(ClientObject.getCOUNT() - 1);
                CONNECTIONS.remove(clientObject.getIP());
                ((ClientObject) n.getTableRow().getItem()).clientCommunicate("forciblyclose");
                Controller.updateStats();
            } catch (IOException e1) {
                Logger.log(Level.WARNING, "Exception thrown: " + e);
            }
        });
        cm.getItems().addAll(mi1, mi2, mi3);
        cm.show(n, e.getScreenX(), e.getScreenY());
        return cm;
    }
}

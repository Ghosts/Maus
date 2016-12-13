package GUI.Components;

import GUI.Controller;
import GUI.Views.SendCommandView;
import Logger.Level;
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
                if(clientObject != null) {
                    clientObject.clientCommunicate("CMD " + sendCommandView.getTextField().getText());
                    Platform.runLater(() -> {
                        try {
                            String comm;
                            BufferedReader in = new BufferedReader(new InputStreamReader(clientObject.getClient().getInputStream()));
                            clientObject.clientCommunicate("CMD " + sendCommandView.getTextField().getText());
//                        while((comm = in.readLine()) != null && !comm.contains("end") ){
//                            sendCommandView.getConsole().appendText(comm);
//                        }
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    });
                }
            });
        });
        mi1.getItems().addAll(sb1, sb2);
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
                PseudoBase.getMausData().remove(clientObject.getIP());
                CONNECTIONS.remove(clientObject.getIP());
                ((ClientObject) n.getTableRow().getItem()).clientCommunicate("forciblyclose");
                Controller.updateStats();
                Controller.updateTable();
            } catch (IOException e1) {
                Logger.log(Level.WARNING, "Exception thrown: " + e);
            }
        });
        cm.getItems().addAll(mi1, mi2, mi3);
        cm.show(n, e.getScreenX(), e.getScreenY());
        return cm;
    }
}

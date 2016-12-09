package GUI;

import Client.ClientObject;
import GUI.Components.ClientList;
import GUI.Components.StatisticsView;
import Server.Data.PseudoBase;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.util.List;

public class Controller {

    public static synchronized void updateTable(){
        ObservableList<ClientObject> list =  FXCollections.observableArrayList();
        for(ClientObject value : PseudoBase.getMausData().values()){
            list.add(value);
        }
        ClientList.getTableView().setItems(list);
        TableView tableView = ClientList.getTableView();
        tableView.refresh();
    }

    public static synchronized void updateStats() {
        Platform.runLater(() -> StatisticsView.getConnectionsLabel().setText("Connections: " + PseudoBase.getMausData().size()));
    }
}

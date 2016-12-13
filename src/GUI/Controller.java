package GUI;

import GUI.Components.ClientList;
import GUI.Views.StatisticsView;
import Maus.Maus;
import Server.ClientObject;
import Server.Data.PseudoBase;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

public class Controller {

    /* Reloads the table to ensure Offline/Online status is accurate. */
    public static void updateTable() {
        ObservableList<ClientObject> list = FXCollections.observableArrayList();
        for (ClientObject value : PseudoBase.getMausData().values()) {
            list.add(value);
        }
        ClientList.getTableView().setItems(list);
        TableView tableView = ClientList.getTableView();
        tableView.refresh();
    }

    /* Refreshes the number of connections based on MausData size. */
    public static void updateStats() {
        Platform.runLater(() -> StatisticsView.getConnectionsLabel().setText("Connections: " + PseudoBase.getMausData().size()));
    }

    /* Changes the primary view to the provided scene. */
    public static void changePrimaryStage(Pane newScene) {
        Maus.getPrimaryStage().setScene(new Scene(newScene, 900, 500));

    }
}

package GUI.Components;

import GUI.Controller;
import Server.ClientObject;
import Server.Data.PseudoBase;
import Server.Data.Repository;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.util.Duration;

public class ClientList implements Repository {
    private static TableView tableView;

    public static TableView getTableView() {
        return tableView;
    }

    public TableView getClientList() {
        Timeline fiveSecondTime = new Timeline(new KeyFrame(Duration.seconds(5), event -> Controller.updateTable()));
        fiveSecondTime.setCycleCount(Timeline.INDEFINITE);
        fiveSecondTime.play();
        tableView = new TableView();
        tableView.setEditable(true);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getStylesheets().add(getClass().getResource("/css/global.css").toExternalForm());

        TableColumn<String, String> onlineStatus = new TableColumn<>("Status");
        onlineStatus.setMaxWidth(70);
        onlineStatus.setResizable(false);
        onlineStatus.setCellValueFactory(
                new PropertyValueFactory<>("onlineStatus"));

        TableColumn<ClientObject, String> nickName = new TableColumn<>("Nickname");
        nickName.setMinWidth(150);
        nickName.setMaxWidth(200);
        nickName.setResizable(false);
        nickName.setCellValueFactory(new PropertyValueFactory<>("nickName"));
        nickName.setCellFactory(TextFieldTableCell.forTableColumn());
        nickName.setOnEditCommit(
                t -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setNickName(t.getNewValue())
        );

        TableColumn<ClientObject, String> IP = new TableColumn<>("IP");
        IP.setMinWidth(600);
        IP.setResizable(false);
        IP.setCellValueFactory(new PropertyValueFactory<>("IP"));
        IP.setCellFactory(col -> {
            final TableCell<ClientObject, String> cell = new TableCell<>();
            cell.textProperty().bind(cell.itemProperty()); // in general might need to subclass TableCell and override updateItem(...) here
            cell.setOnMouseClicked(event -> {
                if (event.getButton().equals(MouseButton.SECONDARY) && cell.getTableView().getSelectionModel().getSelectedItem() != null && cell.getTableView().getSelectionModel().getSelectedItem().getClient().isConnected()) {
                    IPContextMenu.getIPContextMenu(cell, event);
                }
            });
            return cell;
        });
        ObservableList<ClientObject> list = FXCollections.observableArrayList();
        for (ClientObject value : CONNECTIONS.values()) {
            list.add(value);
        }
        tableView.setItems(list);
        tableView.getColumns().addAll(onlineStatus, nickName, IP);
        return tableView;
    }
}

package GUI.Components;

import Client.ClientObject;
import GUI.Controller;
import GUI.Styler;
import Server.Data.PseudoBase;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.util.Duration;

import java.io.IOException;

public class ClientList {
    private static TableView tableView;

    public static TableView getTableView() {
        return tableView;
    }

    public TableView getClientList() throws IOException, ClassNotFoundException {
        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Controller.updateTable();
            }
        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
        tableView = new TableView();
        tableView.setEditable(true);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getStylesheets().add(Styler.globalCSS);

        TableColumn<String, String> onlineStatus = new TableColumn("Status");
        onlineStatus.setMaxWidth(100);
        onlineStatus.setResizable(false);
        onlineStatus.setCellValueFactory(
                new PropertyValueFactory<>("onlineStatus"));

        TableColumn nickName = new TableColumn<>("Nickname");
        nickName.setMinWidth(100);
        nickName.setMaxWidth(900);
        nickName.setResizable(true);
        nickName.setCellValueFactory(new PropertyValueFactory<ClientObject, String>("nickName"));
        nickName.setCellFactory(TextFieldTableCell.forTableColumn());
        nickName.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<ClientObject, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<ClientObject, String> t) {
                        t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setNickName(t.getNewValue());
                    }
                }
        );

        TableColumn<ClientObject, String> IP = new TableColumn("IP");
        IP.setMinWidth(100);
        IP.setResizable(true);
        IP.setCellValueFactory(new PropertyValueFactory<>("IP"));
        IP.setCellFactory(col -> {
            final TableCell<ClientObject, String> cell = new TableCell<>();
            cell.textProperty().bind(cell.itemProperty()); // in general might need to subclass TableCell and override updateItem(...) here
            cell.setOnMouseClicked(event -> {
                if (event.getButton().equals(MouseButton.SECONDARY)) {
                    new IPContextMenu().getIPContextMenu(cell, event);
                }
            });
            return cell;
        });
        ObservableList<ClientObject> list =  FXCollections.observableArrayList();
        for(ClientObject value : PseudoBase.getMausData().values()){
            list.add(value);
        }
        tableView.setItems(list);
        tableView.getColumns().addAll(onlineStatus, nickName, IP);
        return tableView;
    }
}

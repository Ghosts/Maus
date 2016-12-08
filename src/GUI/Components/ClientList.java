package GUI.Components;

import Client.ClientObject;
import GUI.Styler;
import Server.Data.PseudoBase;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;

import java.io.IOException;

public class ClientList {
    public TableView getClientList() throws IOException, ClassNotFoundException {
        TableView tableView = new TableView();
        tableView.setEditable(true);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getStylesheets().add(Styler.globalCSS);

        TableColumn<Integer, String> clientNumber = new TableColumn("#");
        clientNumber.setMaxWidth(400);
        clientNumber.setResizable(false);
        clientNumber.setCellValueFactory(
                new PropertyValueFactory<>("clientNumber"));

        TableColumn nickName = new TableColumn<>("Nickname");
        nickName.setMinWidth(100);
        nickName.setMaxWidth(900);
        nickName.setCellValueFactory(new PropertyValueFactory<ClientObject, String>("nickName"));
        nickName.setCellFactory(TextFieldTableCell.forTableColumn());
        nickName.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<ClientObject, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<ClientObject, String> t) {
                        ((ClientObject) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setNickName(t.getNewValue());
                    }
                }
        );

        TableColumn<ClientObject, String> IP = new TableColumn("IP");
        IP.setMinWidth(100);
        IP.setMaxWidth(900);
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


        tableView.setItems(PseudoBase.getMausData());
        tableView.getColumns().addAll(clientNumber, nickName, IP);
        return tableView;
    }
}

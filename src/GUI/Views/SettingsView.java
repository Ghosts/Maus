package GUI.Views;


import GUI.Components.TopBar;
import GUI.Styler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

class SettingsView {

    BorderPane getSettingsView() {
        BorderPane borderPane = new BorderPane();
        borderPane.getStylesheets().add(Styler.globalCSS);
        borderPane.setTop(new TopBar().getTopBar());
        borderPane.setLeft(settingsViewLeft());
        borderPane.setCenter(settingsViewCenter());
        borderPane.setBottom(new StatisticsView().getStatisticsView());
        return borderPane;
    }

    private HBox settingsViewLeft() {
        HBox hBox = Styler.hContainer(20);
        hBox.getStylesheets().add(Styler.globalCSS);
        hBox.setId("clientBuilder");
        hBox.setPadding(new Insets(20, 20, 20, 20));
        Label title = (Label) Styler.styleAdd(new Label("Settings"), "title");
        hBox.getChildren().add(Styler.vContainer(20, title));
        return hBox;
    }

    private HBox settingsViewCenter() {
        HBox hBox = Styler.hContainer(20);
        hBox.getStylesheets().add(Styler.globalCSS);
        hBox.setId("settingsView");
        hBox.setPadding(new Insets(20, 20, 20, 20));
        Label title = (Label) Styler.styleAdd(new Label(" "), "title");

        Label serverIPLabel = (Label) Styler.styleAdd(new Label("Refresh Rate: "), "label-bright");
        TextField serverIP = new TextField("");
        HBox serverIPBox = Styler.hContainer(serverIPLabel, serverIP);
        serverIP.setEditable(true);
        serverIP.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                serverIP.setText(newValue.replaceAll("[^\\d]", ""));
            }
        }));

        Label clientNameLabel = (Label) Styler.styleAdd(new Label("Max Connections: "), "label-bright");
        TextField clientName = new TextField("");
        HBox clientNameBox = Styler.hContainer(clientNameLabel, clientName);
        clientName.setEditable(true);
        clientName.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                serverIP.setText(newValue.replaceAll("[^\\d]", ""));
            }
        }));

        Button applySettings = new Button("Apply Settings");
        applySettings.setPrefWidth(150);
        applySettings.setPrefHeight(50);

        hBox.getChildren().add(Styler.vContainer(20, title, serverIPBox, clientNameBox, applySettings));
        return hBox;
    }
}

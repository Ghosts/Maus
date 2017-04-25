package GUI.Views;


import GUI.Components.BottomBar;
import GUI.Components.TopBar;
import GUI.Styler;
import Maus.Maus;
import Server.ServerSettings;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

class SettingsView {

    BorderPane getSettingsView() {
        BorderPane borderPane = new BorderPane();
        borderPane.getStylesheets().add(getClass().getResource("/css/global.css").toExternalForm());
        borderPane.setTop(new TopBar().getTopBar(Maus.getPrimaryStage()));
        borderPane.setLeft(settingsViewLeft());
        borderPane.setCenter(settingsViewCenter());
        borderPane.setBottom(new BottomBar().getBottomBar());
        return borderPane;
    }

    private HBox settingsViewLeft() {
        HBox hBox = Styler.hContainer(20);
        hBox.getStylesheets().add(getClass().getResource("/css/global.css").toExternalForm());
        hBox.setId("clientBuilder");
        hBox.setPadding(new Insets(20, 20, 20, 20));
        Label title = (Label) Styler.styleAdd(new Label("Settings"), "title");
        hBox.getChildren().add(Styler.vContainer(20, title));
        return hBox;
    }

    private HBox settingsViewCenter() {
        HBox hBox = Styler.hContainer(20);
        hBox.getStylesheets().add(getClass().getResource("/css/global.css").toExternalForm());
        hBox.setId("settingsView");
        hBox.setPadding(new Insets(20, 20, 20, 20));
        Label title = (Label) Styler.styleAdd(new Label(" "), "title");

        Label listeningPortLabel = (Label) Styler.styleAdd(new Label("Listening Port: "), "label-bright");
        TextField listeningPort = new TextField("" + ServerSettings.getPORT());
        HBox listeningPortBox = Styler.hContainer(listeningPortLabel, listeningPort);
        listeningPort.setEditable(true);
        listeningPort.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                listeningPort.setText(newValue.replaceAll("[^\\d]", ""));
            }
        }));

        Label refreshRateLabel = (Label) Styler.styleAdd(new Label("Refresh Rate: "), "label-bright");
        TextField refreshRate = new TextField("" + ServerSettings.getRefreshRate());
        HBox refreshRateBox = Styler.hContainer(refreshRateLabel, refreshRate);
        refreshRate.setEditable(true);
        refreshRate.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                refreshRate.setText(newValue.replaceAll("[^\\d]", ""));
            }
        }));

        Label maxConnectionsLabel = (Label) Styler.styleAdd(new Label("Max Connections: "), "label-bright");
        TextField maxConnections = new TextField("" + ServerSettings.getMaxConnections());
        HBox maxConnectionsBox = Styler.hContainer(maxConnectionsLabel, maxConnections);
        maxConnections.setEditable(true);
        maxConnections.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                maxConnections.setText(newValue.replaceAll("[^\\d]", ""));
            }
        }));

        ToggleButton soundToggle = new ToggleButton();
        soundToggle.setSelected(ServerSettings.getSOUND());
        if (soundToggle.isSelected()) {
            soundToggle.setText("Sound (on) ");
        } else {
            soundToggle.setText("Sound (off) ");
        }
        soundToggle.setOnAction(event -> {
            if (soundToggle.isSelected()) {
                ServerSettings.setSOUND(true);
                soundToggle.setText("Sound (on) ");
            } else {
                ServerSettings.setSOUND(false);
                soundToggle.setText("Sound (off) ");
            }
        });
        Button applySettings = new Button("Apply Settings");
        applySettings.setPrefWidth(150);
        applySettings.setPrefHeight(50);
        applySettings.setOnAction(event -> {
            if (Integer.parseInt(listeningPort.getText()) != ServerSettings.getPORT()) {
                ServerSettings.setPORT(Integer.parseInt(listeningPort.getText()));
            }
            if (Integer.parseInt(refreshRate.getText()) != ServerSettings.getRefreshRate()) {
                ServerSettings.setRefreshRate(Integer.parseInt(refreshRate.getText()));
            }
            if (Integer.parseInt(maxConnections.getText()) != ServerSettings.getMaxConnections()) {
                ServerSettings.setMaxConnections(Integer.parseInt(maxConnections.getText()));
            }
            Platform.runLater(() -> NotificationView.openNotification("Settings Applied"));

        });
        hBox.getChildren().add(Styler.vContainer(20, title, listeningPortBox, refreshRateBox, maxConnectionsBox, soundToggle, applySettings));
        return hBox;
    }
}

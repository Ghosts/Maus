package GUI.Views;


import GUI.Components.BottomBar;
import GUI.Components.NotificationView;
import GUI.Components.TopBar;
import GUI.Styler;
import Maus.Maus;
import Server.ServerSettings;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
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
        TextField listeningPort = new TextField("" + ServerSettings.PORT);
        HBox listeningPortBox = Styler.hContainer(listeningPortLabel, listeningPort);
        listeningPort.setEditable(true);
        listeningPort.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                listeningPort.setText(newValue.replaceAll("[^\\d]", ""));
            }
        }));

        Label maxConnectionsLabel = (Label) Styler.styleAdd(new Label("Max Connections: "), "label-bright");
        TextField maxConnections = new TextField("" + ServerSettings.MAX_CONNECTIONS);
        HBox maxConnectionsBox = Styler.hContainer(maxConnectionsLabel, maxConnections);
        maxConnections.setEditable(true);
        maxConnections.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                maxConnections.setText(newValue.replaceAll("[^\\d]", ""));
            }
        }));

        CheckBox soundToggle = new CheckBox();
        soundToggle.setSelected(ServerSettings.SOUND);
        if (soundToggle.isSelected()) {
            soundToggle.setText("Sound (on) ");
        } else {
            soundToggle.setText("Sound (off) ");
        }
        soundToggle.setOnAction(event -> {
            if (soundToggle.isSelected()) {
                ServerSettings.SOUND = true;
                soundToggle.setText("Sound (on) ");
            } else {
                ServerSettings.SOUND = false;
                soundToggle.setText("Sound (off) ");
            }
        });

        CheckBox notificaitonToggle = new CheckBox();
        notificaitonToggle.setSelected(ServerSettings.SHOW_NOTIFICATIONS);
        if (notificaitonToggle.isSelected()) {
            notificaitonToggle.setText("Notifications (on) ");
        } else {
            notificaitonToggle.setText("Notifications (off) ");
        }
        notificaitonToggle.setOnAction(event -> {
            if (notificaitonToggle.isSelected()) {
                ServerSettings.SHOW_NOTIFICATIONS = true;
                notificaitonToggle.setText("Notifications (on) ");
            } else {
                ServerSettings.SHOW_NOTIFICATIONS = false;
                notificaitonToggle.setText("Notifications (off) ");
            }
        });

        CheckBox backgroundPersistentTogle = new CheckBox();
        backgroundPersistentTogle.setSelected(ServerSettings.BACKGROUND_PERSISTENT);
        if (backgroundPersistentTogle.isSelected()) {
            backgroundPersistentTogle.setText("Background Persistent (on) ");
        } else {
            backgroundPersistentTogle.setText("Background Persistent (off) ");
        }
        backgroundPersistentTogle.setOnAction(event -> {
            if (backgroundPersistentTogle.isSelected()) {
                ServerSettings.BACKGROUND_PERSISTENT = true;
                backgroundPersistentTogle.setText("Background Persistent (on) ");
            } else {
                ServerSettings.BACKGROUND_PERSISTENT = false;
                backgroundPersistentTogle.setText("Background Persistent (off) ");
            }
        });

        Button applySettings = new Button("Apply Settings");
        applySettings.setPrefWidth(150);
        applySettings.setPrefHeight(50);
        applySettings.setOnAction(event -> {
            if (Integer.parseInt(listeningPort.getText()) != ServerSettings.PORT) {
                ServerSettings.PORT = (Integer.parseInt(listeningPort.getText()));
            }
            if (Integer.parseInt(maxConnections.getText()) != ServerSettings.MAX_CONNECTIONS) {
                ServerSettings.MAX_CONNECTIONS = (Integer.parseInt(maxConnections.getText()));
            }
                Platform.runLater(() -> NotificationView.openNotification("Settings Applied"));
        });
        hBox.getChildren().add(Styler.vContainer(20, title, listeningPortBox, maxConnectionsBox, soundToggle,notificaitonToggle,backgroundPersistentTogle, applySettings));
        return hBox;
    }
}

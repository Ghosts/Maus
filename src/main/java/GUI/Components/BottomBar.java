package GUI.Components;

import GUI.Controller;
import GUI.Styler;
import Server.Data.PseudoBase;
import Server.Data.Repository;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


public class BottomBar implements Repository {
    private static Label connectionsLabel = null;

    public static Label getConnectionsLabel() {
        return connectionsLabel;
    }

    public HBox getBottomBar() {
        HBox hBox = new HBox();
        HBox.setHgrow(hBox, Priority.ALWAYS);
        VBox vBox = new VBox();
        vBox.getStylesheets().add(getClass().getResource("/css/global.css").toExternalForm());
        VBox.setVgrow(vBox, Priority.ALWAYS);
        connectionsLabel = new Label("  Connections: " + CONNECTIONS.size());
        connectionsLabel = (Label) Styler.styleAdd(connectionsLabel, "label-light");
        vBox.getChildren().add(connectionsLabel);
        hBox.getChildren().add(vBox);
        hBox.setId("stat-bar");
        return hBox;
    }
}

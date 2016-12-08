package GUI.Components;

import Client.ClientObject;
import GUI.Styler;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class StatisticsView {
    public HBox getStatisticsView() {
        HBox hBox = new HBox();
        HBox.setHgrow(hBox, Priority.ALWAYS);
        VBox vBox = new VBox();
        vBox.getStylesheets().add(Styler.globalCSS);
        VBox.setVgrow(vBox, Priority.ALWAYS);
        Label connectionsLabel = new Label("Connections: " + ClientObject.getCOUNT());
        connectionsLabel = (Label) Styler.styleAdd(connectionsLabel, "label-bright");
        vBox.getChildren().add(connectionsLabel);
        hBox.getChildren().add(vBox);
        return hBox;
    }
}

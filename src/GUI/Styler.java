package GUI;

import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class Styler {
    public static final String globalCSS = "Resources/css/global.css";

    public static Node styleAdd(Node node, String styleName) {
        node.getStyleClass().add(styleName);
        return node;
    }

    public static MenuItem styleAdd(MenuItem control, String styleName) {
        control.getStyleClass().add(styleName);
        return control;
    }

    public static VBox vContainer(Node... region) {
        VBox vBox = new VBox();
        VBox.setVgrow(vBox, Priority.ALWAYS);
        vBox.setMaxWidth(Double.MAX_VALUE);
        for (Node r : region) {
            vBox.getChildren().add(r);
        }
        return vBox;
    }
    public static VBox vContainer( int spacing, Node... region) {
        VBox vBox = new VBox(spacing);
        VBox.setVgrow(vBox, Priority.ALWAYS);
        vBox.setMaxWidth(Double.MAX_VALUE);
        for (Node r : region) {
            vBox.getChildren().add(r);
        }
        return vBox;
    }

    public static HBox hContainer(int spacing, Node... region) {
        HBox hBox = new HBox(spacing);
        HBox.setHgrow(hBox, Priority.ALWAYS);
        hBox.setMaxWidth(Double.MAX_VALUE);
        for (Node r : region) {
            hBox.getChildren().add(r);
        }
        return hBox;
    }

    public static HBox hContainer(Node... region) {
        HBox hBox = new HBox();
        HBox.setHgrow(hBox, Priority.ALWAYS);
        hBox.setMaxWidth(Double.MAX_VALUE);
        for (Node r : region) {
            hBox.getChildren().add(r);
        }
        return hBox;
    }

}

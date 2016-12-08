package GUI;

import javafx.scene.Node;
import javafx.scene.control.MenuItem;

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
}

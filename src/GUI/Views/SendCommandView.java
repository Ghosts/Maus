package GUI.Views;

import GUI.Styler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class SendCommandView {
    private Button sendCommandButton;
    private TextField textField;
    private TextArea console;

    public VBox getSendCommandView() {
        VBox vBox = new VBox();
        VBox.setVgrow(vBox, Priority.ALWAYS);
        Label label = new Label("Command:");
        label = (Label) Styler.styleAdd(label, "label-bright");
        textField = new TextField("");
        sendCommandButton = new Button("Send Command");
        sendCommandButton.setPadding(new Insets(20, 20, 20, 20));
        console = new TextArea("");
        vBox.getChildren().addAll(label, textField, sendCommandButton, console);
        vBox.getStylesheets().add(Styler.globalCSS);
        return vBox;
    }

    public Button getsendCommandButton() {
        return sendCommandButton;
    }

    public TextArea getConsole() {
        return console;
    }

    public TextField getTextField() {
        return textField;
    }
}

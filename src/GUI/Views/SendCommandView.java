package GUI.Views;

import GUI.Components.TopBar;
import GUI.Styler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SendCommandView {
    private Button sendCommandButton;
    private TextField textField;
    private TextArea console;

    public BorderPane getSendCommandView(Stage stage) {
        BorderPane borderPane = new BorderPane();
        borderPane.getStylesheets().add(Styler.globalCSS);
        borderPane.setTop(new TopBar().getTopBarSansOptions(stage));
        borderPane.setBottom(new StatisticsView().getStatisticsView());
        VBox vBox = new VBox(5);
        vBox.setId("settingsView");
        vBox.setAlignment(Pos.CENTER);
        VBox.setVgrow(vBox, Priority.ALWAYS);
        Label label = new Label("Command:");
        label = (Label) Styler.styleAdd(label, "label-bright");
        textField = new TextField("");
        sendCommandButton = new Button("Send Command");
        console = new TextArea("");
        console.setEditable(false);
        vBox.getChildren().addAll(label, textField, sendCommandButton, console);
        vBox.getStylesheets().add(Styler.globalCSS);
        borderPane.setCenter(vBox);
        return borderPane;
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

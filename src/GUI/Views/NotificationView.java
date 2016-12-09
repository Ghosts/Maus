package GUI.Views;

import GUI.Styler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Created by caden on 12/9/2016.
 */
public class NotificationView {
    public Label getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(Label notificationText) {
        this.notificationText = notificationText;
    }

    Label notificationText;
    public VBox getNotificationView(){
        VBox vBox = new VBox(5);
        vBox.setId("notification");
        vBox.setAlignment(Pos.CENTER);
        VBox.setVgrow(vBox, Priority.ALWAYS);
        Image alert = new Image("Resources/images/alert.png");
        ImageView imageView = new ImageView(alert);
        notificationText = new Label("New Client Connected.");
        notificationText = (Label) Styler.styleAdd(notificationText, "label-bright");
        vBox.getChildren().addAll(imageView, notificationText);
        vBox.getStylesheets().add(Styler.globalCSS);
        return vBox;
    }
}

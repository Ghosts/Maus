package GUI.Views;

import GUI.Styler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class NotificationView {
    Label notificationText;

    public Label getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(Label notificationText) {
        this.notificationText = notificationText;
    }

    public VBox getNotificationView() {
        VBox vBox = new VBox(5);
        vBox.setId("notification");
        vBox.setAlignment(Pos.CENTER);
        VBox.setVgrow(vBox, Priority.ALWAYS);
        Image alert = new Image("Resources/Images/Icons/alert.png");
        ImageView imageView = new ImageView(alert);
        notificationText = new Label("New Client.Client Connected.");
        notificationText = (Label) Styler.styleAdd(notificationText, "label-bright");
        vBox.getChildren().addAll(imageView, notificationText);
        vBox.getStylesheets().add(Styler.globalCSS);
        return vBox;
    }
}

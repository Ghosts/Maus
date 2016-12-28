package GUI.Views;

import GUI.Styler;
import Server.ClientObject;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class NotificationView {
    private Label notificationText;

    public static void openNotification(ClientObject client) {
        Stage stage = new Stage();
        stage.setWidth(300);
        stage.setHeight(100);
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        NotificationView notificationView = new NotificationView();
        stage.setScene(new Scene(notificationView.getNotificationView(), 300, 100));
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - 300);
        stage.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() - 100);
        stage.initStyle(StageStyle.UNDECORATED);
        notificationView.getNotificationText().setText("New Connection: " + client.getIP());
        stage.show();
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(event -> stage.close());
        delay.play();
    }

    Label getNotificationText() {
        return notificationText;
    }

    VBox getNotificationView() {
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

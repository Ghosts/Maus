package GUI.Views;

import GUI.Components.TopBar;
import GUI.Styler;
import Maus.Maus;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;


class UpdatesView {
    BorderPane updatesView = new BorderPane();

    BorderPane getUpdatesView() {
        updatesView.getStylesheets().add(Styler.globalCSS);
        HBox hBox1 = getUpdatesPanel();
        HBox hBox = getAboutPanel();
        hBox.setId("updatesView");
        hBox1.setId("updatesView");
        hBox.setPadding(new Insets(0, 0, 0, 10));
        updatesView.setTop(new TopBar().getTopBar(Maus.getPrimaryStage()));
        updatesView.setLeft(hBox);
        updatesView.setCenter(hBox1);
        updatesView.setBottom(new StatisticsView().getStatisticsView());
        return updatesView;
    }

    private HBox getAboutPanel() {
        Label title = (Label) Styler.styleAdd(new Label("Maus 0.5a"), "title");
        Label desc = (Label) Styler.styleAdd(new Label("Maus is a lightweight remote administrative tool " +
                "written in Java \nby a single developer. Maus is intended to present necessary \nfeatures in an attractive and " +
                "easy to use UI."), "label-bright");
        Button checkUpdates = new Button("Check for Updates");
        checkUpdates.setOnMouseClicked(event -> {
            Platform.runLater(() -> {
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
                notificationView.getNotificationText().setText("Update Check Complete!");
                stage.show();
                PauseTransition delay = new PauseTransition(Duration.seconds(3));
                delay.setOnFinished(e -> stage.close());
                delay.play();
            });
            HBox hBox = getUpdatesPanel();
            hBox.setId("updatesView");
            updatesView.setCenter(hBox);
        });
        return Styler.hContainer(Styler.vContainer(10, title, desc, checkUpdates));
    }

    private HBox getUpdatesPanel() {
        TextArea updates = new TextArea();
        updates.setPrefWidth(600);
        updates.setEditable(false);
        try {
            updates.setText(new Scanner(new URL("https://raw.githubusercontent.com/Ghosts/Maus/master/Changelog.txt").openStream(), "UTF-8").useDelimiter("\\A").next());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Styler.hContainer(updates);
    }

}

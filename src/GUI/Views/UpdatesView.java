package GUI.Views;

import GUI.Components.StatisticsView;
import GUI.Components.TopBar;
import GUI.Styler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;


public class UpdatesView {
    BorderPane updatesView;

    BorderPane getUpdatesView() {
        updatesView = new BorderPane();
        updatesView.getStylesheets().add(Styler.globalCSS);
        HBox hBox = getUpdatesPanel();
        HBox hBox1 = getAboutPanel();
        hBox.setId("updatesView");
        hBox1.setId("updatesView");
        updatesView.setTop(new TopBar().getTopBar());
        hBox1.setPadding(new Insets(0,0,0,10));
        updatesView.setLeft(hBox1);
        updatesView.setCenter(hBox);
        updatesView.setBottom(new StatisticsView().getStatisticsView());
        return updatesView;
    }

    private HBox getAboutPanel() {
        Label title = (Label) Styler.styleAdd(new Label("Maus 0.1a"), "title");
        Label desc = (Label) Styler.styleAdd(new Label("Maus is a lightweight remote administrative tool " +
                "written in Java \nby a single developer. Maus is intended to present necessary \nfeatures in an attractive and " +
                "easy to use UI."), "label-bright");
        Button checkUpdates = new Button("Check for Updates");
        checkUpdates.setOnMouseClicked(event -> {
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

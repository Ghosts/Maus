package GUI.Views;


import GUI.Components.ClientList;
import GUI.Components.StatisticsView;
import GUI.Components.TopBar;
import GUI.Styler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;

public class MainView {
    public BorderPane getMainView() throws IOException, ClassNotFoundException {
        BorderPane borderPane = new BorderPane();
        borderPane.getStylesheets().addAll(Styler.globalCSS);
        borderPane.getStyleClass().add("root");
        borderPane.setTop(logoArea());
        BorderPane.setAlignment(logoArea(), Pos.CENTER);
        borderPane.setCenter(vContainer(new ClientList().getClientList()));
        borderPane.setBottom(new StatisticsView().getStatisticsView());
        return borderPane;
    }

    private VBox logoArea() {
        Image image = new Image("Resources/images/logolight.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setFitHeight(100);
        return vContainer(new TopBar().getMenuBar(), imageView);
    }


    private VBox vContainer(Node... region) {
        VBox vBox = new VBox();
        HBox.setHgrow(vBox, Priority.ALWAYS);
        for (Node r : region) {
            vBox.getChildren().add(r);
        }
        return vBox;
    }


    private VBox vContainer(Pane pane) {
        VBox vBox = new VBox();
        HBox.setHgrow(vBox, Priority.ALWAYS);
        vBox.getChildren().addAll(pane);
        return vBox;
    }

    private HBox hContainer(Region region) {
        HBox hBox = new HBox();
        HBox.setHgrow(hBox, Priority.ALWAYS);
        hBox.getChildren().addAll(region);
        return hBox;
    }
}

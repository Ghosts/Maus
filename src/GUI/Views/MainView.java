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
        borderPane.getStylesheets().add(Styler.globalCSS);
        borderPane.getStyleClass().add("root");
        borderPane.setTop(logoArea());
        borderPane.setCenter(Styler.vContainer(new ClientList().getClientList()));
        borderPane.setBottom(new StatisticsView().getStatisticsView());
        return borderPane;
    }

    private VBox logoArea() {
        Image image = new Image("Resources/images/logolight.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(50);
        return Styler.vContainer(new TopBar().getMenuBar(), imageView);
    }

}

package GUI.Views;


import GUI.Components.TopBar;
import GUI.Styler;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

class ClientBuilderView {

    BorderPane getClientBuilderView() {
        BorderPane borderPane = new BorderPane();
        borderPane.getStylesheets().add(Styler.globalCSS);
        borderPane.setTop(new TopBar().getTopBar());
        borderPane.setCenter(clientBuilderSettings());
        borderPane.setBottom(new StatisticsView().getStatisticsView());
        return borderPane;
    }

    private HBox clientBuilderSettings() {
        HBox hBox = new HBox();
        hBox.setId("clientBuilder");
        Label title = (Label) Styler.styleAdd(new Label("Client Builder"),"title");
        hBox.getChildren().add(Styler.vContainer(title));
        return hBox;
    }
}

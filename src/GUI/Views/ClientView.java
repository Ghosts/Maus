package GUI.Views;

import GUI.Components.ClientList;
import GUI.Components.StatisticsView;
import GUI.Components.TopBar;
import GUI.Styler;
import javafx.scene.layout.BorderPane;

class ClientView {
    BorderPane getClientView() {
        BorderPane borderPane = new BorderPane();
        borderPane.getStylesheets().add(Styler.globalCSS);
        borderPane.getStyleClass().add("root");
        borderPane.setTop(new TopBar().getTopBar());
        borderPane.setCenter(Styler.vContainer(new ClientList().getClientList()));
        borderPane.setBottom(new StatisticsView().getStatisticsView());
        return borderPane;
    }
}

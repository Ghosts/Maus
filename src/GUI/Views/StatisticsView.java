package GUI.Views;

import GUI.Components.BottomBar;
import GUI.Components.TopBar;
import GUI.Styler;
import Maus.Maus;
import Server.Data.PseudoBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.HashMap;


class StatisticsView {
    private BorderPane statisticsView = new BorderPane();
    private HashMap<String, Integer> operatingSystems = new HashMap<>();

    BorderPane getStatisticsView() {
        statisticsView.getStylesheets().add(getClass().getResource("/css/global.css").toExternalForm());
        HBox hBox = getSystemPanel();
        hBox.setId("statisticsView");
        hBox.setPadding(new Insets(0, 0, 0, 10));
        statisticsView.setTop(new TopBar().getTopBar(Maus.getPrimaryStage()));
        statisticsView.setLeft(hBox);
        statisticsView.setBottom(new BottomBar().getBottomBar());
        return statisticsView;
    }

    private HBox getSystemPanel() {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList();
        PseudoBase.getMausData().forEach((string, clientObject) -> {
            if (clientObject.getSYSTEMOS() != null) {
                if (operatingSystems.containsKey(clientObject.getSYSTEMOS())) {
                    operatingSystems.put(clientObject.getSYSTEMOS(), operatingSystems.get(clientObject.getSYSTEMOS()) + 1);
                } else {
                    operatingSystems.put(clientObject.getSYSTEMOS(), 1);
                }
            }
        });
        operatingSystems.forEach((string, integer) -> {
            pieChartData.add(new PieChart.Data(string, PseudoBase.getMausData().size() / integer));
        });
        final PieChart chart = new PieChart(pieChartData);
        chart.setLegendVisible(false);
        chart.setTitle("Operating Systems");
        chart.setMaxSize(300, 300);
        return Styler.hContainer(Styler.vContainer(10, chart));
    }


}

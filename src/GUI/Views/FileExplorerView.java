package GUI.Views;

import GUI.Components.FileContextMenu;
import GUI.Components.TopBar;
import GUI.Styler;
import Maus.Maus;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class FileExplorerView {

    public static BorderPane getFileExplorerView(String[] files, Stage stage){
        BorderPane borderPane = new BorderPane();
        borderPane.getStylesheets().add(Styler.globalCSS);
        borderPane.setTop(new TopBar().getTopBar(stage));
        borderPane.setCenter(getFileExplorerViewCenter(files));
        borderPane.setBottom(new StatisticsView().getStatisticsView());
        return borderPane;
    }

    private static ScrollPane getFileExplorerViewCenter(String[] files) {
        FlowPane flow = new FlowPane();
        flow.setPadding(new Insets(10, 50, 10, 50));
        flow.getStylesheets().add(Styler.globalCSS);
        flow.setId("file-pane");
        flow.setVgap(10);
        flow.setHgap(10);
        flow.setAlignment(Pos.CENTER);
        HBox icons[] = new HBox[files.length];
        String resourcePath = "Resources/Images/Icons/";
        int rot = 0;
        for (String s : files){
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);
            hBox.setPrefWidth(100);
            hBox.setPrefHeight(100);
            hBox.setId("file-icon");
            hBox.setPadding(new Insets(5,5,5,5 ));
            VBox vBox = new VBox(5);
            vBox.setAlignment(Pos.CENTER);
            Label label;
            label = (Label) Styler.styleAdd(new Label(s), "label-bright");
            label.setWrapText(true);
            Tooltip t = new Tooltip(s);
            Tooltip.install(hBox, t);
            vBox.getChildren().addAll(new ImageView(new Image(resourcePath + getExtensionImage(s))), label);
            hBox.getChildren().add(vBox);
            hBox.setOnMouseClicked(event -> {
                if (event.getButton().equals(MouseButton.SECONDARY)) {
                    FileContextMenu.getFileContextMenu(hBox, s, event);
                }
            });
            icons[rot] = hBox;
            flow.getChildren().add(icons[rot]);
            rot++;
        }
        ScrollPane scroll = new ScrollPane();
        scroll.setId("scroll-pane");
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);    // Horizontal scroll bar
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);    // Vertical scroll bar
        scroll.setContent(flow);
        scroll.viewportBoundsProperty().addListener((ov, oldBounds, bounds) -> {
            flow.setPrefWidth(bounds.getWidth());
            flow.setPrefHeight(bounds.getHeight());
        });
        return scroll;
    }

    private static String getExtensionImage(String s){
        String extension = "";
        int i = s.lastIndexOf('.');
        if (i > 0) {
            extension = s.substring(i+1);
        }
        switch (extension){
            case "":
                return "directory.png";
            case "exe":
                return "exe.png";
            case "gif":
                return "gif.png";
            case ".png":
                return "png.png";
            case "zip":
                return "zip.png";
            case "txt":
                return "txt.png";
            case "rar":
                return "rar.png";
            case "xls":
                return "xls.png";
            case "ppt":
                return "ppt.png";
            case "php":
                return "php.png";
            case "pdf":
                return "pdf.png";
            case "mov":
                return "mov.png";
            case "jpg":
                return "jpg.png";
            case "jpeg":
                return "jpeg.png";
            case "doc":
                return "doc.png";
            case "apk":
                return "apk.png";
            case "css":
                return "css.png";
            default:
                return "file.png";
        }
    }
}

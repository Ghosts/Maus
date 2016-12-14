package GUI.Components;

import GUI.Styler;
import Logger.Level;
import Logger.Logger;
import Maus.Maus;
import Server.ServerSettings;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.HBox;

import java.util.Random;

class OptionBar {

    HBox getMenuBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.getStylesheets().add(Styler.globalCSS);
        menuBar.getStyleClass().add("background");

        Label maus = (Label) Styler.styleAdd(new Label("Maus 0.5a"), "option-button");
        maus.setPadding(new Insets(5, 10, 5, 10));
        maus.setOnMouseClicked(event -> {
            String[] MausEsterEgg = {
                    "Maus 0.5a",
                    "):",
                    "Where's the cheese?",
                    "#NotaRAT",
                    "Please consider donating to Wikipedia",
                    "Du haben keine Freunde",
                    ":)",
                    "Just don't get this shit detected",
                    "Stop clicking here",
                    "*CRASH*",
                    "Whiskers",
                    "BlackShades V.5",
                    "1 bot = 1 prayer",
                    "Why did you click here in the first place?",
                    "Maus only continues if I get community feedback!",
                    "INF3CTED!!11oneone!1oen",
                    "Deditated Wam",
                    "Meow",
                    "Luminosity Maus 1.5",
                    "Spreche sie Deutsche?",
                    "Telegram @Luxington",
                    "Carrier pigeons are faster",
                    "Duct Tape is more stable than this shit"
            };
            Random rn = new Random();
            int rnn = rn.nextInt(MausEsterEgg.length);
            maus.setText(MausEsterEgg[rnn]);
        });

        Label minimize = (Label) Styler.styleAdd(new Label("_"), "option-button");
        minimize.setPadding(new Insets(5, 10, 5, 10));
        minimize.setOnMouseClicked(event -> {
            Maus.getPrimaryStage().setIconified(true);
        });

        Label exit = (Label) Styler.styleAdd(new Label("X"), "option-button");
        exit.setPadding(new Insets(5, 10, 5, 10));
        exit.setOnMouseClicked(event -> {
            if (ServerSettings.isBackgroundPersistent()) {
                Platform.exit();
            } else {
                System.exit(0);
            }
            Logger.log(Level.INFO, "Exit event detected. ");
        });

        HBox sep = Styler.hContainer();
        sep.setId("drag-bar");
        final Delta dragDelta = new Delta();
        sep.setOnMousePressed(mouseEvent -> {
            dragDelta.x = Maus.getPrimaryStage().getX() - mouseEvent.getScreenX();
            dragDelta.y = Maus.getPrimaryStage().getY() - mouseEvent.getScreenY();
        });
        sep.setOnMouseDragged(mouseEvent -> {
            Maus.getPrimaryStage().setX(mouseEvent.getScreenX() + dragDelta.x);
            Maus.getPrimaryStage().setY(mouseEvent.getScreenY() + dragDelta.y);
        });

        HBox hBox = Styler.hContainer(5, maus, sep, minimize, exit);
        hBox.setId("drag-bar");
        return hBox;
    }

    class Delta {
        double x, y;
    }

}

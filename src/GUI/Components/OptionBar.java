package GUI.Components;

import GUI.Controller;
import GUI.Styler;
import Logger.Level;
import Logger.Logger;
import Maus.Maus;
import Server.ServerSettings;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

class OptionBar {

    HBox getMenuBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.getStylesheets().add(Styler.globalCSS);
        menuBar.getStyleClass().add("background");
//        Menu menuOptions = new Menu("Options");
        Label minimize = (Label) Styler.styleAdd(new Label("_"), "option-button");
        minimize.setPadding(new Insets(5,10,5,10));
        minimize.setOnMouseClicked(event -> {
            Maus.getPrimaryStage().setIconified(true);
        });
        Label exit = (Label) Styler.styleAdd(new Label("X"), "option-button");
        exit.setPadding(new Insets(5,10,5,10));
        exit.setOnMouseClicked(event -> {
            if (ServerSettings.isBackgroundPersistent()) {
                Platform.exit();
            } else {
                System.exit(0);
            }
            Logger.log(Level.INFO, "Exit event detected. ");
        });

//        menuBar.getMenus().addAll(menuOptions);
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
        HBox hBox = Styler.hContainer(5, menuBar,sep,minimize,exit);
        hBox.setId("drag-bar");
        return hBox;
    }
    class Delta { double x, y; }

}

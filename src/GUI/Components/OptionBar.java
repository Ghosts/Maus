package GUI.Components;

import GUI.Styler;
import Logger.Level;
import Logger.Logger;
import Server.ServerSettings;
import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

class OptionBar {

    MenuBar getMenuBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.getStylesheets().add(Styler.globalCSS);
        menuBar.getStyleClass().add("background");
        Menu menuOptions = new Menu("Options");
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(actionEvent -> {
            if (ServerSettings.isBackgroundPersistent()) {
                Platform.exit();
            } else {
                System.exit(0);
            }
            Logger.log(Level.INFO, "Exit event detected. ");
        });
        menuOptions.getItems().add(exitMenuItem);
        menuBar.getMenus().addAll(menuOptions);
        return menuBar;
    }
}

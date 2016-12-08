package GUI.Components;

import GUI.Styler;
import GUI.Views.SettingsView;
import Logger.Level;
import Logger.Logger;
import Server.ServerSettings;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class TopBar {

    public MenuBar getMenuBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.getStylesheets().add(Styler.globalCSS);
        menuBar.getStyleClass().add("background");
        Menu menuOptions = new Menu("Options");
        MenuItem settingsMenuItem = new MenuItem("Settings");
        settingsMenuItem.setOnAction(event -> {
            SettingsView.viewOpen = true;
            try {
                Stage stage = new Stage();
                stage.setTitle("Settings");
                stage.setScene(new Scene(new SettingsView().getSettingsView(), 450, 450));
                stage.show();
                stage.setOnCloseRequest(we -> SettingsView.viewOpen = false);
            } catch (IOException | ClassNotFoundException e) {
                Logger.log(Level.ERROR, e.toString());
            }
        });
        if (!SettingsView.viewOpen) {
            menuOptions.getItems().add(settingsMenuItem);
        }

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

        Menu menuControl = new Menu("Control");
        Menu menuView = new Menu("View");
        menuBar.getMenus().addAll(menuOptions, menuControl, menuView);
        return menuBar;
    }
}

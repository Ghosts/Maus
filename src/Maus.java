/**
 * Created by caden on 12/7/2016.
 */

import GUI.Styler;
import GUI.Views.MainView;
import Logger.Level;
import Logger.Logger;
import Server.Data.PseudoBase;
import Server.Server;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Maus extends Application {
    private static Server server =  new Server();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, ClassNotFoundException {
        new PseudoBase().createMausData();
        new PseudoBase().loadData(System.getProperty("user.home") + "/Maus/clients/");

        primaryStage.setTitle("Maus");
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(400);
        Scene mainScene = new Scene(new MainView().getMainView(), 900, 400);
        mainScene.getStylesheets().add(Styler.globalCSS);
        primaryStage.setScene(mainScene);
        Logger.log(Level.INFO, "Maus is running.");
        primaryStage.getIcons().add(new Image("Resources/images/icon.png"));
        primaryStage.show();

        Runnable startServer = server;
        new Thread(startServer).start();
        /* Shutdown Hook to save data on close */
        Runtime.getRuntime().addShutdownHook(
                new Thread("mausData") {
                    @Override
                    public void run() {
                        try {
                            new PseudoBase().writeMausData();
                            Logger.log(Level.INFO, "MausData saved to file. ");
                        } catch (IOException e) {
                            Logger.log(Level.ERROR, e.toString());
                        }
                    }
                });
    }
}

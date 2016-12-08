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
import javafx.stage.Stage;

import java.io.IOException;

public class Maus extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, ClassNotFoundException {
        new PseudoBase().createMausData();
        new PseudoBase().loadData(System.getProperty("user.home") + "/Maus/clients/");
        Runnable server = new Server();
        new Thread(server).start();
        primaryStage.setTitle("Maus");
        Scene mainScene = new Scene(new MainView().getMainView(), 900, 700);
        mainScene.getStylesheets().add(Styler.globalCSS);
        primaryStage.setScene(mainScene);
        primaryStage.show();

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

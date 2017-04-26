package Maus;

import GUI.Views.MainView;
import Logger.Level;
import Logger.Logger;
import Server.Data.PseudoBase;
import Server.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.URL;
import java.nio.channels.FileLock;

public class Maus extends Application {
    private static Stage primaryStage;
    private static Server server = new Server();

    /* Aids in making repeated tests easier. */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        if (lockInstance()) {
            launch(args);
        } else {
            System.exit(0);
        }
    }

    private static boolean lockInstance() {
        try {
            final File file = new File(System.getProperty("user.home") + "/.lock");
            final RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            final FileLock fileLock = randomAccessFile.getChannel().tryLock();
            if (fileLock != null) {
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    try {
                        fileLock.release();
                        randomAccessFile.close();
                        file.delete();
                        /*Before Maus is closed - write Maus data to file (server settings, clients, etc.) */
                        try {
                            PseudoBase.writeMausData();
                            Logger.log(Level.INFO, "MausData saved to file. ");
                        } catch (IOException e) {
                            Logger.log(Level.ERROR, e.toString());
                        }
                    } catch (Exception e) {
                        Logger.log(Level.ERROR, e.toString());
                    }
                }));
                return true;
            }
        } catch (Exception e) {
            Logger.log(Level.ERROR, e.toString());
        }
        return false;
    }

    @Override
    public void start(Stage primaryStage) throws IOException, ClassNotFoundException {
        Maus.primaryStage = primaryStage;
        /* Ensure that the necessary files exist */
        new PseudoBase().createMausData();
        /* Load data from files - including client data, server settings, etc. */
        new PseudoBase().loadData(System.getProperty("user.home") + "/Maus/clients/");
        /* Set up primary view */
        getPrimaryStage().setTitle("Maus 1.0b");

        Scene mainScene = new Scene(new MainView().getMainView(), 900, 500);
        mainScene.getStylesheets().add(getClass().getResource("/css/global.css").toExternalForm());
        getPrimaryStage().setScene(mainScene);
        getPrimaryStage().getIcons().add(new Image(getClass().getResourceAsStream("/Images/Icons/icon.png")));
        getPrimaryStage().setOnCloseRequest(event -> System.exit(0));
        /* Maus is running! */
        Logger.log(Level.INFO, "Maus is running.");
        getPrimaryStage().initStyle(StageStyle.UNDECORATED);

        /* Set user's IP as Server IP */
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = new BufferedReader(new InputStreamReader(
                whatismyip.openStream()));

        String ip = in.readLine();
        ServerSettings.CONNECTION_IP = ip;
        getPrimaryStage().show();

        /* Start the server to listen for client connections. */
        Runnable startServer = server;
        new Thread(startServer).start();
    }
}

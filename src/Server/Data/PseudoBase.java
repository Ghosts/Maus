package Server.Data;


import Logger.Level;
import Logger.Logger;
import Server.ClientObject;
import Server.ServerSettings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.io.*;

public class PseudoBase implements Repository {
    private static ObservableMap<String, ClientObject> mausData = FXCollections.observableHashMap();

    public synchronized static ObservableMap<String, ClientObject> getMausData() {
        return mausData;
    }

    public void createMausData() throws IOException {
        final File parent = new File(System.getProperty("user.home") + "/Maus/clients");
        if (!parent.mkdirs()) {
            Logger.log(Level.WARNING, "Unable to make necessary directories, may already exist.");
        }
        File settings = new File(System.getProperty("user.home") + "/Maus/.serverSettings");
        if (!settings.exists()) {
            boolean create = settings.createNewFile();
        }
        File data = new File(parent, ".mp");
        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(data))) {
            writer.write("");
        } catch (IOException i) {
            Logger.log(Level.ERROR, i.toString());
        }
    }

    public void writeMausData() throws IOException {
        for (ClientObject o : mausData.values()) {
            if (o != null) {
                o.serialize();
            }
        }
        File data = new File(System.getProperty("user.home") + "/Maus.Maus/.serverSettings");
        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(data))) {
            writer.write(ServerSettings.isShowNotifications() + " ");
            writer.write(ServerSettings.isBackgroundPersistent() + " ");
            writer.write(ServerSettings.getRefreshRate() + " ");
            writer.write(ServerSettings.getMaxConnections() + " ");
            writer.write(ServerSettings.isP2pConnections() + " ");
            writer.write(ServerSettings.getPORT() + " ");
        } catch (IOException i) {
            Logger.log(Level.ERROR, i.toString());
        }
    }

    private void loadServerSettings() {
        try (BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.home") + "/Maus/.serverSettings"))
        ) {
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String[] settings = stringBuilder.toString().split(" ");
            if (settings.length == 7) {
                ServerSettings.setShowNotifications(Boolean.getBoolean(settings[0].trim()));
                ServerSettings.setBackgroundPersistent(Boolean.getBoolean(settings[1].trim()));
                ServerSettings.setRefreshRate(Integer.parseInt(settings[2].trim()));
                ServerSettings.setMaxConnections(Integer.parseInt(settings[3].trim()));
                ServerSettings.setP2pConnections(Boolean.getBoolean(settings[4].trim()));
                ServerSettings.setPORT(Integer.parseInt(settings[5].trim()));
            }
        } catch (IOException e) {
            Logger.log(Level.ERROR, e.toString());
        }
    }

    public ObservableMap<String, ClientObject> loadData(String directory) throws IOException, ClassNotFoundException {
        loadServerSettings();
        File folder = new File(directory);
        File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                String file = listOfFile.toString();
                if (file.contains(".client")) {
                    ClientObject o;
                    ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                    o = (ClientObject) in.readObject();
                    CONNECTIONS.put(o.getIP(), o);
                    mausData.put(o.getIP(), o);
                    in.close();
                }
            } else {
                break;
            }
        }
        return mausData;
    }
}

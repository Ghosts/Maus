package Client;


import Logger.Level;
import Logger.Logger;
import Server.Data.Repository;

import java.io.*;
import java.net.Socket;

public class ClientObject implements Serializable, Repository {
    private static int COUNT = 0;
    transient private Socket client;
    private int clientNumber;
    private String nickName;
    private String IP;

    public ClientObject(Socket client, String nickName, String IP) {
        clientNumber = getCOUNT() + 1;
        setCOUNT(getCOUNT() + 1);
        this.client = client;
        this.nickName = nickName;
        this.IP = IP;
    }

    public static int getCOUNT() {
        return COUNT;
    }

    public static void setCOUNT(int counter) {
        COUNT = counter;
    }

    public Socket getClient() {
        return client;
    }

    public void setClient(Socket client) {
        this.client = client;
    }

    public Integer getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(Integer clientNumber) {
        this.clientNumber = clientNumber;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public void serialize() {
        final File parent = new File(System.getProperty("user.home") + "/Maus/clients");
        if (!parent.mkdirs()) {
            Logger.log(Level.WARNING, "Unable to make necessary directories, may already exist.");
        }
        if (getIP() != null) {
            try {
                FileOutputStream fileOut =
                        new FileOutputStream(new File(parent, getNickName() + getIP() + ".client"));

                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(this);
                out.close();
                fileOut.close();
                Logger.log(Level.INFO, "Serialized data is saved in Maus/clients/**.client");
            } catch (IOException i) {
                i.printStackTrace();
            }
        }
    }
}


package Server;


import Logger.Level;
import Logger.Logger;
import Server.Data.Repository;

import java.io.*;
import java.net.Socket;

public class ClientObject implements Serializable, Repository {
    transient private Socket client = new Socket();
    private int clientNumber;
    private String onlineStatus = "Online";
    private String nickName;
    private String IP;
    private transient PrintWriter clientOutput;

    ClientObject(Socket client, String nickName, String IP) {
        this.client = client;
        this.nickName = nickName;
        this.IP = IP;
        try {
            this.clientOutput = new PrintWriter(client.getOutputStream(),true);
        } catch (IOException e) {
            Logger.log(Level.WARNING, "Exception thrown: " + e);
        }
        CONNECTIONS.put(IP, this);
    }

    public void setClientNumber(int clientNumber) {
        this.clientNumber = clientNumber;
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

    public PrintWriter getClientOutput() {
        return clientOutput;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
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

    public void clientCommunicate(String msg) {
        clientOutput.println(msg);
        clientOutput.println(msg);
    }

    @Override
    protected void finalize() throws Throwable {
        clientOutput.close();
        client.close();
    }
}


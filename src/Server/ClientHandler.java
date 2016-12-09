package Server;

import Client.ClientObject;
import GUI.Components.StatisticsView;
import GUI.Controller;
import Logger.Level;
import Logger.Logger;
import Server.Data.PseudoBase;
import Server.Data.Repository;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * ClientHandler: responsible for the correct routing of client on connect and request.
 */
public class ClientHandler implements Runnable, Repository {
    private Socket socket;
    private PrintWriter clientOutput;
    private ClientObject client;

    ClientHandler(Socket socket) {
        this.socket = socket;
    }


    /**
     * Implemented from Runnable, called on Thread creation by unique client connection.
     */
    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream())) {
            clientOutput = out;
            String ip = (((InetSocketAddress) Server.getClient().getRemoteSocketAddress()).getAddress()).toString().replace("/", "");
                client = new ClientObject(socket, "Maus Machine " + ClientObject.getCOUNT(), ip);
                Controller.updateStats();
                Platform.runLater(() -> PseudoBase.getMausData().put(ip, client));
            requestHandler(in);
        } catch (IOException | ClassNotFoundException e) {
            client.setOnlineStatus("Offline");
        }
    }

    private void requestHandler(BufferedReader clientInput) throws IOException, ClassNotFoundException {
        String inp;
        do {
            inp = clientInput.readLine();
            client.clientCommunicate("e");
            if (inp != null) {
                if (inp.equals("1")) {
                    client.clientCommunicate("1");
                } else if (inp.equals("forciblyclose")) {
                    client.clientCommunicate("forciblyclose");
                }
            }
        } while (!inp.equals("forciblyclose")) ;
    }
}
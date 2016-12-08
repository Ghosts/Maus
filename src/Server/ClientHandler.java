package Server;

import Client.ClientObject;
import Logger.Level;
import Logger.Logger;
import Server.Data.PseudoBase;
import Server.Data.Repository;

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
    private final Socket client;
    private PrintWriter clientOutput = null;

    ClientHandler(Socket client) {
        this.client = client;
        String ip = (((InetSocketAddress) Server.getClient().getRemoteSocketAddress()).getAddress()).toString().replace("/", "");
        if (!CONNECTIONS.containsKey(ip)) {
            ClientObject clientObject = new ClientObject(client, "Maus Machine " + ClientObject.getCOUNT(), ip);
            PseudoBase.getMausData().add(clientObject);
        }
    }


    /**
     * Implemented from Runnable, called on Thread creation by unique client connection.
     */
    @Override
    public void run() {
        try (
                BufferedReader clientInput = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter clientOutput = new PrintWriter(client.getOutputStream())
        ) {
            this.clientOutput = clientOutput;
            requestHandler(clientInput);
        } catch (IOException e) {
            Logger.log(Level.WARNING, "IOException thrown: " + e);
        }
    }

    private void requestHandler(BufferedReader clientInput) throws IOException {
        String inp;
        while ((inp = clientInput.readLine()) != null) {
            if ("".equals(inp)) {
                break;
            } else if (inp.contains("ACT")) {
                return;
            } else if (inp.contains("REQ")) {
                return;
            }
        }
    }
}
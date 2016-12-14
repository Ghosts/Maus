package Maus;

import Logger.Level;
import Logger.Logger;
import Server.Data.FileUtils;
import Server.Data.PseudoBase;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class ClientBuilder {
    private String clientName;

    public ClientBuilder(String clientName) {
        this.clientName = clientName;
        try {
            PseudoBase.writeMausData();
        } catch (IOException e) {
            Logger.log(Level.ERROR, e.toString());
        }
    }

    public void run() throws IOException {
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        manifest.getMainAttributes().put(new Attributes.Name("Created-By"), "Maus");
        manifest.getMainAttributes().put(Attributes.Name.MAIN_CLASS, "Client");
        String jarFileName = System.getProperty("user.home") + "/Maus/" + clientName + ".jar";
        File jarFile = new File(jarFileName);
        OutputStream os = new FileOutputStream(jarFile);
        JarOutputStream target = new JarOutputStream(os, manifest);
        ArrayList<String> fileList = new ArrayList<>();
        try {
            String file = FileUtils.ExportResource("/Client/Client.class");
            FileUtils.copyFile(file , System.getProperty("user.home").replace("\\", "/") + "/Maus/Client.class");
        } catch (Exception e) {
            Logger.log(Level.ERROR, e.toString());
        }
        fileList.add(System.getProperty("user.home").replace("\\","/") + "/Maus/Client.class");
        fileList.add(System.getProperty("user.home").replace("\\","/") + "/Maus/.mauscs");
        for (String file : fileList) {
            JarEntry je = new JarEntry(file);
            je.setComment("Client creation.");
            je.setTime(Calendar.getInstance().getTimeInMillis());
            Logger.log(Level.INFO, je.toString());
            target.putNextEntry(je);
            BufferedInputStream is = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[1024];
            while (true)
            {
                int count = is.read(buffer);
                if (count == -1)
                    break;
                target.write(buffer, 0, count);
            }
            target.closeEntry();
            is.close();
        }
        target.close();
        Logger.log(Level.INFO, "Build complete!");
    }
}

package Maus;

import Logger.Level;
import Logger.Logger;
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
            e.printStackTrace();
        }
    }

    public void run() throws IOException {
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        manifest.getMainAttributes().put(new Attributes.Name("Created-By"), "Maus");
        String jarFileName = System.getProperty("user.home") + "/Maus/" + clientName + ".jar";
        JarOutputStream target = null;
        try {
            File jarFile = new File(jarFileName);
            OutputStream os = new FileOutputStream(jarFile);
            target = new JarOutputStream(os, manifest);
        } catch (IOException e) {
            Logger.log(Level.ERROR, e.toString());
        }
        ArrayList<String> fileList = new ArrayList<>();
        String root = System.getProperty("user.home") + "/Maus/";
        fileList.add(ClientBuilder.class.getProtectionDomain().getCodeSource().getLocation() + "Client/Client.class");
        fileList.add(ClientBuilder.class.getProtectionDomain().getCodeSource().getLocation() + "Client/.mauscs");
        int len = 0;
        byte[] buffer = new byte[1024];
        for (String file : fileList) {
            JarEntry je = new JarEntry(file);
            je.setComment("Client creation.");
            je.setTime(Calendar.getInstance().getTimeInMillis());
            Logger.log(Level.INFO, je.toString());
            target.putNextEntry(je);
            InputStream is = new BufferedInputStream(new FileInputStream(file));
            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                target.write(buffer, 0, len);
            }
            is.close();
            target.closeEntry();
        }
        target.close();
        Logger.log(Level.INFO, "Build complete!");
    }


}

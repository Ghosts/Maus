package Server.Data;

import java.io.*;
import Maus.*;
public class FileUtils {

    static public String ExportResource(String resourceName) throws Exception {
        String jarFolder = new File(Maus.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
        try (InputStream stream = Maus.class.getResourceAsStream(resourceName);
             OutputStream resStreamOut = new FileOutputStream(jarFolder + "/Client.class")) {
            if (stream == null) {
                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }

            int readBytes;
            byte[] buffer = new byte[4096];
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return jarFolder + "/Client.class";
    }

    public static void copyFile(String filea, String fileb){
        InputStream inStream = null;
        OutputStream outStream = null;

        try{

            File afile =new File(filea);
            File bfile =new File(fileb);

            inStream = new FileInputStream(afile);
            outStream = new FileOutputStream(bfile);

            byte[] buffer = new byte[1024];

            int length;
            //copy the file content in bytes
            while ((length = inStream.read(buffer)) > 0){

                outStream.write(buffer, 0, length);

            }

            inStream.close();
            outStream.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}

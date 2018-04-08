import java.io.*;

public class MethodList {
    public static void copyFileUsingFileStreams(String sourceStr, String destStr) {

        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(new File(sourceStr));
            output = new FileOutputStream(new File(destStr));
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
            input.close();
            output.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("*********");
                if (input != null) {
                    try {
                        input.close();
                    }catch(Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                if (output != null) {
                    try {
                        output.close();
                    }catch(Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
        }
    }
}


import java.io.*;
import java.util.*;

//Iterates over all the files in a folder
public class FileRead {
    static int countFiles = 0;// The number of files
    static int countFolders = 0;// The number of folders

    public static void readFile(String dirPath, String output, String keyword) {
        Set<String> set = new HashSet<>();
        List<String> repeat = new ArrayList<>();
        File inputFolder = new File(dirPath);// Default directory
        File outputFolder = new File(output);// Default directory
        if (!inputFolder.exists()) {
            System.out.println("Directory does not exist:" + inputFolder.getAbsolutePath());
            return;
        }
        if (!outputFolder.exists()) {
            try {
                outputFolder.mkdir();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }
        }
        File[] result = searchJarFile(inputFolder, keyword);// Get the array of files
        System.out.println(" Find the key word \"" + keyword + "\"" + "In " + inputFolder + " and all the subfiles.");
        System.out.println("find" + countFiles + " files，" + countFolders + " folder. " + result.length + " eligible documents:");
        for (int i = 0; i < result.length; i++) {// Looping display files
            File file = result[i];
            if (set.contains(file.getName())) {
                repeat.add(file.getAbsolutePath());
                FIleMove.copyFileUsingFileStreams(file.getAbsolutePath(), output + "\\reapeat_" + file.getName());
            } else {
                FIleMove.copyFileUsingFileStreams(file.getAbsolutePath(), output + "\\" + file.getName());
            }
            set.add(file.getName());
        }
        System.out.println("repeat!!!");
        for (String str : repeat) {
            System.out.println(str);
        }
    }

    public static File[] searchJarFile(File folder, final String keyWord) {// Recursively find files containing keywords

        File[] subFolders = folder.listFiles(new FileFilter() {// Use internal anonymous classes to get files
            @Override
            public boolean accept(File pathname) {// Implementing the accept method of the FileFilter
                if (pathname.isFile()) {// If it is a file
                    countFiles++;
                } else {// If it is a folder
                    countFolders++;
                }
                if (pathname.isDirectory()
                        || (pathname.isFile() && judgeStandardEnd(pathname.getName().toLowerCase(), keyWord.toLowerCase()))) {// it is a Directory or file contains keywords
                    return true;
                } else {
                    return false;
                }
            }
        });

        List<File> result = new ArrayList<File>();
        for (int i = 0; i < subFolders.length; i++) {// Looping display folders or files
            if (subFolders[i].isFile()) {// If it is a file then add the file to the result list
                result.add(subFolders[i]);
            } else {// If it is a folder, call this method recursively and then add all the files to the result list
                File[] foldResult = searchJarFile(subFolders[i], keyWord);
                for (int j = 0; j < foldResult.length; j++) {// Looping display files
                    result.add(foldResult[j]);// Save the file to a collection
                }
            }
        }

        File files[] = new File[result.size()];// Declaration file array, length is the length of the collection
        result.toArray(files);// Collection Array
        return files;
    }

    private static boolean judgeStandardEnd(String fileName, String keyWord) {
        return fileName.endsWith(keyWord);
    }


    public static byte[] getBytesFromFile(String filePath) {
        File file = new File(filePath);
        byte[] ret = null;
        try {
            if (file == null) {
                // log.error("helper:the file is null!");
                return null;
            }
            FileInputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
            byte[] b = new byte[4096];
            int n;
            while ((n = in.read(b)) != -1) {
                out.write(b, 0, n);
            }
            in.close();
            out.close();
            ret = out.toByteArray();
        } catch (IOException e) {
            // log.error("helper:get bytes from file process error!");
            e.printStackTrace();
        }
        return ret;
    }

    // Traversal folders without recursive
    public void traverseFolder1(String path) {
        int fileNum = 0, folderNum = 0;
        File file = new File(path);
        if (file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    System.out.println("文件夹:" + file2.getAbsolutePath());
                    list.add(file2);
                    fileNum++;
                } else {
                    System.out.println("文件:" + file2.getAbsolutePath());
                    folderNum++;
                }
            }
            File temp_file;
            while (!list.isEmpty()) {
                temp_file = list.removeFirst();
                files = temp_file.listFiles();
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        System.out.println("文件夹:" + file2.getAbsolutePath());
                        list.add(file2);
                        fileNum++;
                    } else {
                        System.out.println("文件:" + file2.getAbsolutePath());
                        folderNum++;
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
        System.out.println("文件夹共有:" + folderNum + ",文件共有:" + fileNum);
    }

    // Traversal folders wit recursive

    public void traverseFolder2(String path) {

        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                System.out.println("The directory is empty!!!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        System.out.println("directory: " + file2.getAbsolutePath());
                        traverseFolder2(file2.getAbsolutePath());
                    } else {
                        System.out.println("file: " + file2.getAbsolutePath());
                    }
                }
            }
        } else {
            System.out.println("The Folder does not exist!!!");
        }
    }

    /**
     * The following method is the sample of reading string to a file.
     */

    /**
     * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
     * 当然也是可以读字符串的。
     */
    /* 貌似是说网络环境中比较复杂，每次传过来的字符是定长的，用这种方式？*/
    public static String readString1(String FILE_IN)

    {

        try

        {

            //FileInputStream 用于读取诸如图像数据之类的原始字节流。要读取字符流，请考虑使用 FileReader。

            FileInputStream inStream = new FileInputStream(new File(FILE_IN));

            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];

            int length = -1;

            while ((length = inStream.read(buffer)) != -1)

            {

                bos.write(buffer, 0, length);

                // .write方法 SDK 的解释是 Writes count bytes from the byte array buffer starting at offset index to this stream.

                //  当流关闭以后内容依然存在

            }

            bos.close();

            inStream.close();

            return bos.toString();

            // 为什么不一次性把buffer得大小取出来呢？为什么还要写入到bos中呢？ return new(buffer,"UTF-8") 不更好么?

            // return new String(bos.toByteArray(),"UTF-8");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
// 有人说了 FileReader  读字符串更好，那么就用FileReader吧

    // 每次读一个是不是效率有点低了？

    private static String readString2(String FILE_IN)

    {

        StringBuffer str = new StringBuffer("");

        File file = new File(FILE_IN);

        try {

            FileReader fr = new FileReader(file);

            int ch = 0;

            while ((ch = fr.read()) != -1)

            {

                System.out.print((char) ch + " ");

            }

            fr.close();

        } catch (IOException e) {

            // TODO Auto-generated catch block

            e.printStackTrace();

            System.out.println("File reader出错");

        }

        return str.toString();

    }
    /*按字节读取字符串*/

    /* 个人感觉最好的方式，（一次读完）读字节就读字节吧，读完转码一次不就好了*/

    private static String readString3(String FILE_IN)

    {

        String str = "";

        File file = new File(FILE_IN);

        try {

            FileInputStream in = new FileInputStream(file);

            // size  为字串的长度 ，这里一次性读完

            int size = in.available();

            byte[] buffer = new byte[size];

            in.read(buffer);

            in.close();

            str = new String(buffer, "GB2312");

        } catch (IOException e) {

            // TODO Auto-generated catch block

            e.printStackTrace();
        }

        return str;

    }
    /*InputStreamReader+BufferedReader读取字符串  ， InputStreamReader类是从字节流到字符流的桥梁*/

    /* 按行读对于要处理的格式化数据是一种读取的好方式 */

    private static String readString4(String FILE_IN)

    {

        int len = 0;

        StringBuffer str = new StringBuffer("");

        File file = new File(FILE_IN);

        try {

            FileInputStream is = new FileInputStream(file);

            InputStreamReader isr = new InputStreamReader(is);

            BufferedReader in = new BufferedReader(isr);

            String line = null;

            while ((line = in.readLine()) != null)

            {

                if (len != 0)  // 处理换行符的问题

                {

                    str.append("\r\n" + line);

                } else

                {

                    str.append(line);

                }

                len++;

            }

            in.close();

            is.close();

        } catch (IOException e) {

            // TODO Auto-generated catch block

            e.printStackTrace();

        }

        return str.toString();

    }

}


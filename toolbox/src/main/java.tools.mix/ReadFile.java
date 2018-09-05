
import java.io.*;
import java.util.*;

//Iterates over all the files in a folder
public class ReadFile {
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
                MethodList.copyFileUsingFileStreams(file.getAbsolutePath(), output + "\\reapeat_" + file.getName());
            } else {
                MethodList.copyFileUsingFileStreams(file.getAbsolutePath(), output + "\\" + file.getName());
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

}


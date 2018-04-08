public class ARun {
    public static void main(String[] args) {
        String dirPath = "C:\\Program Files\\lucene-7.3.0";
        String keyword = ".jar";
        String outputDir = "D:\\backup";
        ReadFile.readFile(dirPath, outputDir, keyword);
    }
}

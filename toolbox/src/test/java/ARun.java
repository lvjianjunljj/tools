import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ARun {
    public static void main(String[] args) {
        int blobDataEachLen = 64;


//        String jsonFilePath = "D:\\data\\company work\\PDI\\test data\\3";
//        String binaryFilePath = "D:\\data\\company work\\PDI\\test data\\3_binary";
//        JSONObject json = JsonOperation.getJsonFromFile(jsonFilePath);
//        JSONArray dataBlob = json.getJSONArray("m_dataBlob");
//        byte[][] inputData = new byte[dataBlob.size()][];
//        for (int i = 0; i < dataBlob.size(); i++) {
//            String str = dataBlob.get(i).toString();
//            inputData[i] = HexConvertTool.hexStringToBytes(str);
//        }
//        FileWrite.writeFile(binaryFilePath, inputData);
//        byte[] blobByteData = FileRead.getBytesFromFile(binaryFilePath);
//        String blobHexStringData = HexConvertTool.bytesToHexString(blobByteData);
//        for (int i = 0; i < blobHexStringData.length(); i += blobDataEachLen) {
//            System.out.println(blobHexStringData.substring(i, Math.min(i + blobDataEachLen, blobHexStringData.length())));
//        }


        String inputDirPath = "D:\\data\\company work\\PDI\\test data\\dui doc data";
        String outputDirPath = "D:\\data\\company work\\PDI\\test data\\dui doc data binary";

        File inputDir = new File(inputDirPath);
        File[] fileList = inputDir.listFiles();
        for (File f : fileList) {
            JSONObject json = JsonOperation.getJsonFromFile(f.getAbsoluteFile().toString());
            JSONArray dataBlob = json.getJSONArray("m_dataBlob");
            String filePath = outputDirPath + "\\" + f.getName();
            File file = new File(filePath);
            if (file.exists()) {
                continue;
            }
            byte[][] inputData = new byte[dataBlob.size()][];
            for (int i = 0; i < dataBlob.size(); i++) {
                String str = dataBlob.get(i).toString();
                inputData[i] = HexConvertTool.hexStringToBytes(str);
            }
            FileWrite.writeFile(filePath, inputData);
        }
    }


}
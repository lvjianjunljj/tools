import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

/**
 * @author Jianjun Lv
 * @date 9/14/2018 10:54 PM
 */
public class HexConvertTool {
    //switch0:bfaab9d8c1bfb1a8beaf
    private static String hexString = "0123456789ABCDEF";

//    public static void main(String[] args) throws UnsupportedEncodingException {
//        System.out.println(encode(new String("开关量报警".getBytes("gb2312"), "gb2312")));
//        System.out.println(decode("BFAAB9D8C1BFB1A8BEAF"));
//    }

    /*
     * 将字符串编码成16进制数字,适用于所有字符（包括中文）
     */
    public static String encode(String str) throws UnsupportedEncodingException {
        // 根据默认编码获取字节数组
        byte[] bytes = str.getBytes("gb2312");
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        // 将字节数组中每个字节拆解成2位16进制整数
        for (int i = 0; i < bytes.length; i++) {
            sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
            sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
        }
        return sb.toString();
    }

    /*
     * 将16进制数字解码成字符串,适用于所有字符（包括中文）
     */
    public static String decode(String bytes) throws UnsupportedEncodingException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
        // 将每2位16进制整数组装成一个字节
        for (int i = 0; i < bytes.length(); i += 2)
            baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
                    .indexOf(bytes.charAt(i + 1))));
        return new String(new String(baos.toByteArray(), "gb2312").getBytes("utf-8"), "utf-8");
    }

    public static byte[] strToByteArray(String str) {
        if (str == null) {
            return null;
        }
        byte[] byteArray = str.getBytes();
        return byteArray;
    }


    /*
     * 将16进制的字符串装换为对应的byte数组，例如"A5000C5A81000000000000000000010E90AA" 转换为对应的数组形式
     *
     * @param hexString
     * @return 转换后的数组
     */
    public static byte[] hexStringToBytes(String hexStr) {
        if (hexStr.isEmpty()) {
            return null;
        }
        hexStr = hexStr.toUpperCase();
        int length = hexStr.length() / 2;
        char[] hexChars = hexStr.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    public static String bytesToHexString(byte[] byteData) {
        char[] charList = new char[byteData.length * 2];
        for (int i = 0; i < byteData.length; i++) {
            //Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值
            charList[i * 2] = hexString.charAt((byteData[i] & 0xFF) >> 4);
            charList[i * 2 + 1] = hexString.charAt(byteData[i] & 0xF);
        }
        return new String(charList);
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

}

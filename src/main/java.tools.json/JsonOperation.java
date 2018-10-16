import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * @author Jianjun Lv
 * @date 9/15/2018 11:56 PM
 */


public class JsonOperation {
    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("msg", "yes");//map里面装有yes
        JSONObject jsonObject = JSONObject.fromObject(map);
        //将json对象转化为json字符串
        String result = jsonObject.toString();
        System.out.println(result);
    }

    public static JSONObject getJsonFromFile(String filePath) {
        String fileContent = FileRead.readString1(filePath);
        JSONObject jsonObject = JSONObject.fromObject(fileContent);
        return jsonObject;
    }
}
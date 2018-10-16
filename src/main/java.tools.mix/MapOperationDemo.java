/**
 * @author Jianjun Lv
 * @date 10/16/2018 12:01 AM
 */

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class MapOperationDemo {
    /**
     * @param map
     * @return
     */
    public static Map<Object, Object> sort(Map<Object, Object> map) {
        Map<Object, Object> mapVK = new TreeMap<Object, Object>(
                new Comparator<Object>() {
                    public int compare(Object obj1, Object obj2) {
                        String v1 = (String) obj1;
                        String v2 = (String) obj2;
                        // 比较大小
                        int s = v2.compareTo(v1);
                        return s;
                    }
                });
        Set<Object> col = map.keySet();
        Iterator<Object> iter = col.iterator();
        while (iter.hasNext()) {
            Object key = iter.next();
            Object value = map.get(key);
            mapVK.put(key, value);
        }
        return mapVK;
    }

    /**
     * @param args
     */
    public static void mainMethod(String[] args) {
        // 1.create a Map object
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("08", new Object());
        map.put("03", new Object());
        map.put("04", new Object());
        map.put("15", new Object());
        // 2. Sort Map
        Map<Object, Object> sortMap = sort(map);
        // 3. Traverse the Map before sorting
        Set<Object> keySet = map.keySet();
        for (Object key : keySet) {
            System.out.println(key + "=" + sortMap.get(key));
        }
        System.out.println("------------------------------------");
        // 4. Traverse the Map after sorting
        Set<Object> keySet2 = sortMap.keySet();
        for (Object key : keySet2) {
            System.out.println(key + "=" + sortMap.get(key));
        }
    }

    /**
     The output is:
     03=java.lang.Object@2aafb23c
     04=java.lang.Object@2b80d80f
     15=java.lang.Object@3ab39c39
     08=java.lang.Object@2eee9593
     ------------------------------------
     15=java.lang.Object@3ab39c39
     08=java.lang.Object@2eee9593
     04=java.lang.Object@2b80d80f
     03=java.lang.Object@2aafb23c


     */
}

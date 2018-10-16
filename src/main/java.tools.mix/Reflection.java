import java.lang.reflect.Method;
import java.io.*;
public class Reflection {
    public static void reflectionMethod() {
        try {
            Class<?> clazz = Class.forName("Reflection");
            // calling a method of a class 1
            Method method = clazz.getMethod("reflect1");
            method.invoke(clazz.newInstance());
            //calling a method of a class 2
            method = clazz.getMethod("reflect2", int.class, String.class);
            method.invoke(clazz.newInstance(), 20, "Tom");
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void reflect1() {
        System.out.println("Java reflection mechanism - calling a method of a class 1");
    }
    public void reflect2(int age, String name) {
        System.out.println("Java reflection mechanism - calling a method of a class 2");
        System.out.println("age -> " + age + ". name -> " + name);
    }
}

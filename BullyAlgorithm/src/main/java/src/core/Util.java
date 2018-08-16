package src.core;

public class Util {

    public static void print(Object message, boolean bool){
        if(bool){
            System.out.println(String.valueOf(message));
        }
    }

    public static void print(Object message){
        print(message, false);
    }
}

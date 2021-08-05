package helper;

public class Utility {
    public static int randomInteger(int min, int max) {
        return ((int) (Math.random() * (max - min))) + min;
    }
}

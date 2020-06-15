package utils.OOM;

public class StackOverflowErrorDemo {
    public static void main(String[] args) {
        stackOverflowError();
    }

    private static void stackOverflowError(){
        //Exception in thread "main" java.lang.StackOverflowError
        stackOverflowError();
    }
}

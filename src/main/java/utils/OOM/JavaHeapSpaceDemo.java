package utils.OOM;

public class JavaHeapSpaceDemo {
    public static void main(String[] args) {
        //-Xms10m -Xmx10m
        //JVM堆的初始值和最大值都是10m，而下面直接new 了一个80m 的对象，所以堆直接撑爆了
        //Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
        byte[] bytes = new byte[80 * 1024 * 1024];
    }
}

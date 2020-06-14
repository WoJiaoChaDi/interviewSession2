package utils.GC_Dir;

public class HelloGC {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("****Hello GC*****");

        //Thread.sleep(Integer.MAX_VALUE);

        //配置：-Xms10m -Xmx10m -XX:+PrintGCDetails
        //直接将其撑爆
        byte[] byteArray = new byte[50 * 1024 * 1024];
    }
}

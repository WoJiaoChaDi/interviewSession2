package utils.current;

public class SingletonVolatileDCL_Demo3 {

    private static volatile SingletonVolatileDCL_Demo3 instance=null;
    private SingletonVolatileDCL_Demo3(){
        System.out.println(Thread.currentThread().getName()+"\t 构造方法");
    }

    //如果在静态方法上面加synchronized，太重了，会锁住一整个类
    //public static synchronized SingletonVolatileDCL_Demo3 getInstance(){

    //所以使用DCL（Double Check Lock 双端检测锁机制）
    public static SingletonVolatileDCL_Demo3 getInstance(){
        //加锁前后都加锁
        if(instance==null){
            synchronized (SingletonVolatileDCL_Demo3.class){
                //加锁前后都加锁
                if(instance==null){
                    instance=new SingletonVolatileDCL_Demo3();
                }
            }
        }
        return instance;
    }

    //public static void main(String[] args) {
    //    //单线程中，单例模式成功
    //    System.out.println(SingletonVolatileDCL_Demo3.getInstance() == SingletonVolatileDCL_Demo3.getInstance());
    //    System.out.println(SingletonVolatileDCL_Demo3.getInstance() == SingletonVolatileDCL_Demo3.getInstance());
    //    System.out.println(SingletonVolatileDCL_Demo3.getInstance() == SingletonVolatileDCL_Demo3.getInstance());
    //}

    public static void main(String[] args) {
        //在多线程中，这种单例模式会创建多个实例
        for (int i = 1; i <=10; i++) {
            new Thread(() ->{
                System.out.println(SingletonVolatileDCL_Demo3.getInstance() == SingletonVolatileDCL_Demo3.getInstance());
            },String.valueOf(i)).start();
        }
    }
}

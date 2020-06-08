package utils.current;

public class SingletonDCL_Demo2 {

    private static SingletonDCL_Demo2 instance=null;
    private SingletonDCL_Demo2(){
        System.out.println(Thread.currentThread().getName()+"\t 构造方法");
    }

    //如果在静态方法上面加synchronized，太重了，会锁住一整个类
    //public static synchronized SingletonDCL_Demo2 getInstance(){

    //所以使用DCL（Double Check Lock 双端检测锁机制）
    public static SingletonDCL_Demo2 getInstance(){
        //加锁前后都加锁
        if(instance==null){
            synchronized (SingletonDCL_Demo2.class){
                //加锁前后都加锁
                if(instance==null){
                    instance=new SingletonDCL_Demo2();
                }
            }
        }
        return instance;
    }

    //public static void main(String[] args) {
    //    //单线程中，单例模式成功
    //    System.out.println(SingletonDCL_Demo2.getInstance() == SingletonDCL_Demo2.getInstance());
    //    System.out.println(SingletonDCL_Demo2.getInstance() == SingletonDCL_Demo2.getInstance());
    //    System.out.println(SingletonDCL_Demo2.getInstance() == SingletonDCL_Demo2.getInstance());
    //}

    public static void main(String[] args) {
        //在多线程中，这种单例模式会创建多个实例
        for (int i = 1; i <=10; i++) {
            new Thread(() ->{
                System.out.println(SingletonDCL_Demo2.getInstance() == SingletonDCL_Demo2.getInstance());
            },String.valueOf(i)).start();
        }
    }
}

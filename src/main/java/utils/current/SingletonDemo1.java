package utils.current;

public class SingletonDemo1 {

    private static volatile SingletonDemo1 instance=null;
    private SingletonDemo1(){
        System.out.println(Thread.currentThread().getName()+"\t 构造方法");
    }

    public static SingletonDemo1 getInstance(){
        if(instance==null){
            instance=new SingletonDemo1();
        }
        return instance;
    }

    public static void main(String[] args) {
        //单线程中，单例模式成功
        System.out.println(SingletonDemo1.getInstance() == SingletonDemo1.getInstance());
        System.out.println(SingletonDemo1.getInstance() == SingletonDemo1.getInstance());
        System.out.println(SingletonDemo1.getInstance() == SingletonDemo1.getInstance());
    }

    //public static void main(String[] args) {
    //    //在多线程中，这种单例模式会创建多个实例
    //    for (int i = 1; i <=10; i++) {
    //        new Thread(() ->{
    //            System.out.println(SingletonDemo1.getInstance() == SingletonDemo1.getInstance());
    //        },String.valueOf(i)).start();
    //    }
    //}
}


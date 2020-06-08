package utils.current;

class MyData{
    volatile int number = 0;

    public void addNumber(int num){
        this.number = num;
    }
}

public class volatileDemo {

    public static void main(String[] args) {

        MyData myData = new MyData();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "-number:" + myData.number);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addNumber(20);
        } , "A").start();

        while (myData.number == 0 ){
            // 问：即使没有保证可见性的措施，很多时候共享变量依然能够在主内存和工作内存中及时的更新
            // 一般只有在短时间内高并发的情况下才会出现变量得不到及时更新的情况，因为cpu在执行时会很快的刷新缓存，所以一般情况下很难看到这种问题。
            // 所以在while里面添加如下的任意一条语句，依然有机会获取主存中的值
            // System.out.println();
        }

        System.out.println(Thread.currentThread().getName() + "-number:" + myData.number);
    }

}

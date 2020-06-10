package utils.current.lock.BlockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyResource {

    //默认开启 进行生产消费的交互
    private volatile boolean flag = true;

    //默认值是0
    private AtomicInteger atomicInteger = new AtomicInteger();

    //先不固定BlckingQueue的类型，用构造方法来传入接口参数确定
    private BlockingQueue<String> blockingQueue = null;

    public MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        //记录传参的参数，方便日后查错
        System.out.println("传入的阻塞队列类型：" + blockingQueue.getClass().getName());
    }

    /**
     * 生产者
     */
    public void myProd() throws Exception {
        String data = null;
        boolean returnValue;
        while (flag) {
            data = atomicInteger.incrementAndGet() + "";
            returnValue = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if (returnValue) {
                System.out.println(Thread.currentThread().getName() + "\t 插入队列数据" + data + "成功");
            } else {
                System.out.println(Thread.currentThread().getName() + "\t 插入队列数据" + data + "失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName() + "\t 停止，表示flag=" + flag + "，生产动作结束！");
    }

    /**
     * 消费者
     */
    public void myConsumer() throws Exception {
        String result = null;
        while (flag) {
            result = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if (null == result || "".equalsIgnoreCase(result)) {
                flag = false;
                System.out.println(Thread.currentThread().getName() + "\t" + "超过2秒没有取到消息，消费退出");
                return;
            }
            System.out.println(Thread.currentThread().getName() + "\t消费队列" + result + "成功");

        }
    }

    /**
     * 线程终止方法
     */
    public void stop() throws Exception {
        flag = false;
    }
}

/**
 * volatile/CAS/atomicInteger/BlockQueue/线程交互/原子引用
 **/
public class ProdConsumerBlockQueueDemo {
    public static void main(String[] args) throws Exception {

        //此处的构造方法确定用什么阻塞队列
        MyResource myResource = new MyResource(new ArrayBlockingQueue<>(10));

        //生产线程
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t生产线程启动");
            try {
                myResource.myProd();
                System.out.println("Prod---End");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "Prod").start();

        //消费线程
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t消费线程启动");
            try {
                myResource.myConsumer();
                System.out.println("Consumer---End");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "Consumer").start();


        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("5秒钟时间到,停止活动");
        myResource.stop();
    }
}
         
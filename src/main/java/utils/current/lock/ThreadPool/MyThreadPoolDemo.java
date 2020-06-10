package utils.current.lock.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 4种获取java多线程的方式
 *  Thread
 *  Runnable
 *  Callable
 *  线程池
 */
public class MyThreadPoolDemo {
    public static void main(String[] args) {

        //查看底层cpu是多少线程
        System.out.println(Runtime.getRuntime().availableProcessors());

        //Array         Arrays
        //Collection    Collections
        //Executor      Executors

        //ExecutorService threadPool = Executors.newFixedThreadPool(5);//一池5个线程。
        //ExecutorService threadPool = Executors.newSingleThreadExecutor();//一池1个线程。
        ExecutorService threadPool = Executors.newCachedThreadPool();//一池N个线程
        //上面这三个new的底层都是 new ThreadPoolExecutor()：
        //ThreadPoolExecutor(int corePoolSize,
        //                        int maximumPoolSize,
        //                        long keepAliveTime,
        //                        TimeUnit unit,
        //                        BlockingQueue<Runnable> workQueue)

        //模拟20个用户来办理业务，每个用户就是一个来自外部的请求线程
        try {
            for (int i = 0; i < 20; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
                //暂停一会儿（newCachedThreadPool，只用了一个线程，如果没有暂停，则会有多个线程）
                //TimeUnit.MILLISECONDS.sleep(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭线程池
            threadPool.shutdown();
        }

    }
}

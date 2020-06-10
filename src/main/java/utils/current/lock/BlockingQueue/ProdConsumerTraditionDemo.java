package utils.current.lock.BlockingQueue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 共享资源类
 */
class ShareData {
    private int num = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    /**
     * 生产者
     */
    public void increment() throws Exception {
        lock.lock();
        try {
            //1 判断
            while (num != 0) {
                //等待 不生产
                //如果是最老版本，用synchronized锁方法的话，用wait()
                //this.wait();
                condition.await();
            }
            //2 干活
            num++;
            System.out.println(Thread.currentThread().getName() + "\t" + num);

            //3 通知唤醒
            //如果是最老版本，用synchronized锁方法的话，用notifyAll()
            //this.notifyAll();
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 消费者
     */
    public void deIncrement() throws Exception {
        lock.lock();
        try {
            //1 判断
            while (num == 0) {
                //等待 消费
                //如果是最老版本，用synchronized锁方法的话，用wait()
                //this.wait();
                condition.await();
            }
            //2 干活
            num--;
            System.out.println(Thread.currentThread().getName() + "\t" + num);

            //3 通知唤醒
            //如果是最老版本，用synchronized锁方法的话，用notifyAll()
            //this.notifyAll();
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}

/**
 * Description
 * 一个初始值为0的变量 两个线程交替操作 一个加1 一个减1来5轮
 *
 * 1    线程  操作(方法)    资源类
 * 2    判断  干活         通知
 * 3    防止虚假唤醒机制（超过两个线程，用if就会出现虚假环境问题，所以必须用while）
 **/
public class ProdConsumerTraditionDemo {
    public static void main(String[] args) {
        ShareData shareData = new ShareData();
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    shareData.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "AA").start();
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    shareData.deIncrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "BB").start();
    }
}
         

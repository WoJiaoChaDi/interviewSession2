package utils.current.reenter.test2;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Phone implements Runnable {
    private Lock lock = new ReentrantLock();

    @SneakyThrows
    @Override
    public void run() {
        get();
    }

    private void get() throws InterruptedException {
        lock.lock();
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\tget");
            set();
        } finally {
            lock.unlock();
            lock.unlock();
        }
    }

    private void set() throws InterruptedException {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\tset");
            TimeUnit.SECONDS.sleep(3);
        } finally {
            lock.unlock();
        }
    }
}

/**
 * Description:
 * 可重入锁(也叫做递归锁)
 * 指的是同一先生外层函数获得锁后,内层敌对函数任然能获取该锁的代码
 * 在同一线程外外层方法获取锁的时候,在进入内层方法会自动获取锁
 * <p>
 * 也就是说,线程可以进入任何一个它已经标记的锁所同步的代码块
 *
 * @author veliger@163.com
 * @date 2019-04-12 23:36
 **/
public class ReenterLockDemo2 {
    /**
     * Thread-0 get
     * Thread-0 set
     * Thread-1 get
     * Thread-1 set
     *
     * @param args
     */
    public static void main(String[] args) {
        Phone phone = new Phone();
        Thread t3 = new Thread(phone);
        Thread t4 = new Thread(phone);
        t3.start();
        t4.start();

    }
}


         
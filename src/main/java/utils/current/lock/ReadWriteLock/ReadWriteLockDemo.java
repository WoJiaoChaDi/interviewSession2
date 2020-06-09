package utils.current.lock.ReadWriteLock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

class MyCache//资源类
{
    private volatile Map<String, Object> map = new HashMap<>();

    public void put(String key, Object value) {
        System.out.println(Thread.currentThread().getName() + "\t 正在写入" + key);

        //暂停一会儿线程吗，模拟网络拥堵
        try { TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }

        map.put(key, value);
        System.out.println(Thread.currentThread().getName() + "\t 写入完成" + key);
    }

    public void get(String key) {

        System.out.println(Thread.currentThread().getName() + "\t 正在读取：");

        //暂停一会儿线程吗，模拟网络拥堵
        try { TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }

        Object result = map.get(key);
        System.out.println(Thread.currentThread().getName() + "\t 读取完成" + result);
    }
}

/**
 * 多个线程同时读一个资源类没有任何问题，所以为了满足高并发量，读取共享资源应该可能同时进行。
 * 但是，如果有一个线程想去写共享资源，就不应该再有其他线程可以对该资源进行读或写
 *
 * 小总结：
 *      读-读共存
 *      读-写不能共存
 *      写-写不能共存
 */
public class ReadWriteLockDemo {

    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        for (int i = 0; i < 5; i++) {

            final int tempInt = i;
            new Thread(() -> {
                myCache.put(tempInt + "", tempInt + "");
            }, String.valueOf(i)).start();
        }

        for (int i = 0; i < 5; i++) {
            final int tempInt = i;
            new Thread(() -> {
                myCache.get(tempInt + "");
            }, String.valueOf(i)).start();
        }

        //输出：写的操作被打断了，非独占的
        //0	 正在写入0
        //1	 正在写入1
        //4	 正在写入4
        //2	 正在写入2
        //3	 正在写入3
        //1	 正在读取：
        //2	 正在读取：
        //0	 正在读取：
        //3	 正在读取：
        //4	 正在读取：
        //4	 写入完成4
        //1	 写入完成1
        //0	 写入完成0
        //1	 读取完成1
        //3	 写入完成3
        //2	 写入完成2
        //2	 读取完成null
        //4	 读取完成4
        //0	 读取完成0
        //3	 读取完成3
    }
}

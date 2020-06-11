package utils.current.lock.DeadLock;

import java.util.concurrent.TimeUnit;

class HoldLockThead implements Runnable{

    private String lockA;
    private String lockB;

    public HoldLockThead(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t 自己持有：" + lockA + "\t 尝试获得：" + lockB);

            try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }

            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t 自己持有：" + lockB + "\t 尝试获得：" + lockA);
            }
        }
    }
}
/**
 * 死锁是指两个或两个以上的进程在执行过程中，因争夺资源而造成的一种互相等待的现象。
 * 若无外力干涉，那它们都将无法推进下去
 */
public class DeadLockDemo {
    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";

        new Thread(new HoldLockThead(lockA, lockB), "AAA").start();
        new Thread(new HoldLockThead(lockB, lockA), "BBB").start();

        /**
         * linux    ps -ef|grep xxxx    ls -l
         *
         * windows下的java运行程序，也有类似ps的查看进程的命令，但是目前我们需要查看的只是java
         *          jps = java ps       jps -l
         *
         * jps -l
         * jstack 9636      查看catch的那个e.printStackTrace()
         */

//        Found one Java-level deadlock:
//        =============================
//        "BBB":
//        waiting to lock monitor 0x000000001d5a0c08 (object 0x000000076b994320, a java.lang.String),
//        which is held by "AAA"
//        "AAA":
//        waiting to lock monitor 0x000000001d5a3548 (object 0x000000076b994358, a java.lang.String),
//        which is held by "BBB"
//
//        Java stack information for the threads listed above:
//        ===================================================
//        "BBB":
//        at utils.current.lock.DeadLock.HoldLockThead.run(DeadLockDemo.java:23)
//        - waiting to lock <0x000000076b994320> (a java.lang.String)
//        - locked <0x000000076b994358> (a java.lang.String)
//        at java.lang.Thread.run(Thread.java:748)
//        "AAA":
//        at utils.current.lock.DeadLock.HoldLockThead.run(DeadLockDemo.java:23)
//        - waiting to lock <0x000000076b994358> (a java.lang.String)
//        - locked <0x000000076b994320> (a java.lang.String)
//        at java.lang.Thread.run(Thread.java:748)
//
//        Found 1 deadlock.
    }
}

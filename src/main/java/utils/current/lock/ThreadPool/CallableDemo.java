package utils.current.lock.ThreadPool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Runnable 和 Callable区别：
 *      Runnable 没有返回值，没有异常
 *      Callable 有返回值，有异常
 */
class MyThreadRunnable implements Runnable{
    @Override
    public void run() {

    }
}

class MyThreadCallable implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + "进入了Callable方法");
        //throw new Exception("123");
        return 1024;
    }
}

public class CallableDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //一共两个线程，一个main主线程  一个叫AA 的callable线程

        FutureTask<Integer> futureTask = new FutureTask<Integer>(new MyThreadCallable());
        FutureTask<Integer> futureTask2 = new FutureTask<Integer>(new MyThreadCallable());
        new Thread(futureTask, "AA").start();
        //同一个futureTask，第二次不会进入
        new Thread(futureTask, "BB").start();
        //如果要多算几次，那么需要用新的futureTask
        new Thread(futureTask2, "CC").start();

        //异常 和 返回值 都是在 futureTask.get() 方法的时候才出现
        // 建议放在最后，因为futureTask.get()时，如果线程内容没有执行完，会导致堵塞
        Integer integer = futureTask.get();

        //判断future线程是否计算完毕
        while (!futureTask.isDone()) {

        }

        System.out.println("返回值：" + integer);
    }
}

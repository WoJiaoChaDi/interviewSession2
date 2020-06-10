package utils.current.lock.BlockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueDemo {
    public static void main(String[] args) {

        //抛出异常
        //addRemoveElement();

        //特殊值
        //offerPeekPoll();

        //阻塞
        //putTake();

        //超时阻塞
        offerPollWithTimeout();
    }

    private static void offerPollWithTimeout() {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        try {
            System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));
            System.out.println(blockingQueue.offer("b", 2L, TimeUnit.SECONDS));
            System.out.println(blockingQueue.offer("c", 2L, TimeUnit.SECONDS));
            System.out.println("=======");
            System.out.println(blockingQueue.offer("x", 2L, TimeUnit.SECONDS));

            System.out.println("-------");

            System.out.println(blockingQueue.poll(2L, TimeUnit.SECONDS));
            System.out.println(blockingQueue.poll(2L, TimeUnit.SECONDS));
            System.out.println(blockingQueue.poll(2L, TimeUnit.SECONDS));
            System.out.println("=======");
            System.out.println(blockingQueue.poll(2L, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void putTake() {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        try {
            blockingQueue.put("a");
            blockingQueue.put("b");
            blockingQueue.put("c");
            System.out.println("========");
            //阻塞队列总大小是3，所以put第4个元素，因为阻塞队列满了，所以线程会阻塞，直到put成功或中断退出
            //blockingQueue.put("x");

            System.out.println(blockingQueue.take());
            System.out.println(blockingQueue.take());
            System.out.println(blockingQueue.take());
            System.out.println("---------");
            //阻塞队列总大小是3，所以获取第4个元素，因为阻塞队列空了，所以线程会阻塞，直到可以take出一个元素或中断退出
            System.out.println(blockingQueue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void offerPeekPoll() {
        //List list = new ArrayList<>();
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        //总共长度3，第4个元素放入失败
        System.out.println(blockingQueue.offer("x"));

        System.out.println(blockingQueue.peek());

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        //由于第四个元素放入失败，所以获取第四个是null
        System.out.println(blockingQueue.poll());
    }

    private static void addRemoveElement() {
        //List list = new ArrayList<>();
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        //如果元素满了，会抛出 java.lang.IllegalStateException: Queue full 异常
        //System.out.println(blockingQueue.add("x"));

        //当前队列头的元素
        System.out.println(blockingQueue.element());

        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());

        //如果没有元素了，会抛出 java.util.NoSuchElementException 异常
        //System.out.println(blockingQueue.remove());

        //如果没有元素，检查当前队列头的元素，会抛出  java.util.NoSuchElementException  异常
        System.out.println(blockingQueue.element());
    }
}

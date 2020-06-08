package utils.current.collections;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Description: 集合类不安全的问题
 *
 * @author veliger@163.com
 * @date 2019-04-12 22:15
 **/
public class ContainerNotSafeDemo {
    /**
     * 笔记
     * 写时复制 copyOnWrite 容器即写时复制的容器 往容器添加元素的时候,不直接往当前容器object[]添加,而是先将当前容器object[]进行
     * copy 复制出一个新的object[] newElements 然后向新容器object[] newElements 里面添加元素 添加元素后,
     * 再将原容器的引用指向新的容器 setArray(newElements);
     * 这样的好处是可以对copyOnWrite容器进行并发的读,而不需要加锁 因为当前容器不会添加任何容器.所以copyOnwrite容器也是一种
     * 读写分离的思想,读和写不同的容器.
     * public boolean add(E e) {
     * final ReentrantLock lock = this.lock;
     * lock.lock();
     * try {
     * Object[] elements = getArray();
     * int len = elements.length;
     * Object[] newElements = Arrays.copyOf(elements, len + 1);
     * newElements[len] = e;
     * setArray(newElements);
     * return true;
     * } finally {
     * lock.unlock();
     * }
     * }
     *
     * @param args
     */
    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(1, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }

        //并发修改异常
        //Exception in thread "22" java.util.ConcurrentModificationException

        /**
         * 1.故障现象
         *  java.util.ConcurrentModificationException
         * 2.导致原因
         *    并发争抢修改导致
         * 3.解决方案
         *  3.1 new Vector<>()
         *      -->  Vector的public synchronized void addElement(E obj)
         *           add方法自动加锁了，虽然保证了安全，但是性能不高
         *  3.2 Collections.synchronizedList(new ArrayList<>());
         *      -->  构建成一个同步的List、Set、Map
         *  3.3 new CopyOnWriteArrayList<>();
         *      -->  用的类似读写锁的方式，add方法才锁
         *
         * 4.优化建议（同样的错误不犯两次）
         */

        List<String> list2 = new CopyOnWriteArrayList<>();
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                list2.add(UUID.randomUUID().toString().substring(1, 8));
                System.out.println(list2);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * ArrayList线程不安全的例子
     */
    @Test
    public void test1(){

    }
}

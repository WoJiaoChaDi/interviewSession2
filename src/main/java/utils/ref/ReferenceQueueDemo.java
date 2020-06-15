package utils.ref;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class ReferenceQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        Object o1 = new Object();
        Object o2 = new Object();
        ReferenceQueue referenceQueue = new ReferenceQueue();
        SoftReference<Object> softReference = new SoftReference<>(o1, referenceQueue);
        WeakReference<Object> weakReference = new WeakReference<>(o2, referenceQueue);

        System.out.println(o1);
        System.out.println(o2);
        System.out.println(softReference.get());
        System.out.println(weakReference.get());
        //GC回收后，进入引用队列
        System.out.println(referenceQueue.poll());
        System.out.println("======");

        o1 = null;
        o2 = null;
        System.gc();
        Thread.sleep(500);

        System.out.println(o1);
        System.out.println(o2);
        System.out.println(softReference.get());
        System.out.println(weakReference.get());
        System.out.println(referenceQueue.poll());

        //输出：
        //java.lang.Object@7f31245a
        //java.lang.Object@6d6f6e28
        //java.lang.Object@7f31245a
        //java.lang.Object@6d6f6e28
        //null
        //        ======
        //null
        //null
        //java.lang.Object@7f31245a
        //null
        //java.lang.ref.WeakReference@135fbaa4
    }
}

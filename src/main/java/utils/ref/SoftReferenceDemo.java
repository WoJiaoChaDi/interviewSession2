package utils.ref;

import java.lang.ref.SoftReference;

public class SoftReferenceDemo {
    /**
     * 内存够用的时候就保留，不够用就回收！
     */
    public static void softRef_Memory_Enough(){
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<>(o1);
        System.out.println(o1);
        System.out.println(softReference.get());

        o1 = null;
        System.gc();
        System.out.println(o1);
        System.out.println(softReference.get());
    }

    /**
     * 内存不够用就回收！
     * JVM配置，故意产生大对象并配置小的内存，让它内存不够用导致OOM，看软引用的回收情况
     * -Xms5m -Xmx5m -XX:+PrintGCDetails
     */
    public static void softRef_Memory_Not_Enough(){
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<>(o1);
        System.out.println(o1);
        System.out.println(softReference.get());

        o1 = null;
        //内存不够用的时候，会自己GC
        //System.gc();
        System.out.println(o1);
        System.out.println(softReference.get());

        try {
            byte[] bytes = new byte[50 * 1024 * 1024];
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(o1);
            System.out.println(softReference.get());
        }
    }



    public static void main(String[] args) {
        //softRef_Memory_Enough();
        softRef_Memory_Not_Enough();
        //日志：
//        java.lang.Object@7f31245a
//        java.lang.Object@7f31245a
//        null
//        java.lang.Object@7f31245a
//                [GC (Allocation Failure) [PSYoungGen: 884K->512K(1536K)] 988K->712K(5632K), 0.0006973 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
//[GC (Allocation Failure) [PSYoungGen: 512K->512K(1536K)] 712K->728K(5632K), 0.0008126 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
//[Full GC (Allocation Failure) [PSYoungGen: 512K->0K(1536K)] [ParOldGen: 216K->589K(4096K)] 728K->589K(5632K), [Metaspace: 2942K->2942K(1056768K)], 0.0079691 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
//[GC (Allocation Failure) [PSYoungGen: 0K->0K(1536K)] 589K->589K(5632K), 0.0003568 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
//[Full GC (Allocation Failure) Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
//                [PSYoungGen: 0K->0K(1536K)] [ParOldGen: 589K->574K(4096K)] 589K->574K(5632K), [Metaspace: 2942K->2942K(1056768K)], 0.0083801 secs] [Times: user=0.05 sys=0.00, real=0.01 secs]
//        null
//        at utils.ref.SoftReferenceDemo.softRef_Memory_Not_Enough(SoftReferenceDemo.java:39)
//        null      //软引用，内存不足的时候进行回收
//        at utils.ref.SoftReferenceDemo.main(SoftReferenceDemo.java:52)
    }
}

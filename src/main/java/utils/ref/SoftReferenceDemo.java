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
//                [GC (Allocation Failure) [PSYoungGen: 1022K->504K(1536K)] 1022K->616K(5632K), 0.0016350 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
//        java.lang.Object@7f31245a
//        java.lang.Object@7f31245a
//                [GC (Allocation Failure) [PSYoungGen: 953K->496K(1536K)] 1066K->720K(5632K), 0.0007095 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
//[GC (Allocation Failure) [PSYoungGen: 496K->504K(1536K)] 720K->736K(5632K), 0.0005691 secs] [Times: user=0.02 sys=0.00, real=0.00 secs]
//[Full GC (Allocation Failure) [PSYoungGen: 504K->0K(1536K)] [ParOldGen: 232K->594K(4096K)] 736K->594K(5632K), [Metaspace: 3020K->3020K(1056768K)], 0.0083722 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
//[GC (Allocation Failure) [PSYoungGen: 0K->0K(1536K)] 594K->594K(5632K), 0.0004826 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
//[Full GC (Allocation Failure) [PSYoungGen: 0K->0K(1536K)] [ParOldGen: 594K->577K(4096K)] 594K->577K(5632K), [Metaspace: 3020K->3020K(1056768K)], 0.0083897 secs] [Times: user=0.08 sys=0.00, real=0.01 secs]
//        Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
//        null
//        null    这个内存不够用的时候，软引用也回收了
    }
}

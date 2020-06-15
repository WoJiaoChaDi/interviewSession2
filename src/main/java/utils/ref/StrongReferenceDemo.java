package utils.ref;

public class StrongReferenceDemo {
    public static void main(String[] args) {
        Object obj1 = new Object(); //这样定义的默认就是强引用
        Object obj2 = obj1;//obj2引用赋值
        obj1 = null;//置空
        System.gc();
        //obj2是强引用，不会被垃圾回收
        System.out.println(obj1);
        System.out.println(obj2);
    }
}

package utils.GC_Dir;

public class GCRootDemo {

    /**
     * 在Java中，可作为GC Roots 的对象有：
     *      1.虚拟机栈（栈帧中的局部变量区，也叫做局部变量表）
     *      2.方法区中的类静态属性引用的对象。
     *      3.方法区中常量引用的对象
     *      4.本地方法栈中N( Native方法)引用的对象
     */
    //2.方法区中的类静态属性引用的对象。
    private static GCRootDemo t2;
    //3.方法区中常量引用的对象
    private static final GCRootDemo t3 = new GCRootDemo();

    public static void main(String[] args) {
        //1.虚拟机栈（栈帧中的局部变量区，也叫做局部变量表）
        GCRootDemo g1 = new GCRootDemo();
    }
}

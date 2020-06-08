package utils.current;

public class ReSortSeqDemo {
    int a = 0;
    boolean flag = false;

    public void method01(){
        a = 1;          //语句1
        flag = true;    //语句2
    }

    // 多线程环境中，线程交替执行，由于编译器优化重拍的存在
    // 两个线程中使用的变量能否保证一致性是无法确定的，结果无法预测
    // 即：可能 method01() 方法 可能指令重排后，变成了  语句2、语句1、语句3  这个顺序了
    // 这样 当另一个线程的 method02()方法抢占到资源后，就直接进入了if，导致最后输出结果是 a=0+5;
    public void method02(){
        if (flag) {
            a = a + 5;  //语句3
            System.out.println("****retValue:" + a);
        }
    }
}

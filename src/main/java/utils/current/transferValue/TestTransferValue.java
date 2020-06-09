package utils.current.transferValue;

public class TestTransferValue {

    public void set1(int age) {
        age = 30;
    }

    public void set2(Person person) {
        person.setName("xxx");
    }

    public void set3(Person person) {
        person = new Person("xxx");
    }

    public void set4(String str) {
        str = "xxx";
    }

    public static void main(String[] args) {
        TestTransferValue test = new TestTransferValue();
        // 对于基本类型，是传递的值
        // main方法中 有个 age=20
        // set1方法中 也有个 age=20
        // 而只改了set1中age的值，所以main方法中的age还是原来的值
        int age = 20;
        test.set1(age);
        System.out.println("age---" + age);

        // 对于对象，传递的是引用地址
        // 在main方法中，其person的name的引用地址，指向 "abc" 字符串所在的内存地址
        // 在set2方法中，将person的name的引用地址，从指向 "abc" 改为了 指向 "xxx"
        // 在这中途，person的引用地址并没有改变，改变的是 person.name的引用地址
        //
        // 所以，最后main方法中的 person的引用地址 和 set2 中的引用地址是同一个，
        // 所以这个相同的person对象的name的引用地址也是同一个，所以main中的person.name也指向了 "xxx"
        Person person = new Person("abc");
        test.set2(person);
        System.out.println("person-name---" + person.getName());

        // 对比上一例子
        // 在main方法中，其person3的引用地址 指向 地址A
        // 在set3方法中，把person3的引用地址 重新指向了 地址B，
        // 而返回main方法中，main中的person3的引用地址，依然指向的是 地址A，所以地址A中的person对象的name值并未改变
        Person person3 = new Person("abc");
        test.set3(person3);
        System.out.println("person3-name---" + person3.getName());

        // 类似第三个例子
        // 所以
        // 在main方法中，main的str 其指向 "abc" 的内存地址
        // 在set3方法中，set3的str 从指向 "abc" 改为了 "xxx" 的内存地址
        // 所以，在main方法中的 str，指向没有改变
        String str = "abc";
        test.set4(str);
        System.out.println("str---" + str);
    }
}

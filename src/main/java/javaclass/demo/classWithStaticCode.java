package javaclass.demo;

public class classWithStaticCode {
    // 静态代码块: JVM加载类时执行，仅执行一次
    static {
        System.out.println("Static block executed. This only happens once when the JVM loads the class.");
    }

    // 构造代码块: 每一次创建对象时执行
    {
        System.out.println("Instance initialization block executed. This happens each time an instance is created.");
    }

    // 构造函数: 每一次创建对象时执行
    public classWithStaticCode() {
        System.out.println("Constructor executed. This happens each time an instance is created.");
    }
}

package ognl.demo;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;

public class OGNLExample {

    // 示例类
    public static class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    public static void main(String[] args) throws OgnlException {
        // 创建一个Person对象实例
        Person person = new Person("John Doe", 30);

        // 创建Ognl上下文并设置根对象
        OgnlContext context = new OgnlContext();
        context.put("person", person); // 设置"person"作为OGNL表达式的根对象

        // 使用OGNL表达式获取或修改对象属性
        // 获取name属性值
        Object nameValue = Ognl.getValue("#person.name", context, context.getRoot());
        // Object nameValue = Ognl.getValue("${1+1}", context, context.getRoot());
        System.out.println("Name: " + nameValue);

        // 修改age属性值
        Ognl.setValue("#person.age", context, context.getRoot(), 35);
        System.out.println("Age after modification: " + Ognl.getValue("#person.age", context, context.getRoot()));
    }
}
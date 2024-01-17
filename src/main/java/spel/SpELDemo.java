package spel;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class SpELDemo {

    public static void main(String[] args) {
        // 创建一个SpEL表达式解析器
        ExpressionParser parser = new SpelExpressionParser();

        // 定义一个对象上下文（例如，可以是任意Java对象）
        Person person = new Person("John Doe", 30);

        // 创建一个标准评估上下文，并设置根对象为person
        StandardEvaluationContext context = new StandardEvaluationContext(person);

        // 解析并执行一个SpEL表达式
        String spelExpression = "#root.name + ' is ' + #root.age + ' years old'";
        Expression exp = parser.parseExpression(spelExpression);

        // 执行表达式并获取结果
        String result = (String) exp.getValue(context);
        System.out.println(result);  // 输出：John Doe is 30 years old

        // 另外一个例子，不依赖于任何特定的对象上下文
        String simpleExpression = "T(java.lang.Math).random() * 100";
        Expression randomExp = parser.parseExpression(simpleExpression);
        Double randomNumber = (Double) randomExp.getValue();
        System.out.printf("A random number between 0 and 100: %.2f%n", randomNumber);
    }

    static class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}
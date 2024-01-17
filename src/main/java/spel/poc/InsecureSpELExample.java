package spel.poc;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class InsecureSpELExample {

    // 假设这是敏感数据
    public String secretData = "This is a secret message.";

    public static void main(String[] args) {
        InsecureSpELExample example = new InsecureSpELExample();

        // 直接从用户输入获取SpEL表达式
        String userInput = "#root.secretData"; // 这可能是恶意用户的输入

        // 创建SpEL解析器和评估上下文
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext(example);

        // 解析并执行用户提供的SpEL表达式
        Object result = parser.parseExpression(userInput).getValue(context);

        // 输出结果（在这个例子中会输出敏感信息）
        System.out.println("Result: " + result);
    }

    // 注释：
    // 上述代码中存在严重安全漏洞：
    // 1. 用户可以直接通过SpEL表达式访问`secretData`属性。
    // 2. 如果用户输入诸如"#environment.systemProperties['user.home']"这样的表达式，则可能暴露系统环境变量。
    // 3. 更为复杂的注入攻击可能会利用SpEL执行任意操作，例如方法调用、对象创建等。
}
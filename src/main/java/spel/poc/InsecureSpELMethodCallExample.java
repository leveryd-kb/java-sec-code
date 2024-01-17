package spel.poc;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class InsecureSpELMethodCallExample {

    // 假设这是个简单的数据处理方法
    public String process(String input) {
        return "Processed: " + input; // 简化的逻辑，实际可能涉及数据库操作、文件读写等敏感操作
    }

    public static void main(String[] args) {
        InsecureSpELMethodCallExample example = new InsecureSpELMethodCallExample();

        // 用户输入一个带有方法调用的SpEL表达式
        String userInput = "#root.process('Malicious Data')";
        // String userInput = "#target.process('Malicious Data')";
        // String userInput = "T(java.lang.Runtime).getRuntime().exec('cp /etc/passwd /tmp/1')";

        // 创建SpEL解析器和评估上下文，将"example"对象作为根对象
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext(example);
        // 注册'@target'为当前对象
        context.setVariable("target", example);

        // 解析并执行用户提供的SpEL表达式
        Object result = parser.parseExpression(userInput).getValue(context);

        // 输出结果（在这个例子中，恶意数据被传递给了process方法）
        System.out.println("Result: " + result);
    }

    // 注释：
    // 上述代码存在严重安全漏洞：
    // 1. 用户可以通过SpEL表达式直接调用`process`方法，并传入任意参数。
    // 2. 在本例中，攻击者可以构造字符串'Malicious Data'来尝试触发潜在的安全问题，例如SQL注入、目录遍历或其他系统层面的攻击。
    // 3. 即使`process`方法本身没有明显的安全隐患，允许不受限制地调用任何类的方法也极其危险，因为其他方法可能包含敏感操作。

    // 正确做法是严格限制SpEL表达式的使用，禁止其对任何可能造成危害的方法进行调用。
}
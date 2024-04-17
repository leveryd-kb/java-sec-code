package hessian;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.OutputStream;
import java.io.InputStream;


public class HessianSerializationExample {

    public static void main(String[] args) throws IOException {
        // 创建一个简单的对象
        SimpleBean bean = new SimpleBean();
        bean.setId(123);
        bean.setName("Test BeanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxBeanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        //bean.setName("hello");
        //bean.setName("com.alibaba.fastjson.JSONObject");

        // bean.setName("\u00c3");
        // String bean = "abcdefghijklmnopqrstuvwxyz0123456789";
        // String bean = "com.alibaba.fastjson.JSONObject";

        writeToFile(bean);
        restore();
        //writeToConsole(bean);
    }

    // 方法用于将字节数组转换成16进制字符串
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x ", b));
        }
        return sb.toString().trim();
    }

    private static void writeToConsole(Object o) throws IOException {
        // 序列化对象
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Hessian2Output out = new Hessian2Output(baos);
        out.writeObject(o);
        out.flush();

        // 获取序列化后的字节数组
        byte[] serializedBytes = baos.toByteArray();

        // 将字节数组转换为16进制字符串并输出
        String hexString = bytesToHex(serializedBytes);
        System.out.println("Serialized data as hexadecimal: " + hexString);

    }

    private static void writeToFile(Object o) throws IOException{
        // 准备输出流，这里将序列化后的数据写入到名为"hessian_serialized.bin"的文件中
        OutputStream fileOutputStream = new FileOutputStream("hessian_serialized.bin");

        // 创建Hessian2Output并绑定到文件输出流
        Hessian2Output out = new Hessian2Output(fileOutputStream);

        // 序列化对象并写入文件
        out.writeObject(o);
        out.flush();
        out.close();

        // 不再需要单独转换为16进制并输出，因为我们已将二进制数据直接写入了文件
        System.out.println("Serialized data has been written to hessian_serialized.bin file.");
    }

    public static void restore() throws IOException {
        // 读取之前保存的Hessian序列化数据的文件
        InputStream inputStream = new FileInputStream("hessian_serialized.bin");

        // 创建Hessian2Input并绑定到输入流
        Hessian2Input in = new Hessian2Input(inputStream);

        // 反序列化数据
        SimpleBean bean = (SimpleBean) in.readObject(SimpleBean.class);

        // 处理反序列化后的对象
        System.out.println("Deserialized SimpleBean:");
        System.out.println("ID: " + bean.getId());
        System.out.println("Name: " + bean.getName());

        // 关闭输入流
        in.close();
    }
}

// 示例Bean类
class SimpleBean implements Serializable {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
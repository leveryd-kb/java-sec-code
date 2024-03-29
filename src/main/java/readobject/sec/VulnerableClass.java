package readobject.sec;

import java.io.*;

// 定义一个名为 VulnerableClass 的类，实现 Serializable 接口以允许对象的序列化和反序列化
public class VulnerableClass implements Serializable {

    // serialVersionUID 是序列化时用于验证类版本一致性的标识
    private static final long serialVersionUID = 1L;

    // 在反序列化过程中，如果对象的类定义包含一个名为 readObject 的方法，
    // 那么这个方法会在反序列化过程中被自动调用
    private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        // defaultReadObject 方法负责读取非静态和非瞬态字段
        stream.defaultReadObject();
        // exec 方法用于执行指定的字符串命令。在这个例子中，我们打开了计算器应用
        Runtime.getRuntime().exec("open -a Calculator");
    }

    public static void main(String[] args) throws Exception {
        // 创建一个 ObjectOutputStream 对象，用于将对象写入到文件中
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tmp.ser"));
        // 使用 writeObject 方法将 VulnerableClass 对象写入到文件中
        oos.writeObject(new VulnerableClass());
        // 关闭 ObjectOutputStream
        oos.close();

        // 创建一个 ObjectInputStream 对象，用于从文件中读取对象
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("tmp.ser"));
        // 使用 readObject 方法从文件中读取 VulnerableClass 对象。在这个过程中，readObject 方法会被自动调用
        ois.readObject();
        // 关闭 ObjectInputStream
        ois.close();
    }
}

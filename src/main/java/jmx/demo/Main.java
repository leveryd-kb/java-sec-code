package jmx.demo;

import javax.management.*;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.management.ManagementFactory;

public class Main {
    public static void main(String[] args) throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, InterruptedException, IOException {
        // 获取当前JVM的MBean服务器
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

        // 创建MBean对象实例
        MyMBean myMBean = new MyMBean();

        // 创建MBean的对象名
        ObjectName objectName = new ObjectName("com.example:type=MyMBean");

        // 将MBean注册到MBean服务器
        mBeanServer.registerMBean(myMBean, objectName);

        System.out.println("MBean registered successfully...");

        //构造 JMXServiceURL
        JMXServiceURL jmxServiceURL = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi");
        JMXConnectorServer jmxConnectorServer = JMXConnectorServerFactory.newJMXConnectorServer(jmxServiceURL, null, mBeanServer);
        jmxConnectorServer.start();
        System.out.println("JMXConnectorServer is running");

        // 保持程序运行，以便可以通过JMX客户端连接
        Thread.sleep(Long.MAX_VALUE);
    }
}

// 两种方式打开远程JMX
// 方式一：https://docs.oracle.com/javadb/10.10.1.2/adminguide/radminjmxenablenoauth.html
// java -cp target/java-sec-1.0-SNAPSHOT.jar -Dcom.sun.management.jmxremote.port=9010 -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false jmx.demo.Main
// 方式二：上面代码那样打开
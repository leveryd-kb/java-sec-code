package jmx.demo;

import javax.management.*;
import javax.management.remote.*;

public class JmxClient {
    public static void main(String[] args) throws Exception {
        // Replace with the port number you identified
        int port = 1099;

        // Construct the JMX service URL
        String urlStr = "service:jmx:rmi:///jndi/rmi://localhost:" + port + "/jmxrmi";
        JMXServiceURL url = new JMXServiceURL(urlStr);

        // Connect to the JMX agent
        JMXConnector connector = JMXConnectorFactory.connect(url);
        try {
            MBeanServerConnection mbsc = connector.getMBeanServerConnection();

            // Print the names of all registered MBeans
            for (ObjectName name : mbsc.queryNames(null, null)) {
                System.out.println(name);
            }
        } finally {
            connector.close();
        }
    }
}

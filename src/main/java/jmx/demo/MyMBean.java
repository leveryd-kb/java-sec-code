package jmx.demo;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class MyMBean implements DynamicMBean {

    // MBean的属性
    private String myProperty = "Initial Value";

    @Override
    public Object getAttribute(String attribute) throws AttributeNotFoundException, MBeanException, ReflectionException {
        if ("MyProperty".equals(attribute)) {
            return myProperty;
        }
        throw new AttributeNotFoundException("Attribute not found");
    }

    @Override
    public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
        if ("MyProperty".equals(attribute.getName())) {
            this.myProperty = (String) attribute.getValue();
        } else {
            throw new AttributeNotFoundException("Attribute not found");
        }
    }

    @Override
    public AttributeList getAttributes(String[] attributes) {
        return null;
    }

    @Override
    public AttributeList setAttributes(AttributeList attributes) {
        return null;
    }

    @Override
    public Object invoke(String actionName, Object[] params, String[] signature) throws MBeanException, ReflectionException {
        return null;
    }

    // 其他必要的MBean接口方法实现...

    // 创建MBean信息
    private static MBeanInfo createMBeanInfo() {
        MBeanAttributeInfo attrInfo = new MBeanAttributeInfo("MyProperty", "java.lang.String", "Example Property", true, true, false);
        MBeanInfo mBeanInfo = new MBeanInfo(MyMBean.class.getName(), "My Managed Bean Description", new MBeanAttributeInfo[]{attrInfo}, null, null, null);
        return mBeanInfo;
    }

    // 实现DynamicMBean接口的核心方法
    @Override
    public MBeanInfo getMBeanInfo() {
        return createMBeanInfo();
    }
}
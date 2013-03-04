package com.paul.common.util.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

/**
 * 保存由ContentextLoadListener载入的ApplicationContext 静态变量
 * 并实现了BeanFactory接口的委托，简化客户代码调用。
 */
public class SpringContextUtil {
    private static ApplicationContext context;

    public static void setApplicationContext(ApplicationContext acx) {
        context = acx;
    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    public static Object getBean(String name) throws BeansException {
        return context.getBean(name);
    }

    public static Object getBean(String name, Class requiredType) throws BeansException {
        return context.getBean(name, requiredType);
    }

    public static boolean containsBean(String name) {
        return context.containsBean(name);
    }

    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return context.isSingleton(name);
    }

    public static Class getType(String name) throws NoSuchBeanDefinitionException {
        return context.getType(name);
    }

    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return context.getAliases(name);
    }
}

package com.gandalf.framework.spring;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 类PropertyConfigurer.java的实现描述：提供对配置文件的解析
 * 
 * @author gandalf 2014-2-21 下午2:23:25
 */
public class PropertyConfigurer extends PropertyPlaceholderConfigurer {

    private static Map<String, Object> ctxPropertiesMap;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
                                                                                                            throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        ctxPropertiesMap = new HashMap<String, Object>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            ctxPropertiesMap.put(keyStr, value);
        }
    }

    public static Object getProperty(String name) {
        return ctxPropertiesMap.get(name);
    }

    public static String getPropertyStr(String name) {
        return (String) ctxPropertiesMap.get(name);
    }
}

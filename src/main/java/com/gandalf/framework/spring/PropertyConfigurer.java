package com.gandalf.framework.spring;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 类PropertyConfigurer.java的实现描述：提供对配置文件的解析
 * 
 * @author gandalf 2014-2-21 下午2:23:25
 */
public class PropertyConfigurer extends PropertyPlaceholderConfigurer {

    private static Map<String, String> ctxPropertiesMap;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
                                                                                                            throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        ctxPropertiesMap = new HashMap<String, String>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            ctxPropertiesMap.put(keyStr, value);
        }
    }

    public static String getProperty(String name) {
        return ctxPropertiesMap.get(name);
    }
    
    public static Set<String> getPropertyName() {
    	return ctxPropertiesMap.keySet();
    }

}

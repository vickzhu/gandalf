package com.gandalf.framework.ibatis;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述：ibatis客户端的拦截器 用cglib的方式做增强
 * 
 * @author gandalf
 */
public class SqlMapClientInterceptor implements MethodInterceptor {

    private Logger   logger   = LoggerFactory.getLogger(SqlMapClientInterceptor.class);

    // 被代理的对象实例
    private Object   object;
    // cglib的代理器
    private Enhancer enhancer = new Enhancer();

    /**
     * 默认的拦截方法
     * 
     * @param o
     * @param method
     * @param args
     * @param proxy
     * @return
     * @throws Throwable
     */
    public Object intercept(Object o, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Object result = null;

        // 执行调用
        String methodName = method.getName();
        // 拦截指定的方法
        if ((methodName.startsWith("query") || methodName.startsWith("update") || methodName.startsWith("insert"))) {
            long startTime = System.currentTimeMillis();
            result = proxy.invoke(object, args);
            // 取得Statement (这个判断为了容错)
            String statementName = "Unknown";
            if (args.length > 0 && args[0] instanceof String) {
                statementName = (String) args[0];
            }
            logger.debug(statementName + " cost " + (System.currentTimeMillis() - startTime) + "ms");
        } else {
            result = proxy.invoke(object, args);
        }
        return result;
    }

    /**
     * 构造一个代理的实例
     * 
     * @param object
     * @return
     */
    public Object proxy(Object object) {
        this.object = object;
        enhancer.setSuperclass(object.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

}

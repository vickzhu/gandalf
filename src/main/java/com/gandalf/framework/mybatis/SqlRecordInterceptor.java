package com.gandalf.framework.mybatis;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * 该类用于计算sql语句的执行时间
 * Intercepts用于表明当前的对象是一个Interceptor，而Signature则表明要拦截的接口、方法以及对应的参数类型
 * Mybatis拦截器只能拦截四种类型的接口：Executor、StatementHandler、ParameterHandler和ResultSetHandler
 * </pre>
 * @author gandalf 2016-3-21 上午11:14:30
 * 
 */
@Intercepts( {
    @Signature( method = "query", type = Executor.class, args = {MappedStatement.class, Object.class, RowBounds.class,ResultHandler.class }),
    @Signature( method = "update", type = Executor.class, args = {MappedStatement.class, Object.class }) })
public class SqlRecordInterceptor implements Interceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(SqlRecordInterceptor.class);

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        long start = System.currentTimeMillis();
        Object returnValue = invocation.proceed();
        long end = System.currentTimeMillis();
        long time = (end - start);
        logger.debug(boundSql.getSql() + " ["+time+"ms]");
        return returnValue;
	}
	

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this); 
	}

	/**
	 * 用于在配置文件中注入属性
	 */
	@Override
	public void setProperties(Properties properties) {
		
	}

}

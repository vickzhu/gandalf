package com.gandalf.framework.velocity.render;

import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.Renderable;

/**
 * 类SystemInfoRender.java的实现描述：系统信息渲染
 * 
 * @author gandalf 2014-2-25 下午3:02:37
 */
public class SystemInfoRender implements Renderable {

    private String value;

    public SystemInfoRender(String value) {
        this.value = value;
    }

    /*
     * (non-Javadoc)
     * @see org.apache.velocity.runtime.Renderable#render(org.apache.velocity.context.InternalContextAdapter,
     * java.io.Writer)
     */
    public boolean render(InternalContextAdapter context, Writer writer) throws IOException, MethodInvocationException,
                                                                        ParseErrorException, ResourceNotFoundException {
        writer.write(value);
        return true;
    }

}

package com.gandalf.framework.velocity.render;

import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.Renderable;

/**
 * 类InputRender.java的实现描述：输入框渲染
 * 
 * @author gandalf 2014-2-25 下午2:30:40
 */
public class InputRender implements Renderable {

    private String type;
    private String name;
    private String value;

    public InputRender(String type, String name, String value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    /*
     * (non-Javadoc)
     * @see org.apache.velocity.runtime.Renderable#render(org.apache.velocity.context.InternalContextAdapter,
     * java.io.Writer)
     */
    public boolean render(InternalContextAdapter context, Writer writer) throws IOException, MethodInvocationException,
                                                                        ParseErrorException, ResourceNotFoundException {
        StringBuffer sb = new StringBuffer();
        sb.append("<input type=\"");
        sb.append(type);
        sb.append("\" name=\"");
        sb.append(name);
        sb.append("\" value=\"");
        sb.append(value);
        sb.append("\" />");
        writer.write(sb.toString());
        return true;
    }

}

package com.gandalf.framework.velocity.directive;

import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;

/**
 * 类SliteralDirective.java的实现描述：源代码输出指令
 * 
 * @author gandalf 2013-10-14 下午3:24:17
 */
public class SliteralDirective extends GandalfDirective {

    /*
     * (non-Javadoc)
     * @see org.apache.velocity.runtime.directive.Directive#getName()
     */
    @Override
    public String getName() {
        return "SLITERAL";
    }

    /*
     * (non-Javadoc)
     * @see org.apache.velocity.runtime.directive.Directive#getType()
     */
    @Override
    public int getType() {
        return LINE;
    }

    /*
     * (non-Javadoc)
     * @see org.apache.velocity.runtime.directive.Directive#render(org.apache.velocity.context.InternalContextAdapter,
     * java.io.Writer, org.apache.velocity.runtime.parser.node.Node)
     */
    @Override
    public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException,
                                                                                   ResourceNotFoundException,
                                                                                   ParseErrorException,
                                                                                   MethodInvocationException {
        SimpleNode snText = (SimpleNode) node.jjtGetChild(0);
        String text = (String) snText.value(context);
        writer.write(text);
        return true;
    }

}

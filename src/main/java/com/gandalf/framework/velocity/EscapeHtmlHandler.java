package com.gandalf.framework.velocity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.event.ReferenceInsertionEventHandler;
import org.apache.velocity.runtime.Renderable;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.util.RuntimeServicesAware;

import com.gandalf.framework.velocity.directive.GandalfDirective;
import com.gandalf.framework.web.escape.StringEscapeUtil;

/**
 * 对velocity输出进行处理，默认会对所有输出进行html实体编码
 * 
 * @author gandalf 2014-3-17 下午3:25:32
 */
public class EscapeHtmlHandler implements ReferenceInsertionEventHandler, RuntimeServicesAware {

    private RuntimeServices rs;

    private Pattern         pattern;

    private String          matchRegExp = null;

    /*
     * (non-Javadoc)
     * @see org.apache.velocity.app.event.ReferenceInsertionEventHandler#referenceInsert (java.lang.String,
     * java.lang.Object)
     */
    public Object referenceInsert(String reference, Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Renderable) {
            return value;
        }
        if (value instanceof GandalfDirective) {
            return value;
        }
        if (matchRegExp == null) {
            return escape(value);
        } else if (pattern.matcher(reference).matches()) {
            return value;
        } else {
            return escape(value);
        }
    }

    private String escape(Object text) {
        return StringEscapeUtil.escapeHtml(text.toString());
    }

    /*
     * (non-Javadoc)
     * @see org.apache.velocity.util.RuntimeServicesAware#setRuntimeServices(org.
     * apache.velocity.runtime.RuntimeServices)
     */
    public void setRuntimeServices(RuntimeServices rs) {
        this.rs = rs;
        /**
         * Get the regular expression pattern.
         */
        matchRegExp = rs.getConfiguration().getString(getMatchAttribute());
        if (StringUtils.isBlank(matchRegExp)) {
            matchRegExp = null;
            rs.getLog().error("Regular expression <" + matchRegExp + ">.  can't execute escaping!");
        }
        pattern = Pattern.compile(matchRegExp);
    }

    /**
     * @return attribute "eventhandler.noescape.html.match"
     */
    protected String getMatchAttribute() {
        return "eventhandler.noescape.html.match";
    }

    /**
     * Retrieve a reference to RuntimeServices. Use this for checking additional configuration properties.
     * 
     * @return The current runtime services object.
     */
    protected RuntimeServices getRuntimeServices() {
        return rs;
    }

    public static void main(String[] args) {
        Matcher matcher = Pattern.compile("\\$\\{?screen_content\\}?|\\$\\!?\\{?tokenUtil\\S*\\}?").matcher("$tokenUtil");
        System.out.println(matcher.matches());

    }
}

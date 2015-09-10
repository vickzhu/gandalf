package com.gandalf.framework.velocity;

import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

import com.gandalf.framework.velocity.directive.SjsDirective;
import com.gandalf.framework.velocity.directive.SliteralDirective;
import com.gandalf.framework.velocity.directive.SxmlDirective;

/**
 * 类GandalfVelocityConfigurer.java的实现描述：velocity配置类
 * 
 * @author gandalf 2015-1-13 下午10:45:29
 */
public class GandalfVelocityConfigurer extends VelocityConfigurer {

    private static final String USERDIRECTIVEKEY        = "userdirective";
    private final List<String>  additionalUserDirective = new ArrayList<String>(3);

    public GandalfVelocityConfigurer() {
        additionalUserDirective.add(SliteralDirective.class.getName());
        additionalUserDirective.add(SxmlDirective.class.getName());
        additionalUserDirective.add(SjsDirective.class.getName());
    }

    protected void postProcessVelocityEngine(VelocityEngine velocityEngine) {
        super.postProcessVelocityEngine(velocityEngine);
        for (String className : additionalUserDirective) {
            velocityEngine.addProperty(USERDIRECTIVEKEY, className);
        }
        velocityEngine.addProperty(RuntimeConstants.VM_LIBRARY, "com/gandalf/framework/velocity/macros.vm");
    }

}

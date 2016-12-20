package com.gandalf.framework.velocity.tool;

import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.config.ValidScope;

import com.gandalf.framework.velocity.render.InputRender;
import com.gandalf.framework.web.tool.TokenUtil;

/**
 * 类TokenUtil.java的实现描述：生成Token
 * 
 * @author gandalf 2014-2-20 下午7:24:04
 */
@DefaultKey("tokenTool")
@ValidScope(Scope.APPLICATION)
public class TokenTool extends AbstractTool {

    public InputRender getHiddenField() {
        String token = TokenUtil.getToken(request, response);
        return new InputRender("hidden", TokenUtil.getTokenKey(), token);
    }
    
    public String getTokenKey(){
    	return TokenUtil.getTokenKey();
    }
    
    public String getToken(){
    	return TokenUtil.getToken(request, response);
    }
    
    /**
     * 启用异步token
     */
    public void enableAjaxToken(){
    	TokenUtil.setTokenInCookie(request, response);
    }
    
}

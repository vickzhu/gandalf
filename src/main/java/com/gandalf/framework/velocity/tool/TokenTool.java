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
	
	/**
	 * 生成隐藏的input
	 * @return
	 */
	public InputRender getHiddenInput() {
		return getHiddenInput(0);
	}
	
	public InputRender getHiddenInputOnce() {
		return getHiddenInput(1);
	}
    
	/**
	 * 生成隐藏的Input
	 * @return
	 */
    public InputRender getHiddenInput(int type) {
    	String token = TokenUtil.getLongToken(request, response);
    	String inputName = TokenUtil.getCsrfTokenKey();
    	if(type == 1) {//防止重复提交
    		token = TokenUtil.getOnceToken(request, response);
    		inputName = TokenUtil.getOnceTokenKey();
    	}
        return new InputRender("hidden", inputName, token);
    }
    
    public String getTokenValue() {
    	return TokenUtil.getLongToken(request, response);
    }
    
    public String getTokenKey(){
    	return TokenUtil.getCsrfTokenKey();
    }
    
}

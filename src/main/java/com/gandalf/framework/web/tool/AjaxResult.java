package com.gandalf.framework.web.tool;

/**
 * 类AjaxResult.java的描述：异步请求结果
 * 
 * @author gandalf 2016年4月6日 上午9:37:01
 */
public class AjaxResult {

	/**
	 * 返回结果
	 */
	private Object data;
	/**
	 * 成功或失败消息
	 */
	private String message;
	/**
	 * 是否成功
	 */
	private boolean success;

	public AjaxResult(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public AjaxResult(boolean success, String message, Object data) {
		this.success = success;
		this.message = message;
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}

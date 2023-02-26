package com.gandalf.framework.web.tool;

/**
 * 类AjaxResult.java的描述：异步请求结果
 * 
 * @author gandalf 2016年4月6日 上午9:37:01
 */
public class AjaxResult {

	/**
	 * 状态码，默认200
	 */
	private int code = 200;
	/**
	 * 返回结果
	 */
	private Object data;
	/**
	 * 成功或失败消息
	 */
	private String message;

	/**
	 * 默认返回200的状态码
	 */
	public AjaxResult() {
		this.code = 200;
		this.message = "sucess";
	}
	
	/**
	 * 默认成功
	 * @param data
	 */
	public AjaxResult(Object data) {
		this.code = 200;
		this.message = "sucess";
		this.data = data;
	}

	public AjaxResult(int code, String message) {
		this.code = code;
		this.message = message;
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

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	public void setError(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public void setSuccess(Object data) {
		this.code = 200;
		this.message = "success";
		this.data = data;
	}

}

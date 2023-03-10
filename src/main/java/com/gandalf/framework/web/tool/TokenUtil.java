package com.gandalf.framework.web.tool;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gandalf.framework.constant.SymbolConstant;
import com.gandalf.framework.util.StringUtil;
import com.gandalf.framework.web.token.store.TokenStore;

/**
 * 类TokenUtil.java的实现描述：用于生成Token
 * 
 * @author gandalf 2014-3-11 下午3:34:45
 */
public class TokenUtil {

	/**
	 * csrf Token的名字
	 */
	public static final String CSRF_TOKEN_KEY = "_csrftoken";
	/**
	 * 表单提交Token的名字
	 */
	public static final String NONCE_TOKEN_KEY = "_nonceToken";
	/**
	 * 一次性Token在session中的key
	 */
	protected static final String TOKEN_SESSION_O_KEY = "_sot";
	/**
	 * 长时间Token在session中的key
	 */
	protected static final String TOKEN_SESSION_L_KEY = "_slt";
	/**
	 * Token分割符
	 */
	protected static final String TOKEN_SEPARATOR = SymbolConstant.COMMA;
	/**
	 * 最多保存5个Token
	 */
	protected static final int maxTokens = 5;
	/**
	 * 单个token的长度
	 */
	protected static final int tokenLength = 8;

	public static String getCsrfTokenKey() {
		return CSRF_TOKEN_KEY;
	}

	public static String getNonceTokenKey() {
		return NONCE_TOKEN_KEY;
	}

	/**
	 * 获取长时间使用的token
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getLongToken(HttpServletRequest request, HttpServletResponse response) {
		String token = TokenStore.getToken(request, response, TOKEN_SESSION_L_KEY);
		if (StringUtil.isBlank(token)) {
			token = StringUtil.getRand(tokenLength);
			TokenStore.saveToken(request, response, TOKEN_SESSION_L_KEY, token);
		}
		return token;
	}

	/**
	 * 获取一次性使用的token
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getOnceToken(HttpServletRequest request, HttpServletResponse response) {
		String token = StringUtil.getRand(tokenLength);
		setTokenInStore(request, response, token);
		return token;
	}

	/**
	 * 检查request和session中的Token是否一致
	 * 
	 * @param request
	 * @return
	 */
	public static boolean checkToken(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getParameter(NONCE_TOKEN_KEY);
		if (StringUtil.isNotBlank(token)) {// 临时token，主要防止表单重复提交
			return checkToken(token, request, response);
		}
		token = request.getParameter(CSRF_TOKEN_KEY);
		if (StringUtil.isBlank(token)) {
			return false;
		}
		String tokenInSession = TokenStore.getToken(request, response, TOKEN_SESSION_L_KEY);
		return token.equals(tokenInSession);

	}

	/**
	 * 检查token和session中的是否一致
	 * 
	 * @param tokenFromRequest
	 * @param request
	 * @param response
	 * @return
	 */
	public static boolean checkToken(String token, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtil.isBlank(token)) {// request必须存在token
			return false;
		}
		List<String> tokensInSession = getTokensInStore(request, response);
		if (!tokensInSession.contains(token)) {
			return false;
		} else {
			tokensInSession.remove(token);
			setTokensInStore(request, response, tokensInSession);
			return true;
		}
	}

	/**
	 * 将Token保存到session
	 * 
	 * @param token
	 * @return
	 */
	private static String setTokenInStore(HttpServletRequest request, HttpServletResponse response, String token) {
		LinkedList<String> tokens = getTokensInStore(request, response);
		tokens.addLast(token);
		while (tokens.size() > maxTokens) {
			tokens.removeFirst();
		}
		setTokensInStore(request, response, tokens);
		return token;
	}

	/**
	 * 从session中获得所有的Token
	 * 
	 * @return
	 */
	private static LinkedList<String> getTokensInStore(HttpServletRequest request, HttpServletResponse response) {
		String token = TokenStore.getToken(request, response, TOKEN_SESSION_O_KEY);
		if (StringUtil.isBlank(token)) {
			return createLinkedList(StringUtil.EMPTY);
		} else {
			String[] tokens = StringUtil.split(token, TOKEN_SEPARATOR);
			return createLinkedList(tokens);
		}

	}

	/**
	 * 将所有的Token保存在Session中
	 * 
	 * @param tokens
	 */
	private static void setTokensInStore(HttpServletRequest request, HttpServletResponse response,
			List<String> tokens) {
		if (tokens.isEmpty()) {
			TokenStore.removeToken(request, response, TOKEN_SESSION_O_KEY);
		} else {
			TokenStore.saveToken(request, response, TOKEN_SESSION_O_KEY, StringUtil.join(tokens, TOKEN_SEPARATOR));
		}
	}

	/**
	 * 用于保存Token
	 * 
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static <T, V extends T> LinkedList<T> createLinkedList(V... args) {
		LinkedList<T> list = new LinkedList<T>();
		if (args != null) {
			for (V v : args) {
				list.add(v);
			}
		}
		return list;
	}
}

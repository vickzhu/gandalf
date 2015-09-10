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
     * Token的key
     */
    protected static final String TOKEN_KEY         = "gandalftoken";
    /**
     * Token在session中存在的key
     */
    protected static final String TOKEN_SESSION_KEY = "_st";
    /**
     * Token分割符
     */
    protected static final String TOKEN_SEPARATOR   = SymbolConstant.COMMA;
    /**
     * 最多保存5个Token
     */
    protected static final int    maxTokens         = 5;
    /**
     * 单个token的长度
     */
    protected static final int    tokenLength       = 6;

    /**
     * 得到Token的key
     * 
     * @return
     */
    public static String getTokenKey() {
        return TOKEN_KEY;
    }

    /**
     * 生成Token
     * 
     * @return
     */
    public static String getToken(HttpServletRequest request, HttpServletResponse response) {
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
        String tokenFromRequest = request.getParameter(TOKEN_KEY);
        if (StringUtil.isBlank(tokenFromRequest)) {// request必须存在token
            return false;
        }
        List<String> tokensInSession = getTokensInStore(request, response);
        if (!tokensInSession.contains(tokenFromRequest)) {
            return false;
        } else {
            tokensInSession.remove(tokenFromRequest);
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
        String token = TokenStore.getToken(request, response, TOKEN_SESSION_KEY);
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
    private static void setTokensInStore(HttpServletRequest request, HttpServletResponse response, List<String> tokens) {
        if (tokens.isEmpty()) {
            TokenStore.removeToken(request, response, TOKEN_SESSION_KEY);
        } else {
            TokenStore.saveToken(request, response, TOKEN_SESSION_KEY, StringUtil.join(tokens, TOKEN_SEPARATOR));
        }
    }

    /**
     * 用于保存Token
     * 
     * @param args
     * @return
     */
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

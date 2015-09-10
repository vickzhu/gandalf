package com.gandalf.framework.web.session;

import com.gandalf.framework.requestcontext.RequestContext;

/**
 * sesion存储接口，建议将session model 和session attribute 分开存储，自定义定时器扫描session model，过期的session将model和attribute一同删除
 * 
 * @author gandalf 2014-3-31 上午11:21:31
 */
public interface SessionStore {

    /**
     * 查找session模型
     * 
     * @param sessionId
     * @return
     */
    public SessionModel findSessionModel(RequestContext requestContext, String sessionId);

    /**
     * 获得所有属性
     * 
     * @param sessionId
     * @return
     */
    public SessionAttribute getAttributes(RequestContext requestContext, String sessionId);

    /**
     * 这个方法需要将model和attribute同时删除
     * 
     * @param sessionId
     */
    public void invalidate(RequestContext requestContext, String sessionId);

    /**
     * 提交更改，最好根据sessionAttribute中的modified属性判断是否需要更改属性
     * 
     * @param sessionModel session模型
     * @param sessionAttribute session中保存的属性
     */
    public void commit(RequestContext requestContext, SessionModel sessionModel, SessionAttribute sessionAttribute);
}

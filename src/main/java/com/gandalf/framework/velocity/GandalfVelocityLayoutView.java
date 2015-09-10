package com.gandalf.framework.velocity;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.ToolManager;
import org.apache.velocity.tools.view.ViewToolContext;
import org.springframework.web.servlet.view.velocity.VelocityLayoutView;

import com.gandalf.framework.constant.SymbolConstant;

/**
 * 类GandelfVelocityLayoutView.java的实现描述：自定义Velocity布局视图,添加特性：
 * 
 * <pre>
 *  1、支持VelocityTool2.0
 *  2、根据目录自动匹配layout
 * </pre>
 * 
 * @author gandalf 2014-2-25 下午5:22:02
 */
public class GandalfVelocityLayoutView extends VelocityLayoutView {

    private static final String DEFAULT_LAYOUT_PRE = "layout";
    private static final String DEFAULT_VM         = "default.vm";

    @Override
    protected Context createVelocityContext(Map<String, Object> model, HttpServletRequest request,
                                            HttpServletResponse response) throws Exception {

        ViewToolContext ctx = new ViewToolContext(this.getVelocityEngine(), request, response, this.getServletContext());
        // velocity默认变量查找顺序:session-->toolbox-->servletAPI，每个变量输出都会先去sesion找一遍，下面相当于调整了顺序，将session放到最后查找
        ctx.setUserCanOverwriteTools(false);
        if (this.getToolboxConfigLocation() != null) {
            ToolManager tm = new ToolManager();
            tm.setVelocityEngine(this.getVelocityEngine());
            tm.configure(this.getServletContext().getRealPath(this.getToolboxConfigLocation()));

            for (String scope : Scope.values()) {
                ctx.addToolbox(tm.getToolboxFactory().createToolbox(scope));
            }
        }

        if (model != null && !model.isEmpty()) {
            ctx.putAll(model);
        }

        return ctx;
    }

    /**
     * Overrides the normal rendering process in order to pre-process the Context, merging it with the screen template
     * into a single value (identified by the value of screenContentKey). The layout template is then merged with the
     * modified Context in the super class.
     */
    @Override
    protected void doRender(Context context, HttpServletResponse response) throws Exception {
        String matchedLayoutUrl = matchLayoutUrl(this.getUrl());
        setLayoutUrl(matchedLayoutUrl);
        super.doRender(context, response);
    }

    /**
     * 向上搜索的模板映射规则。
     * <p>
     * 例如：将模板名：<code>"about/directions/driving.vm"</code>映射到layout template，将顺次搜索以下模板：
     * </p>
     * <ol>
     * <li><code>&quot;layout/about/directions/driving.vm&quot;</code></li>
     * <li><code>&quot;layout/about/directions/default.vm&quot;</code></li>
     * <li><code>&quot;layout/about/default.vm&quot;</code></li>
     * <li><code>&quot;layout/default.vm&quot;</code></li>
     * </ol>
     * 
     * @param screenUrl
     * @return
     */
    private String matchLayoutUrl(String screenUrl) {
        String layoutUrl = buildLayoutUrl(screenUrl);
        boolean exist = isExistLayout(layoutUrl);
        if (exist) {
            return layoutUrl;
        }

        while (screenUrl.lastIndexOf(SymbolConstant.SLASH) > 0) {
            screenUrl = screenUrl.substring(0, screenUrl.lastIndexOf(SymbolConstant.SLASH));
            layoutUrl = buildDefaultLayoutUrl(screenUrl);
            if (isExistLayout(layoutUrl)) {
                return layoutUrl;
            }
        }
        return buildDefaultLayoutUrl(null);
    }

    /**
     * 是否存在Layout
     * 
     * @param url
     * @return
     */
    private boolean isExistLayout(String url) {
        return getVelocityEngine().resourceExists(url);
    }

    /**
     * 构建Layout
     * 
     * @param url
     * @return
     */
    private String buildLayoutUrl(String url) {
        StringBuilder sb = new StringBuilder();
        sb.append(DEFAULT_LAYOUT_PRE);
        sb.append(SymbolConstant.SLASH);
        sb.append(url);
        return sb.toString();
    }

    /**
     * 构建默认Layout
     * 
     * @param url
     * @return
     */
    private String buildDefaultLayoutUrl(String url) {
        StringBuilder sb = new StringBuilder();
        sb.append(DEFAULT_LAYOUT_PRE);
        sb.append(SymbolConstant.SLASH);
        if (StringUtils.isNotBlank(url)) {
            sb.append(url);
            sb.append(SymbolConstant.SLASH);
        }
        sb.append(DEFAULT_VM);
        return sb.toString();
    }

}

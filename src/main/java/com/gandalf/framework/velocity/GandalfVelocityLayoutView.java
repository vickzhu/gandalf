package com.gandalf.framework.velocity;

import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.ToolManager;
import org.apache.velocity.tools.view.ViewToolContext;
import org.springframework.core.NestedIOException;
import org.springframework.web.servlet.view.velocity.VelocityToolboxView;

import com.gandalf.framework.constant.SymbolConstant;
import com.gandalf.framework.util.StringUtil;

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
public class GandalfVelocityLayoutView extends VelocityToolboxView {
	/**
	 * The default {@link #setLayoutUrl(String) layout url}.
	 */
	public static final String DEFAULT_LAYOUT_URL = "layout.vm";

	/**
	 * The default {@link #setLayoutKey(String) layout key}.
	 */
	public static final String DEFAULT_LAYOUT_KEY = "layout";

	/**
	 * The default {@link #setScreenContentKey(String) screen content key}.
	 */
	public static final String DEFAULT_SCREEN_CONTENT_KEY = "screen_content";


	private String layoutUrl = DEFAULT_LAYOUT_URL;

	private String layoutKey = DEFAULT_LAYOUT_KEY;

	private String screenContentKey = DEFAULT_SCREEN_CONTENT_KEY;
	
	private String viewUri;

	public String getViewUri() {
		return viewUri;
	}

	public void setViewUri(String viewUri) {
		this.viewUri = viewUri;
	}

	/**
	 * Set the layout template to use. Default is {@link #DEFAULT_LAYOUT_URL "layout.vm"}.
	 * @param layoutUrl the template location (relative to the template
	 * root directory)
	 */
	public void setLayoutUrl(String layoutUrl) {
		this.layoutUrl = layoutUrl;
	}

	/**
	 * Set the context key used to specify an alternate layout to be used instead
	 * of the default layout. Screen content templates can override the layout
	 * template that they wish to be wrapped with by setting this value in the
	 * template, for example:<br>
	 * <code>#set( $layout = "MyLayout.vm" )</code>
	 * <p>Default key is {@link #DEFAULT_LAYOUT_KEY "layout"}, as illustrated above.
	 * @param layoutKey the name of the key you wish to use in your
	 * screen content templates to override the layout template
	 */
	public void setLayoutKey(String layoutKey) {
		this.layoutKey = layoutKey;
	}

	/**
	 * Set the name of the context key that will hold the content of
	 * the screen within the layout template. This key must be present
	 * in the layout template for the current screen to be rendered.
	 * <p>Default is {@link #DEFAULT_SCREEN_CONTENT_KEY "screen_content"}:
	 * accessed in VTL as <code>$screen_content</code>.
	 * @param screenContentKey the name of the screen content key to use
	 */
	public void setScreenContentKey(String screenContentKey) {
		this.screenContentKey = screenContentKey;
	}


	/**
	 * Overrides <code>VelocityView.checkTemplate()</code> to additionally check
	 * that both the layout template and the screen content template can be loaded.
	 * Note that during rendering of the screen content, the layout template
	 * can be changed which may invalidate any early checking done here.
	 */
	@Override
	public boolean checkResource(Locale locale) throws Exception {
		if (!super.checkResource(locale)) {
			return false;
		}

		try {
			// Check that we can get the template, even if we might subsequently get it again.
			getTemplate(this.layoutUrl);
			return true;
		}
		catch (ResourceNotFoundException ex) {
			throw new NestedIOException("Cannot find Velocity template for URL [" + this.layoutUrl +
					"]: Did you specify the correct resource loader path?", ex);
		}
		catch (Exception ex) {
			throw new NestedIOException(
					"Could not load Velocity template for URL [" + this.layoutUrl + "]", ex);
		}
	}

	/**
	 * Overrides the normal rendering process in order to pre-process the Context,
	 * merging it with the screen template into a single value (identified by the
	 * value of screenContentKey). The layout template is then merged with the
	 * modified Context in the super class.
	 */
	@Override
	protected void doRender(Context context, HttpServletResponse response) throws Exception {
		renderScreenContent(context);

		// Velocity context now includes any mappings that were defined
		// (via #set) in screen content template.
		// The screen template can overrule the layout by doing
		// #set( $layout = "MyLayout.vm" )
		String layoutUrlToUse = (String) context.get(this.layoutKey);
		if (layoutUrlToUse != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Screen content template has requested layout [" + layoutUrlToUse + "]");
			}
		}
		else {
			// No explicit layout URL given -> use default layout of this view.
			layoutUrlToUse = matchLayoutUrl();
		}

		mergeTemplate(getTemplate(layoutUrlToUse), context, response);
	}

	/**
	 * The resulting context contains any mappings from render, plus screen content.
	 */
	private void renderScreenContent(Context velocityContext) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Rendering screen content template [" + getUrl() + "]");
		}

		StringWriter sw = new StringWriter();
		Template screenContentTemplate = getTemplate(getUrl());
		screenContentTemplate.merge(velocityContext, sw);

		// Put rendered content into Velocity context.
		velocityContext.put(this.screenContentKey, sw.toString());
	}

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
    private String matchLayoutUrl() {
//    	//加上这段的话会默认匹配文件名，但是为了效率，去掉文件名匹配，直接匹配default.vm
//    	String layoutUrl = buildLayoutUrl(viewUri);//构建全路径
//        boolean exist = isExistLayout(layoutUrl);
//        if (exist) {
//            return layoutUrl;
//        }
        while(viewUri.length() > 0){
        	if(viewUri.lastIndexOf(SymbolConstant.SLASH) > 0){
        		viewUri = viewUri.substring(0, viewUri.lastIndexOf(SymbolConstant.SLASH));
        	} else {
        		viewUri = StringUtil.EMPTY;
        	}
        	layoutUrl = buildDefaultLayoutUrl(viewUri);
        	if (isExistLayout(layoutUrl)) {
                return layoutUrl;
            }
        }
        return null;
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
    
    private static final String DEFAULT_LAYOUT_PRE = "layout";
    private static final String DEFAULT_VM         = "default.vm";

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

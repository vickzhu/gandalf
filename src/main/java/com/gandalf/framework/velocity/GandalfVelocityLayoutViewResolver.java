package com.gandalf.framework.velocity;

import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.velocity.VelocityLayoutView;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

/**
 * 该类对<code>VelocityLayoutViewResolver</code>进行了改造
 * <ol>
 * <li>将默认渲染的VelocityLayoutView替换为GandalfVelocityLayoutView</li>
 * <li>去掉layoutUrl配置,并根据viewName传入到GandalfViewLayoutView</li>
 * </ol>
 * @author gandalf 2016-3-22 下午3:25:32
 *
 */
public class GandalfVelocityLayoutViewResolver extends VelocityViewResolver {
	
	private String layoutUrl;

	private String layoutKey;

	private String screenContentKey;


	/**
	 * Requires VelocityLayoutView.
	 * @see VelocityLayoutView
	 */
	@Override
	protected Class requiredViewClass() {
		return GandalfVelocityLayoutView.class;
	}

	/**
	 * Set the layout template to use. Default is "layout.vm".
	 * @param layoutUrl the template location (relative to the template
	 * root directory)
	 * @see VelocityLayoutView#setLayoutUrl
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
	 * <p>The default key is "layout", as illustrated above.
	 * @param layoutKey the name of the key you wish to use in your
	 * screen content templates to override the layout template
	 * @see VelocityLayoutView#setLayoutKey
	 */
	public void setLayoutKey(String layoutKey) {
		this.layoutKey = layoutKey;
	}

	/**
	 * Set the name of the context key that will hold the content of
	 * the screen within the layout template. This key must be present
	 * in the layout template for the current screen to be rendered.
	 * <p>Default is "screen_content": accessed in VTL as
	 * <code>$screen_content</code>.
	 * @param screenContentKey the name of the screen content key to use
	 * @see VelocityLayoutView#setScreenContentKey
	 */
	public void setScreenContentKey(String screenContentKey) {
		this.screenContentKey = screenContentKey;
	}


	@Override
	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		GandalfVelocityLayoutView view = (GandalfVelocityLayoutView) super.buildView(viewName);
		// Use not-null checks to preserve VelocityLayoutView's defaults.
		view.setViewUri(viewName+getSuffix());
		if (this.layoutUrl != null) {
			view.setLayoutUrl(this.layoutUrl);
		}
		if (this.layoutKey != null) {
			view.setLayoutKey(this.layoutKey);
		}
		if (this.screenContentKey != null) {
			view.setScreenContentKey(this.screenContentKey);
		}
		return view;
	}
	
}

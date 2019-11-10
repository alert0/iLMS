package com.hotent.mini.web.tag;

import java.util.Map;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * 功能权限标签
 * @author Administrator
 * 
 */
@SuppressWarnings("serial")
public class AnchorTag extends BodyTagSupport {
	/** 链接的样式class */
	private String css;
	/** 链接的 别名 */
	private String alias;
	/** 链接的 name */
	private String name;
	/** 链接的 name */
	private String id;
	/** 链接的 href */
	private String href;
	/** 链接的 action */
	private String action;
	/** 链接的 onclick */
	private String onclick;
	/** 链接的 目标 */
	private String target;

	/**
	 * 当没有权限的时候超链接是否显示。
	 */
	private boolean showNoRight = true;
	private static ThreadLocal<Map<String, Boolean>> threadLocalVar = new ThreadLocal<Map<String, Boolean>>();

	public static void cleanFuncRights() {
		threadLocalVar.remove();
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public void setShowNoRight(boolean _isShowNoRight) {
		this.showNoRight = _isShowNoRight;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public int doStartTag() throws JspTagException {
		return EVAL_BODY_BUFFERED;
	}

	/**
	 * 是否有权限
	 * 
	 * @param systemAlias
	 *            系统别名
	 * @return
	 */
	protected boolean getHasRights() {
		//TODO 在此处判断是否拥有访问权限
		return true;
	}

	/**
	 * 输出标签
	 * 
	 * @return
	 * @throws Exception
	 */
	private String getOutput() throws Exception {
		boolean canAccess = getHasRights();
		/**
		 * 当没有权限不显示,返回空串。
		 */
		if (!showNoRight && !canAccess)
			return "";

		String body = this.getBodyContent().getString();
		StringBuffer content = new StringBuffer("<a ");

		if (id != null) // ID
			content.append(" id=\"" + id + "\" ");
		if (name != null) // name
			content.append(" name=\"" + name + "\" ");
		if (target != null) // 目标
			content.append(" target=\"" + target + "\" ");

		// 可以访问的情况。
		if (canAccess) {
			if (css != null)
				content.append(" class=\"" + css + "\" ");
			if (href != null)
				content.append(" href=\"" + href + "\" ");
			if (action != null)
				content.append(" action=\"" + action + "\" ");
			if (onclick != null)
				content.append(" onclick=\"" + onclick + "\" ");
		} else {
			if (css != null)
				content.append(" class=\"" + css + " disabled\" ");
			else
				content.append(" class=\"disabled\" ");
			// 拼接没权限
			content.append(" onclick=\"$.noRight();\" ");
		}

		content.append(">").append(body).append("</a>");

		return content.toString();
	}

	public int doEndTag() throws JspTagException {
		try {
			String str = getOutput();
			pageContext.getOut().print(str);
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException(e.getMessage());
		}
		return SKIP_BODY;
	}
}

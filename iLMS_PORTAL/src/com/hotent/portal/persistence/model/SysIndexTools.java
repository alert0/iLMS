package com.hotent.portal.persistence.model;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：首页工具 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:mouhb
 * 邮箱:mouhb@jee-soft.cn
 * 日期:2017-08-06 12:45:45
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class SysIndexTools extends AbstractModel<String>{
	/**首页工具权限*/
	public static final String INDEX_TOOLS="indexTools";
	public static final short NOT_STATISTICS = 0;
	public static final short SERVICE = 1;
	public static final short SLEF_QUERY = 2;
	
	/**
	* ID
	*/
	protected String id; 
	
	/**
	* 名称
	*/
	protected String name; 
	
	/**
	* 图标
	*/
	protected String icon; 
	
	/**
	* 链接
	*/
	protected String url; 
	
	/**
	* 类型
	*/
	protected String type; 
	
	/**
	* 统计模式(0--不统计，1--service，2-自定义查询
	*/
	protected Short countMode; 
	
	/**
	* 统计算法
	*/
	protected String counting; 
	/**
	* 统计参数
	*/
	protected String countParam; 
	
	/**
	* 创建人
	*/
	protected String createBy; 
	
	/**
	* 创建时间
	*/
	protected java.util.Date createTime;
	
	protected Long statistics;
	
	/**
	 * 字体样式
	 */
	protected String fontStyle;
	
	/**
	 * 统计数字样式
	 */
	protected String numberStyle;
	
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 返回 ID
	 * @return
	 */
	public String getId() {
		return this.id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 返回 名称
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	/**
	 * 返回 图标
	 * @return
	 */
	public String getIcon() {
		return this.icon;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * 返回 链接
	 * @return
	 */
	public String getUrl() {
		return this.url;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * 返回 类型
	 * @return
	 */
	public String getType() {
		return this.type;
	}
	
	public void setCountMode(Short countMode) {
		this.countMode = countMode;
	}
	
	/**
	 * 返回 统计模式(0--不统计，1--service，2-自定义查询
	 * @return
	 */
	public Short getCountMode() {
		return this.countMode;
	}
	
	public void setCounting(String counting) {
		this.counting = counting;
	}
	
	/**
	 * 返回 统计算法
	 * @return
	 */
	public String getCounting() {
		return this.counting;
	}
	
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
	
	/**
	 * 返回 统计参数
	 * @return
	 */
	public String getCountParam() {
		return countParam;
	}

	public void setCountParam(String countParam) {
		this.countParam = countParam;
	}

	/**
	 * 返回 创建人
	 * @return
	 */
	public String getCreateBy() {
		return this.createBy;
	}
	
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * 返回 创建时间
	 * @return
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public Long getStatistics() {
		return statistics;
	}

	public void setStatistics(Long statistics) {
		this.statistics = statistics;
	}

	
	public String getFontStyle() {
		return fontStyle;
	}

	public void setFontStyle(String fontStyle) {
		this.fontStyle = fontStyle;
	}

	public String getNumberStyle() {
		return numberStyle;
	}

	public void setNumberStyle(String numberStyle) {
		this.numberStyle = numberStyle;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("name", this.name) 
		.append("icon", this.icon) 
		.append("url", this.url) 
		.append("type", this.type) 
		.append("countMode", this.countMode) 
		.append("counting", this.counting) 
		.append("countParam", this.countParam) 
		.append("createBy", this.createBy) 
		.append("createTime", this.createTime) 
		.toString();
	}
}
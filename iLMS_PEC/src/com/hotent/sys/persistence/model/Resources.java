package com.hotent.sys.persistence.model;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:sys_res_resources entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zyp
 * 创建时间:2014-03-04 15:20:24
 */
public class Resources extends AbstractModel<String> implements Cloneable{
   //LOGO路径
	public final static String LOGO_PATH="/styles/theme/default/images/logo";
	//ICON路径
	public final static String ICON_PATH="/styles/theme/default/fonts/fonts.json";
	
	//Min ICON路径
	public final static String ICON_MIN_PATH="/fonts/fonts.json";
	//图标类型
	public final static String ICON_TYPE = "PNG|JPG|JPEG|GIF";
	//扩展的功能按钮图标路径
	public final static String ICON_EXTEND="/styles/default/images/extendIcon";
	//扩展功能样式文件位置
	public final static String EXTEND_CSS_PATH="/styles/default/css/form.css";
	
	public final static String ICON_DEFAULT_FOLDER="/styles/default/images/icon/tree_folder.gif";
	public final static String ICON_DEFAULT_LEAF="/styles/default/images/icon/tree_file.gif";
	
	public final static String IS_CHECKED_N="false";
	public final static String IS_CHECKED_Y="true";
	
	/**
	 * 根结点的父ID
	 */
	public final static String ROOT_PID="-1";//重要
	/**
	 * 根结点的ID
	 */
	public final static String ROOT_ID="0";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String  id; /*主键*/
	protected String  name; /*资源名称*/
	protected String  alias; /*别名*/
	protected String  sn; /*序号*/
	protected String  icon; /*图标*/
	protected String  parentId; /*父节点*/
	protected String  defaultUrl; /*默认的URL*/
	protected Boolean  isFolder; /*是否目录Y,N*/
	protected Boolean  displayInMenu; /*是否显示到菜单Y,N*/
	protected Boolean  isOpen; /*默认展开Y,N*/
	protected String  systemId; /*子系统ID*/
	protected String  path; /*资源路径*/
	protected String checked=IS_CHECKED_N;/*是否选中*/
	protected List<ResUrl> resUrlList;
	
	public void setId(String id) 
	{
		this.id = id;
	}
	/**
	 * 返回 主键
	 * @return
	 */
	public String getId() 
	{
		return this.id;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 资源名称
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setAlias(String alias) 
	{
		this.alias = alias;
	}
	/**
	 * 返回 别名
	 * @return
	 */
	public String getAlias() 
	{
		return this.alias;
	}
	public void setSn(String sn) 
	{
		this.sn = sn;
	}
	/**
	 * 返回 序号
	 * @return
	 */
	public String getSn() 
	{
		return this.sn;
	}
	public void setIcon(String icon) 
	{
		this.icon = icon;
	}
	/**
	 * 返回 图标
	 * @return
	 */
	public String getIcon() 
	{
		return this.icon;
	}
	public void setParentId(String parentId) 
	{
		this.parentId = parentId;
	}
	/**
	 * 返回 父节点
	 * @return
	 */
	public String getParentId() 
	{
		return this.parentId;
	}
	public void setDefaultUrl(String defaultUrl) 
	{
		this.defaultUrl = defaultUrl;
	}
	/**
	 * 返回 默认的URL
	 * @return
	 */
	public String getDefaultUrl() 
	{
		return this.defaultUrl;
	}
	public void setIsFolder(Boolean isFolder) 
	{
		this.isFolder = isFolder;
	}
	/**
	 * 返回 是否目录Y,N
	 * @return
	 */
	public Boolean getIsFolder() 
	{
		return this.isFolder;
	}
	public void setDisplayInMenu(Boolean displayInMenu) 
	{
		this.displayInMenu = displayInMenu;
	}
	/**
	 * 返回 是否显示到菜单Y,N
	 * @return
	 */
	public Boolean getDisplayInMenu() 
	{
		return this.displayInMenu;
	}
	public void setIsOpen(Boolean isOpen) 
	{
		this.isOpen = isOpen;
	}
	/**
	 * 返回 默认展开Y,N
	 * @return
	 */
	public Boolean getIsOpen() 
	{
		return this.isOpen;
	}
	public void setSystemId(String systemId) 
	{
		this.systemId = systemId;
	}
	/**
	 * 返回 子系统ID
	 * @return
	 */
	public String getSystemId() 
	{
		return this.systemId;
	}
	public void setPath(String path) 
	{
		this.path = path;
	}
	/**
	 * 返回 资源路径
	 * @return
	 */
	public String getPath() 
	{
		return this.path;
	}
	public List<ResUrl> getResUrlList() {
		return resUrlList;
	}
	public void setResUrlList(List<ResUrl> resUrlList) {
		this.resUrlList = resUrlList;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("name", this.name) 
		.append("alias", this.alias) 
		.append("sn", this.sn) 
		.append("icon", this.icon) 
		.append("parentId", this.parentId) 
		.append("defaultUrl", this.defaultUrl) 
		.append("isFolder", this.isFolder) 
		.append("displayInMenu", this.displayInMenu) 
		.append("isOpen", this.isOpen) 
		.append("systemId", this.systemId) 
		.append("path", this.path) 
		.toString();
	}
}
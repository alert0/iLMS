package com.hotent.sys.persistence.model;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;
import com.hotent.base.core.util.PropertyUtil;

/**
 * 
 * <pre>
 * 描述：电子印章 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:liyj
 * 邮箱:lyj@jee-soft.cn
 * 日期:2014-11-12 10:14:37
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class SysSeal extends AbstractModel<String> {
	protected String id; /* 主键 */
	protected String sealName; /* 印章名称 */
	protected String belongName; /* 所属用户名称 */
	protected String sealPath; /* 保存路径 */
	protected String sealExt; /* 印章格式 */
	protected String sealSize; /* 印章大小 */
	protected byte[] sealStream; /* 印章二进制流 */
	protected String sealDesc; /* 印章说明 */
	protected String showImagePath; /* 印章展示图片主键 */
	protected String type; /* 类型 */
	protected String creator; /* 创建者ID */
	protected Date createTime; /* 创建时间 */
	protected String saveType; /* 类型 */

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 返回 主键
	 * 
	 * @return
	 */
	public String getId() {
		return this.id;
	}

	public void setSealName(String sealName) {
		this.sealName = sealName;
	}

	/**
	 * 返回 印章名称
	 * 
	 * @return
	 */
	public String getSealName() {
		return this.sealName;
	}

	public void setBelongName(String belongName) {
		this.belongName = belongName;
	}

	/**
	 * 返回 所属用户名称
	 * 
	 * @return
	 */
	public String getBelongName() {
		return this.belongName;
	}

	public void setSealPath(String sealPath) {
		this.sealPath = sealPath;
	}

	/**
	 * 返回 保存路径
	 * 
	 * @return
	 */
	public String getSealPath() {
		String path = "";
		return path+this.sealPath;
	}
	
	/**
	 * 返回 保存的绝对路径
	 * 
	 * @return
	 */
	public String getAbsoluteSealPath() {
		String path = PropertyUtil.getProperty("seal.upload") + "\\sign\\";
		return path+this.sealPath;
	}

	public void setSealExt(String sealExt) {
		this.sealExt = sealExt;
	}

	/**
	 * 返回 印章格式
	 * 
	 * @return
	 */
	public String getSealExt() {
		return this.sealExt;
	}

	public void setSealSize(String sealSize) {
		this.sealSize = sealSize;
	}

	/**
	 * 返回 印章大小
	 * 
	 * @return
	 */
	public String getSealSize() {
		return this.sealSize;
	}

	public void setSealStream(byte[] sealStream) {
		this.sealStream = sealStream;
	}

	/**
	 * 返回 印章二进制流
	 * 
	 * @return
	 */
	public byte[] getSealStream() {
		return this.sealStream;
	}

	public void setSealDesc(String sealDesc) {
		this.sealDesc = sealDesc;
	}

	/**
	 * 返回 印章说明
	 * 
	 * @return
	 */
	public String getSealDesc() {
		return this.sealDesc;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 返回 类型
	 * 
	 * @return
	 */
	public String getType() {
		return this.type;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * 返回 创建者
	 * 
	 * @return
	 */
	public String getCreator() {
		return this.creator;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 返回 创建时间
	 * 
	 * @return
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	/**
	 * showImagePath
	 * @return  the showImagePath
	 * @since   1.0.0
	 */
	
	public String getShowImagePath() {
		String path = "";
		return path+showImagePath;
	}
	
	/**
	 * showImagePath	:返回图片的绝对路径
	 * @return  the showImagePath
	 * @since   1.0.0
	 */
	
	public String getAbsoluteShowImagePath() {
		String path = PropertyUtil.getProperty("seal.upload") + "\\img\\";
		return path+showImagePath;
	}

	/**
	 * @param showImagePath the showImagePath to set
	 */
	public void setShowImagePath(String showImagePath) {
		this.showImagePath = showImagePath;
	}

	/**
	 * saveType
	 * @return  the saveType
	 * @since   1.0.0
	 */
	
	public String getSaveType() {
		return saveType;
	}

	/**
	 * @param saveType the saveType to set
	 */
	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).append("sealName", this.sealName).append("belongName", this.belongName).append("sealPath", this.sealPath).append("sealExt", this.sealExt).append("sealSize", this.sealSize).append("sealStream", this.sealStream).append("sealDesc", this.sealDesc).append("showImagePath", this.showImagePath).append("type", this.type).append("creator", this.creator).append("createTime", this.createTime).toString();
	}
}
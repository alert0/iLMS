package com.hotent.sys.persistence.model;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.PropertyUtil;

/**
 * 
 * <pre>
 * 描述：OFFICE模版表 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:liyj
 * 邮箱:lyj@jee-soft.cn
 * 日期:2014-10-31 17:46:00
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class SysOfficeTemplate extends AbstractModel<String> {
	// 文件保存类型
	public class SaveType {
		public static final String DB = "db";// 数据库保存
		public static final String FILE = "file";// 文件保存
	}

	protected String id; /* 主键 */
	protected String name; /* 模板名称 */
	protected String path; /* 保存路径 */
	protected String ext; /* 模板格式 */
	protected String size; /* 模板大小 */
	protected byte[] stream; /* 模板二进制流 */
	protected String desc; /* 模板说明 */
	protected String type; /* 类型(印章，office文档) */
	protected String createBy; /* 创建者 */
	protected java.util.Date createTime; /* 创建时间 */
	protected String saveType; /* 保存类型: Folder,文件 Database，数据库 */

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

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回 模板名称
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 返回 保存路径
	 * 
	 * @return
	 */
	public String getPath() {
		return this.path;
	}
	
	/**
	 * 返回 保存绝对路径
	 * 
	 * @return
	 */
	public String getAbsolutePath() {
		String path = PropertyUtil.getProperty("officetemplate.upload");
		return path+this.path;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	/**
	 * 返回 模板格式
	 * 
	 * @return
	 */
	public String getExt() {
		return this.ext;
	}

	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * 返回 模板大小
	 * 
	 * @return
	 */
	public String getSize() {
		return this.size;
	}

	public void setStream(byte[] stream) {
		this.stream = stream;
	}

	/**
	 * 返回 模板二进制流
	 * 
	 * @return
	 */
	public byte[] getStream() {
		return this.stream;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * 返回 模板说明
	 * 
	 * @return
	 */
	public String getDesc() {
		return this.desc;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 返回 类型(印章，office文档)
	 * 
	 * @return
	 */
	public String getType() {
		return this.type;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * 返回 创建者
	 * 
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
	 * 
	 * @return
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}

	/**
	 * 返回 保存类型: Folder,文件 Database，数据库
	 * 
	 * @return
	 */
	public String getSaveType() {
		return this.saveType;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).append("name", this.name).append("path", this.path).append("ext", this.ext).append("size", this.size).append("stream", this.stream).append("desc", this.desc).append("type", this.type).append("createBy", this.createBy).append("createTime", this.createTime).append("saveType", this.saveType).toString();
	}
}
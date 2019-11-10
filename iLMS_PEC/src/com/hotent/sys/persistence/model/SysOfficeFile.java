package com.hotent.sys.persistence.model;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 
 * <pre>
 * 描述：在线文档和WEB签章业务表 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2014-10-31 09:28:06
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class SysOfficeFile extends AbstractModel<String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2745484141262736776L;

	protected String id; /* 主键 */
	protected String fileName; /* 文件名称 */
	protected String filePath; /* 文件路径 */
	protected String fileExt; /* 文件格式 */
	protected Long fileSize; /* 文件大小 */
	protected byte[] fileStream; /* 文件二进制流 */
	protected String description; /* 文件说明 */
	protected String type; /* 类型(office或者webSign) */
	protected String createBy; /* 创建者 */
	protected java.util.Date createTime; /* 创建时间 */

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

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 返回 文件名称
	 * 
	 * @return
	 */
	public String getFileName() {
		return this.fileName;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * 返回 文件路径
	 * 
	 * @return
	 */
	public String getFilePath() {
		return this.filePath;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	/**
	 * 返回 文件格式
	 * 
	 * @return
	 */
	public String getFileExt() {
		return this.fileExt;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * 返回 文件大小
	 * 
	 * @return
	 */
	public Long getFileSize() {
		return this.fileSize;
	}

	public void setFileStream(byte[] fileStream) {
		this.fileStream = fileStream;
	}

	/**
	 * 返回 文件二进制流
	 * 
	 * @return
	 */
	public byte[] getFileStream() {
		return this.fileStream;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 返回 文件说明
	 * 
	 * @return
	 */
	public String getDescription() {
		return this.description;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 返回 类型(office或者webSign)
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

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id)
				.append("fileName", this.fileName)
				.append("filePath", this.filePath)
				.append("fileExt", this.fileExt)
				.append("fileSize", this.fileSize)
				.append("fileStream", this.fileStream)
				.append("description", this.description)
				.append("type", this.type).append("createBy", this.createBy)
				.append("createTime", this.createTime).toString();
	}
}
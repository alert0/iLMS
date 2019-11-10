package com.hotent.sys.persistence.model;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.api.BaseModel;
import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:附件 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:miao
 * 创建时间:2014-04-04 08:54:40
 */
public class DefaultFile extends AbstractModel<String>{
	
	/** * 文件已经删除 [value=1]*/
	public static Short FILE_DEL = 1;
	/** * 文件存在[value=0] */
	public static Short FILE_NOT_DEL = 0;
	/**  * 匿名用户  */
	public static String FILE_UPLOAD_UNKNOWN = "unknown";
	/**  * 其他分类  */
	public static String FILETYPE_OTHERS="others";
	/**附件类型*/
	public static String FILE_TYPE_MAIL="mail";
	public static String FILE_TYPE_USER="user";
	/**存储类型*/
	public static String FILE_STORE_DISK="disk";
	public static String FILE_STORE_DB="db";
	public static String FILE_STORE_FTP = "ftp";
	
	protected String  id; /*主键*/
	protected String  xbTypeId; /*附件分类ID*/
	protected String  fileName; /*文件名*/
	protected String  fileType; /*附件类型。如：mail=邮件附件；user=用户信息附件*/
	protected String  storeType; /*存储类型。DISK=基于磁盘；DB=基于数据库；BOTH=两者*/
	protected String  filePath; /*文件路径*/
	protected byte[]  bytes; /*文件二进制数据*/
	protected Long  byteCount; /*总字节数*/
	protected String  ext; /*扩展名*/
	protected String  note; /*说明*/
	protected String  creator; /*上传者ID*/
	protected String  creatorName; /*上传者*/
	protected java.util.Date  createTime; /*创建时间*/
	protected Short isDel; /*删除标识*/
	
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
	public void setXbTypeId(String xbTypeId) 
	{
		this.xbTypeId = xbTypeId;
	}
	/**
	 * 返回 附件分类ID
	 * @return
	 */
	public String getXbTypeId() 
	{
		return this.xbTypeId;
	}
	public void setFileName(String fileName) 
	{
		this.fileName = fileName;
	}
	/**
	 * 返回 文件名
	 * @return
	 */
	public String getFileName() 
	{
		return this.fileName;
	}
	public void setFileType(String fileType) 
	{
		this.fileType = fileType;
	}
	/**
	 * 返回 附件类型。如：mail=邮件附件；user=用户信息附件
	 * @return
	 */
	public String getFileType() 
	{
		return this.fileType;
	}
	public void setStoreType(String storeType) 
	{
		this.storeType = storeType;
	}
	/**
	 * 返回 存储类型。DISK=基于磁盘；DB=基于数据库；BOTH=两者
	 * @return
	 */
	public String getStoreType() 
	{
		return this.storeType;
	}
	public void setFilePath(String filePath) 
	{
		this.filePath = filePath;
	}
	/**
	 * 返回 文件路径
	 * @return
	 */
	public String getFilePath() 
	{
		return this.filePath;
	}
	public void setBytes(byte[] bytes) 
	{
		this.bytes = bytes;
	}
	/**
	 * 返回 文件二进制数据
	 * @return
	 */
	public byte[] getBytes() 
	{
		return this.bytes;
	}
	public void setByteCount(Long byteCount) 
	{
		this.byteCount = byteCount;
	}
	/**
	 * 返回 总字节数
	 * @return
	 */
	public Long getByteCount() 
	{
		return this.byteCount;
	}
	public void setExt(String ext) 
	{
		this.ext = ext;
	}
	/**
	 * 返回 扩展名
	 * @return
	 */
	public String getExt() 
	{
		return this.ext;
	}
	public void setNote(String note) 
	{
		this.note = note;
	}
	/**
	 * 返回 说明
	 * @return
	 */
	public String getNote() 
	{
		return this.note;
	}
	public void setCreator(String creator) 
	{
		this.creator = creator;
	}
	/**
	 * 返回 上传者ID
	 * @return
	 */
	public String getCreator() 
	{
		return this.creator;
	}
	public void setCreatorName(String creatorName) 
	{
		this.creatorName = creatorName;
	}
	/**
	 * 返回 上传者
	 * @return
	 */
	public String getCreatorName() 
	{
		return this.creatorName;
	}
	public void setCreateTime(java.util.Date createTime) 
	{
		this.createTime = createTime;
	}
	/**
	 * 返回 创建时间
	 * @return
	 */
	public java.util.Date getCreateTime() 
	{
		return this.createTime;
	}
	public void setIsDel(Short isDel) 
	{
		this.isDel = isDel;
	}
	/**
	 * 返回 删除标识
	 * @return
	 */
	public Short getIsDel() 
	{
		return this.isDel;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("xbTypeId", this.xbTypeId) 
		.append("fileName", this.fileName) 
		.append("fileType", this.fileType) 
		.append("storeType", this.storeType) 
		.append("filePath", this.filePath) 
		.append("bytes", this.bytes) 
		.append("byteCount", this.byteCount) 
		.append("ext", this.ext) 
		.append("note", this.note) 
		.append("creator", this.creator) 
		.append("creatorName", this.creatorName) 
		.append("createTime", this.createTime) 
		.append("isDel", this.isDel) 
		.toString();
	}
}
package com.hanthink.sw.model;


public class FileInfoModel extends SwAnnounceModel{

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 5559985093069757667L;
	/**文件id**/
	private String fileId;
	/**文件名**/
	private String fileName;
	/**业务文件编码***/
	private String fileType;
	/**文件格式**/
	private String fileFormat;
	/**文件服务器**/
	private String serverId;
	/**相对路径**/
	private String realPath;
	/**文件大小**/
	private String fileSize;
	/**操作IP**/
	private String opeIp;
	/**描述**/
	private String description;
	
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileFormat() {
		return fileFormat;
	}
	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	public String getRealPath() {
		return realPath;
	}
	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getOpeIp() {
		return opeIp;
	}
	public void setOpeIp(String opeIp) {
		this.opeIp = opeIp;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}

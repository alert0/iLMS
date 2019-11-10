package com.hanthink.gps.pub.vo;

import java.io.File;

import com.hanthink.gps.pub.vo.BaseVO;

public class ExcelImportVO extends BaseVO{
private static final long serialVersionUID = -5761563430213565522L;

	
	private File attachment;
	
	private String fileName;

	public File getAttachment() {
		return attachment;
	}

	public String getFileName() {
		return fileName;
	}

	public void setAttachment(File attachment) {
		this.attachment = attachment;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}

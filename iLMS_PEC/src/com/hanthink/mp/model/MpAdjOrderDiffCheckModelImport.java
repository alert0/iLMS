package com.hanthink.mp.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @Desc    : 计划对比调整差异导入Model
 * @CreateOn: 2019年1月26日
 * @author  : WY
 *
 * @ChangeList
 * Date			  Version		   Editor		     ChangeReasons
 *
 */
public class MpAdjOrderDiffCheckModelImport extends MpAdjOrderDiffCheckModel {
	
	/**
     * 
     */
    private static final long serialVersionUID = -3644918350353655988L;
    /** 导入UUID */
	private String uuid;
	/** 检查结果(0错误;1：通过;2:重复存在) */
	private String checkResult;
	/** 检查结果信息 */
	private String checkInfo;
	/** 导入状态 */
	private String importStatus;
		
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	public String getCheckInfo() {
		return checkInfo;
	}
	public void setCheckInfo(String checkInfo) {
		this.checkInfo = checkInfo;
	}
	public String getImportStatus() {
		return importStatus;
	}
	public void setImportStatus(String importStatus) {
		this.importStatus = importStatus;
	}
}

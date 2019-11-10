package com.hanthink.mon.model;

/**
 * 
 * @Desc : 允许误差 Excel导入类
 * @FileName : MonAllowDeviationModelImport.java
 * @author Midnight
 * @date 2018年11月11日
 */
public class MonAllowDeviationModelImport extends MonAllowDeviationModel {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 539213240084183226L;
	/** 导入UUID */
	private String uuid;
	/** 检查结果(0错误;1：通过;2:重复存在) */
	private String checkResult;
	/** 检查结果信息 */
	private String checkInfo;
	/** 导入状态 */
	private String importStatus;
	/** 操作类型状态 */
	private String opeType;
	
	private String codeValueNameB;
	private String codeValueNameC;

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

	public String getOpeType() {
		return opeType;
	}

	public void setOpeType(String opeType) {
		this.opeType = opeType;
	}

	/**
	 * 零件剩余量主数据导入数据检查
	 * 
	 * @param vo
	 */
	public static void checkImportData(MonAllowDeviationModelImport m) {
		StringBuffer checkInfo = new StringBuffer();

		if (m.getFactoryCode() == null || "".equals(m.getFactoryCode())) {
			checkInfo.append("工厂代码为空;");
		}
		if (m.getErrorDate() == null || "".equals(m.getErrorDate())) {
			checkInfo.append("误差时间为空;");
		}
		if (m.getRouteCode() == null || "".equals(m.getRouteCode())) {
			checkInfo.append("集货路线为空;");
		}
		if (checkInfo == null || "".equals(checkInfo.toString())) {
			m.setCheckResult("1");
			m.setCheckInfo("");
		} else {
			m.setCheckResult("0");
			m.setCheckInfo(checkInfo.toString());
		}
	}

	public String getCodeValueNameB() {
		return codeValueNameB;
	}

	public void setCodeValueNameB(String codeValueNameB) {
		this.codeValueNameB = codeValueNameB;
	}

	public String getCodeValueNameC() {
		return codeValueNameC;
	}

	public void setCodeValueNameC(String codeValueNameC) {
		this.codeValueNameC = codeValueNameC;
	}
}

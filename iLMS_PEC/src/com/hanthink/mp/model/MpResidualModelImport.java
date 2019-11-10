package com.hanthink.mp.model;

/**
 * 
 * @Desc		: 剩余量主数据  Excel导入类
 * @FileName	: MpResidualModelImport.java
 * @CreateOn	: 2018年9月10日 上午10:30:10
 * @author 		: linzhuo
 *
 *
 * @ChangeList
 * Date				Version		Editor		Reasons
 * 2018年9月2日		V1.0		linzhuo		新建
 */
public class MpResidualModelImport extends MpResidualModel {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 539213240084183226L;
	/** 导入UUID */
	private String  uuid;
	/** 检查结果(0错误;1：通过;2:重复存在) */
	private String  checkResult;
	/** 检查结果信息 */
	private String  checkInfo;
	/** 导入状态 */
	private String  importStatus;
	/** 操作类型状态 */
	private String  opeType;
	
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
     * @param vo
     * @author linzhuo  2018-9-12
     */
    public static void checkImportData(MpResidualModelImport m) {
        StringBuffer checkInfo = new StringBuffer();
        
        if(m.getFactoryCode()== null || "".equals(m.getFactoryCode())){
            checkInfo.append("工厂代码为空;");
        }
        if(m.getPartNo() == null || "".equals(m.getPartNo())){
            checkInfo.append("零件号为空;");
        }
        if(m.getSupplierNo() == null || "".equals(m.getSupplierNo())){
            checkInfo.append("供应商代码为空;");
        }
        if(m.getSupFactory() == null || "".equals(m.getSupFactory())){
            checkInfo.append("出货地代码为空;");
        }
        if(m.getUnloadPort() == null || "".equals(m.getUnloadPort())){
            checkInfo.append("计算队列为空;");
        }
        
        if(checkInfo == null || "".equals(checkInfo.toString())){
            m.setCheckResult("1");
            m.setOpeType("I");
            m.setCheckInfo("");
        }else{
            m.setCheckResult("0");
            m.setOpeType("U");
            m.setCheckInfo(checkInfo.toString());
        }
        
    }
}

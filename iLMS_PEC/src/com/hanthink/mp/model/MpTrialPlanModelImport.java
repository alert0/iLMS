package com.hanthink.mp.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @Desc		: 新车型计划维护  Excel导入类
 * @FileName	: MpTrialPlanModelImport.java
 * @CreateOn	: 2018年9月10日 上午10:30:10
 * @author 		: linzhuo
 *
 *
 * @ChangeList
 * Date				Version		Editor		Reasons
 * 2018年9月10日		V1.0		linzhuo		新建
 */
public class MpTrialPlanModelImport extends MpTrialPlanModel {
	
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3567805265025060318L;
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
     * 新车型计划维护导入数据检查
     * @param vo
     * @author linzhuo  2018-9-12
     */
    public static void checkImportData(MpTrialPlanModelImport m) {
        StringBuffer checkInfo = new StringBuffer();
        
        if(m.getFactoryCode()== null || "".equals(m.getFactoryCode())){
            checkInfo.append("工厂代码为空;");
        }
        if(m.getAfoffDateStr() == null || "".equals(m.getAfoffDateStr())){
            checkInfo.append("总装下线日期为空;");
        }else {
        	try {
          		 SimpleDateFormat DateFormat=new SimpleDateFormat("yyyy-MM-dd");
          		 DateFormat.parse(m.getAfoffDateStr());
          	}catch (Exception e) {
          		m.setAfoffDateStr(null);
          		checkInfo.append("不是日期;");
   			}
        }
        if(m.getCarType() == null || "".equals(m.getCarType())){
            checkInfo.append("车型为空;");
        }
        if(m.getProPhase() == null || "".equals(m.getProPhase())){
            checkInfo.append("生产阶段为空;");
        }
        if(m.getOrderNo()== null || "".equals(m.getOrderNo())){
            checkInfo.append("订单号为空;");
        }
        
        if(checkInfo == null || "".equals(checkInfo.toString())){
            m.setCheckResult("1");
            m.setCheckInfo("");
        }else{
            m.setCheckResult("0");
            m.setCheckInfo(checkInfo.toString());
        }
    }
	
}

package com.hanthink.jisi.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import com.hotent.base.core.model.AbstractModel;
import com.hotent.base.core.util.string.StringUtil;
import com.mysql.jdbc.StringUtils;

public class JisiPartGroupModel extends AbstractModel<String>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2422642895385127790L;
	
	/**
	 * 导入状态
	 */
	private String importStatusDesc;
	
	/**
	 * 校验信息
	 */
	private String checkResultDesc;
	/**工厂**/
	private String factoryCode;
	/**id**/
	private String id;
	/**信息点**/
	private String planCode;
	/**信息点类型**/
	private String planCodeType;
	/**零件组代码**/
	private String partGroupNo;
	/**零件组名称**/
	private String partGroupName;
	/**打印机Id**/
	private String printerId;
	/**打印机名称**/
	private String printerName;
	/**打印机描述**/
	private String printerIdDesc;
	/**打印位置**/
	private String printLocation;
	/**是否自动打印**/
	private String isAutoPrint;
	/**是否自动打印数据字典名称**/
	private String isAutoPrintName;
	/**生效日期**/
	private String effStart;
	/**失效日期**/
	private String effEnd;
	/**创建人**/
	private String creationUser;
	/**最后修改用户**/
	private String lastModifiedUser;
	/**最后修改IP**/
	private String lastModifiedIp;
	
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPlanCode() {
		return planCode;
	}
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}
	public String getPartGroupNo() {
		return partGroupNo;
	}
	public void setPartGroupNo(String partGroupNo) {
		this.partGroupNo = partGroupNo;
	}
	public String getPartGroupName() {
		return partGroupName;
	}
	public void setPartGroupName(String partGroupName) {
		this.partGroupName = partGroupName;
	}
	public String getPrinterId() {
		return printerId;
	}
	public void setPrinterId(String printerId) {
		this.printerId = printerId;
	}
	public String getPrintLocation() {
		return printLocation;
	}
	public void setPrintLocation(String printLocation) {
		this.printLocation = printLocation;
	}
	public String getIsAutoPrint() {
		return isAutoPrint;
	}
	public void setIsAutoPrint(String isAutoPrint) {
		this.isAutoPrint = isAutoPrint;
	}
	public String getEffStart() {
		return effStart;
	}
	public void setEffStart(String effStart) {
		this.effStart = effStart;
	}
	public String getEffEnd() {
		return effEnd;
	}
	public void setEffEnd(String effEnd) {
		this.effEnd = effEnd;
	}
	public String getCreationUser() {
		return creationUser;
	}
	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}
	public String getLastModifiedUser() {
		return lastModifiedUser;
	}
	public void setLastModifiedUser(String lastModifiedUser) {
		this.lastModifiedUser = lastModifiedUser;
	}
	public String getLastModifiedIp() {
		return lastModifiedIp;
	}
	public void setLastModifiedIp(String lastModifiedIp) {
		this.lastModifiedIp = lastModifiedIp;
	}
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public String getPrinterIdDesc() {
		return printerIdDesc;
	}
	public void setPrinterIdDesc(String printerIdDesc) {
		this.printerIdDesc = printerIdDesc;
	}
	public String getIsAutoPrintName() {
		return isAutoPrintName;
	}
	public void setIsAutoPrintName(String isAutoPrintName) {
		this.isAutoPrintName = isAutoPrintName;
	}
	public String getPrinterName() {
		return printerName;
	}
	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}

	public String getPlanCodeType() {
		return planCodeType;
	}
	public void setPlanCodeType(String planCodeType) {
		this.planCodeType = planCodeType;
	}
	
	public String getImportStatusDesc() {
		return importStatusDesc;
	}
	public void setImportStatusDesc(String importStatusDesc) {
		this.importStatusDesc = importStatusDesc;
	}
	public String getCheckResultDesc() {
		return checkResultDesc;
	}
	public void setCheckResultDesc(String checkResultDesc) {
		this.checkResultDesc = checkResultDesc;
	}
	public static void checkImportData(JisiPartGroupModel model, Map<String, String> map_pr)throws Exception {
		StringBuffer checkInfo = new StringBuffer();
		/*if (StringUtil.isEmpty(model.getPlanCode())) {
			checkInfo.append("信息点为空");
		}*/
		if (StringUtil.isEmpty(model.getPartGroupNo())) {
			checkInfo.append("零件组代码不能为空;");
		}
		if (StringUtil.isEmpty(model.getPartGroupName())) {
			checkInfo.append("零件组名称不能为空;");
		}
		if (StringUtil.isEmpty(model.getPrinterName())) {
			checkInfo.append("打印机不能为空;");
		}else {
			if(StringUtil.isEmpty(map_pr.get(model.getPrinterName()))) {
				checkInfo.append("打印机在系统不存在;");
			}
		}
		if(StringUtil.isEmpty(model.getPrintLocation())) {
			checkInfo.append("打印位置不能为空;");
		}else {
			try {
				Integer.valueOf(model.getPrintLocation());
			} catch (Exception e) {
				checkInfo.append("打印位置格式错误;");
			}
		}
		if (StringUtil.isEmpty(model.getIsAutoPrintName())) {
			checkInfo.append("是否自动打印不能为空;");
		}
		
		String effStart2 = model.getEffStart();
		String effEnd2 = model.getEffEnd();
		if(StringUtils.isNullOrEmpty(effStart2)) {
			checkInfo.append("生效日期不能为空;");
		}
		if(StringUtils.isNullOrEmpty(effEnd2)) {
			checkInfo.append("失效日期不能为空;");
		}
		if(!StringUtils.isNullOrEmpty(effEnd2) && !StringUtils.isNullOrEmpty(effEnd2)) {
			SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );
			try {
				format.parse(effStart2);
			} catch (Exception e) {
				checkInfo.append("生效日期格式不正确;");
				model.setEffStart("");
			}
			try {
				format.parse(effEnd2);
			} catch (Exception e) {
				checkInfo.append("失效日期格式不正确;");
				model.setEffEnd("");
			}
			try {
				format.parse(effStart2);
				format.parse(effEnd2);
				long number = parseTime(effEnd2) - parseTime(effStart2);
				if (number < 0L) {
					checkInfo.append("失效日期不能小于生效日期;");
				}
			} catch (Exception e) {
				checkInfo.append("生效日期格式不正确;失效日期格式不正确;");
			}
		}
		
		/*if (StringUtil.isEmpty(model.getEffStart())) {
			checkInfo.append("生效日期不能为空;");
		}
		if (StringUtil.isEmpty(model.getEffEnd())) {
			checkInfo.append("失效时间不能为空;");
		}*/
		
		if(checkInfo == null || "".equals(checkInfo.toString())){
			model.setCheckResult("1");
			model.setOpeType("I");
			model.setCheckInfo("");
        }else{
        	model.setCheckResult("0");
        	model.setOpeType("U");
        	model.setCheckInfo(checkInfo.toString());
        }
	}
	
	/**
	 * @Description: 判断生失效日期大小
	 * @param: @param strTime
	 * @param: @return    
	 * @return: long   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	private static long parseTime(String strTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		long time = 0L;
		try {
			time = format.parse(strTime).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}
}

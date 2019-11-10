package com.hanthink.jisi.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.hotent.base.core.model.AbstractModel;
import com.hotent.base.core.util.string.StringUtil;

import bsh.ParseException;

public class JisiPartModel extends AbstractModel<String>{
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 3301626089152486174L;
	
	/**
	 * 生失效状态
	 */
	private String effStatusDesc;
	/**工厂**/
	private String factoryCode;
	/**信息点**/
	private String planCode;
	/**信息点类型**/
	private String planCodeType;
	/**id**/
	private String id;
	/**零件组id**/
	private String partGroupId;
	/**零件组代码**/
	private String partGroupNo;
	/**零件名称**/
	private String partNameCn;
	/**零件组名称**/
	private String partGroupName;
	/**零件号**/
	private String partNo;
	/**简号**/
	private String partShortNo;
	/**记号**/
	private String partMark;
	/**生效日期**/
	private String effStart;
	/**失效日期**/
	private String effEnd;
	/**生失效状态**/
	private String effStatus;
	/**创建人**/
	private String creationUser;
	/**创建时间**/
	private String creationTime;
	/**最后修改用户**/
	private String lastModifiedUser;
	/**最后修改用户**/
	private String lastModifiedTime;
	/**最后修改IP**/
 	private String lastModifiedIp;
 	/**下拉框值**/
 	private String value;
 	/**下拉框名称**/
 	private String label;
 	
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
	
	public String getPlanCodeType() {
		return planCodeType;
	}
	public void setPlanCodeType(String planCodeType) {
		this.planCodeType = planCodeType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPartGroupId() {
		return partGroupId;
	}
	public void setPartGroupId(String partGroupId) {
		this.partGroupId = partGroupId;
	}
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public String getPartMark() {
		return partMark;
	}
	public void setPartMark(String partMark) {
		this.partMark = partMark;
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
	public String getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}
	public String getLastModifiedUser() {
		return lastModifiedUser;
	}
	public void setLastModifiedUser(String lastModifiedUser) {
		this.lastModifiedUser = lastModifiedUser;
	}
	public String getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
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
	public String getPartGroupNo() {
		return partGroupNo;
	}
	
	public String getEffStatusDesc() {
		return effStatusDesc;
	}
	public void setEffStatusDesc(String effStatusDesc) {
		this.effStatusDesc = effStatusDesc;
	}
	public String getPlanCode() {
		return planCode;
	}
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
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
	public String getPartShortNo() {
		return partShortNo;
	}
	public void setPartShortNo(String partShortNo) {
		this.partShortNo = partShortNo;
	}
	public String getEffStatus() {
		return effStatus;
	}
	public void setEffStatus(String effStatus) {
		this.effStatus = effStatus;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getPartNameCn() {
		return partNameCn;
	}
	public void setPartNameCn(String partNameCn) {
		this.partNameCn = partNameCn;
	}	
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
	public static void checkImportData(JisiPartModel model)throws Exception {
		StringBuffer checkInfo = new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if (StringUtil.isEmpty(model.getPartGroupNo())) {
			checkInfo.append("零件组不能为空");
		}
		if (StringUtil.isEmpty(model.getPartNo())) {
			checkInfo.append("零件号不能为空");
		}
		if (StringUtil.isEmpty(model.getPartMark())) {
			checkInfo.append("记号不能为空");
		}
		if (StringUtil.isEmpty(model.getEffStart())) {
			checkInfo.append("生效日期不能为空");
		}else {
			try {
				sdf.parse(model.getEffStart());
			} catch (Exception e) {
				checkInfo.append("生效日期格式错误");
			}
		}
		if (StringUtil.isEmpty(model.getEffEnd())) {
			checkInfo.append("失效日期不能为空");
		}else {
			String endRequireDateStr = model.getEffEnd();
			Date endRequireDate;
			try {
				endRequireDate = sdf.parse(endRequireDateStr);
				Date nowDate = new Date();
				if(endRequireDate.getTime() < nowDate.getTime()) {
					checkInfo.append("失效日期不能小于当前时间");
				}
			} catch (Exception e) {
				checkInfo.append("失效日期格式错误");
				e.printStackTrace();
			}
		}
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
}

package com.hanthink.sps.model;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hanthink.util.excel.ExcelUtil;
import com.mysql.jdbc.StringUtils;

/**
 * <pre> 
 * 描述：MM_SPS_MOULD_CONFIG 实体对象
 * 作者:dtp
 * 日期:2018-11-21 12:05:55
 * </pre>
 */
public class SpsMouldConfigModel {
	
	protected String id; 
	
	/** 导入UUID */
    private String uuid;

    /** 检查结果(0错误;1：通过;2:重复存在) */
    private String checkResult;

    /** 检查结果信息 */
    private String checkInfo;

    /** 导入状态 */
    private String importStatus;
    
    /**
     * 显示属性(value)
     */
    private String configShowDesc;
	
	/**
	 * 工厂代码
	 */
	protected String factoryCode;
	
	/**
	 * 模板IDMM_SPS_MOULD.ID     
	 */
	protected String mouldId; 
	
	/**
	 * 对应模板位置号
	 */
	protected String mouldPlace; 
	
	/**
	 * 配置项IDMM_SPS_CONFIG_ITEM.ID     
	 */
	protected String configId; 
	
	/**
	 * 配置项代码
	 */
	protected String configCode;
	
	/**
	 * 配置项描述
	 */
	protected String configDesc;
	
	/**
	 * 配置项显示属性数据字典类型“SPS_CONFIG_SHOW"     
	 */
	protected String configShow; 
	
	/**
	 * 图片ID
	 */
	protected String imageId; 
	
	/**
	 * 是否上传图片
	 */
	protected String isUploadImage;
	
	/**
	 * 创建时间
	 */
	protected String creationTime; 
	
	/**
	 * 创建人
	 */
	protected String creationUser; 
	
	/**
	 * 最后修改时间
	 */
	protected String lastModifiedTime; 
	
	/**
	 * 最后修改用户
	 */
	protected String lastModifiedUser; 
	
	/**
	 * 最后修改IP
	 */
	protected String lastModifiedIp;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getMouldId() {
		return mouldId;
	}

	public void setMouldId(String mouldId) {
		this.mouldId = mouldId;
	}

	public String getMouldPlace() {
		return mouldPlace;
	}

	public void setMouldPlace(String mouldPlace) {
		this.mouldPlace = mouldPlace;
	}

	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	public String getConfigShow() {
		return configShow;
	}

	public void setConfigShow(String configShow) {
		this.configShow = configShow;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	public String getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
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

	public String getIsUploadImage() {
		return isUploadImage;
	}

	public void setIsUploadImage(String isUploadImage) {
		this.isUploadImage = isUploadImage;
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

	public String getConfigCode() {
		return configCode;
	}

	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}

	public String getConfigDesc() {
		return configDesc;
	}

	public void setConfigDesc(String configDesc) {
		this.configDesc = configDesc;
	}

	/**
	 * @Description: 校验导入数据合法性 
	 * @param: @param m    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月23日
	 */
	public static void checkImportData(SpsMouldConfigModel model, Map<String, String> configMap,
			Map<String, String> dictMap) {
		StringBuffer checkInfo = new StringBuffer();
		if(StringUtils.isNullOrEmpty(model.getMouldPlace())) {
			checkInfo.append("位置号不能为空;");
		}
		//校验导入非空配置项代码是否存在
		if(!StringUtils.isNullOrEmpty(model.getConfigCode())) {
			String configCode = configMap.get(model.getConfigCode());
			if(StringUtils.isNullOrEmpty(configCode)) {
				checkInfo.append("配置项代码在系统不存在;");
			}
			model.setConfigCode(configMap.get(model.getConfigCode()));
		}
		//校验导入显示属性是否存在
		if(!StringUtils.isNullOrEmpty(model.getConfigShow())) {
			String configShow = dictMap.get(model.getConfigShow());
			if(StringUtils.isNullOrEmpty(configShow)) {
				checkInfo.append("显示属性在系统不存在;");
			}
			model.setConfigShow(dictMap.get(model.getConfigShow()));
		}
		/*if(StringUtils.isNullOrEmpty(model.getConfigId())) {
			checkInfo.append("配置项不能为空;");
		}
		if(StringUtils.isNullOrEmpty(model.getConfigShow())) {
			checkInfo.append("显示属性不能为空;");
		}*/
		if(StringUtils.isNullOrEmpty(checkInfo.toString())){
			model.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			model.setCheckInfo("");
		}else{
			model.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_ERROR);
			model.setCheckInfo(checkInfo.toString());
		}
	} 
	public String getConfigShowDesc() {
		return configShowDesc;
	}

	public void setConfigShowDesc(String configShowDesc) {
		this.configShowDesc = configShowDesc;
	}

	/**
     * 利用正则表达式判断字符串是否是数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
	
}

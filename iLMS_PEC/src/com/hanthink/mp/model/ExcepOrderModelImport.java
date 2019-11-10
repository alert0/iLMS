package com.hanthink.mp.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @Desc    : 例外订购需求导入Model
 * @CreateOn: 2018年9月11日
 * @author  : WY
 *
 * @ChangeList
 * Date			  Version		   Editor		     ChangeReasons
 *
 */
public class ExcepOrderModelImport extends ExcepOrderModel {
	
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
    
	/**
     * 车型配置导入数据检查
     * @param vo
     * @author zuosl  2015-10-8
     */
    public static void checkImportData(ExcepOrderModelImport m) {
        StringBuffer checkInfo = new StringBuffer();
        
        if(m.getPartNo() == null || "".equals(m.getPartNo())){
            checkInfo.append("零件号为空;");
        }
        if(m.getSupplierNo() == null || "".equals(m.getSupplierNo())){
            checkInfo.append("供应商代码为空;");
        }
        if(m.getSupFactory() == null || "".equals(m.getSupFactory())){
            checkInfo.append("出货地代码为空;");
        }
        if(m.getOrderNum() == null || "".equals(m.getOrderNum())||"0".equals(m.getOrderNum())){
            checkInfo.append("需求数量为空或为0;");
        }else {
        	try {
        		Integer.parseInt(m.getOrderNum());
        	}catch (Exception e) {
        		m.setOrderNum("1");
        		checkInfo.append("不是数字;");
			}
        }
        if(m.getArriveDateStr() == null || "".equals(m.getArriveDateStr())){
            checkInfo.append("到货日期为空;");
        }else {
        	try {
        		 SimpleDateFormat DateFormat=new SimpleDateFormat("yyyy-MM-dd");
        		 DateFormat.parse(m.getArriveDateStr());
        	}catch (Exception e) {
        		 m.setArriveDateStr((new Date()).toString());
        		 checkInfo.append("不是日期;");
			}
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

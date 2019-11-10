package com.hanthink.mp.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.hotent.base.core.model.AbstractModel;

import sun.tools.jar.resources.jar;

/**
 * 
 * @Desc    : 例外订单Model
 * @CreateOn: 2018年9月29日
 * @author  : WY
 *
 * @ChangeList
 * Date			  Version		   Editor		     ChangeReasons
 *
 */
public class MpExcepOrderModel extends AbstractModel<String>{
    
  

    /**  
	 * <p>Description: long</p>  
	 * @author linzhuo  
	 * @date 2018年9月29日
	 * @version 1.0  
	 */  
	private static final long serialVersionUID = 8458984532728191706L;

	/**
     * id
     */
    private String id;
    
    /**
     * 供应商代码
     */
    private String supplierNo;
    
    /**
     * 出货地
     */
    private String supFactory;
    
    /**
     * 零件号
     */
    private String partNo;
    
    /**
     * 需求量
     */
    private String orderNum;
    
    /**
     * 到货日期
     */
    private Date arriveDate;
    
    /**
     * 到货日期字符
     */
    private String arriveDateStr;
    
    /**
     * 到货日期（开始）
     */
    private String arriveDateStrStart;
    
    /**
     * 到货日期（结束）
     */
    private String arriveDateStrEnd;
    
    /**
     * 工厂代码
     */
    private String factoryCode;
    
    /**
     * 到货仓库
     */
    private String storage;
    
    /**
     * 创建人
     */
    private String createUser;
   
    /**
     * 创建时间
     */
    private java.util.Date createTime;
    
    /**
     * 计算状态
     */
    private String calStatus;
    
    /**
     * 计算时间
     */
    private java.util.Date calTime;
    
    /**
     * 车间
     */
    private String workcenter;
    
    /**
     * 用途
     */
    private String use;
    
    /**
     * 需求部门
     */
    private String demandDepartment;
    
    /**
     * 需求人
     */
    private String demander;
    
    /**
     * 联系电话
     */
    private String conNumber;
    
    /**
     * 操作人
     */
    private String optUser;
    
    /**
     * 操作时间
     */
    private java.util.Date optTime;
    
    /**
     * 操作时间（字符）
     */
    private String optTimeStr;
    
    /**
     * 副表  供应商表
     */
    /**
     * 供应商名称
     */
    private String supplierName;
    
    /**
     * 副表  订单需求关系表
     */
    /**
     * 物流单号
     */
    private String orderNo;
		
    /**
     * 副表  例外订单头表
     */
    /**
     * 订单号
     */
    private String purchaseNo;
    
    /**
     * 副表  例外订单明细需求
     */
    /**
     * 行号
     */
    private String rowNo;
    
    /**
     * 发布标识
     */
    private String delFlag;
    
    /**
     * 采购类型
     */
    private String purchaseType;
    
    /**
     * 发布日期（开始）
     */
    private String optTimeStrStart;
    
    /**
     * 发布日期（结束）
     */
    private String optTimeStrEnd;
    
    public String getDelFlagStr() {
		return delFlagStr;
	}

	public void setDelFlagStr(String delFlagStr) {
		this.delFlagStr = delFlagStr;
	}

	private String delFlagStr;
    
    /**
     * 副表
     * 数据字典表
     */
    /**
     * 数据值
     */
    private String codeValueNameB;
    
    private String codeValueNameC;
    
    /**
     * 主表  例外订单需求历史表
     */
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getFactoryCode() {
        return factoryCode;
    }

    public void setFactoryCode(String factoryCode) {
        this.factoryCode = factoryCode;
    }

    public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public String getSupplierNo() {
        return supplierNo;
    }

    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    public String getSupFactory() {
        return supFactory;
    }

    public void setSupFactory(String supFactory) {
        this.supFactory = supFactory;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public Date getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(Date arriveDate) {
        this.arriveDate = arriveDate;
    }

    public String getArriveDateStr() {
        return arriveDateStr;
    }

    public void setArriveDateStr(String arriveDateStr) {
        this.arriveDateStr = arriveDateStr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public String getCalStatus() {
		return calStatus;
	}

	public void setCalStatus(String calStatus) {
		this.calStatus = calStatus;
	}

	public java.util.Date getCalTime() {
		return calTime;
	}

	public void setCalTime(java.util.Date calTime) {
		this.calTime = calTime;
	}

	public String getArriveDateStrStart() {
		return arriveDateStrStart;
	}

	public void setArriveDateStrStart(String arriveDateStrStart) {
		this.arriveDateStrStart = arriveDateStrStart;
	}

	public String getArriveDateStrEnd() {
		return arriveDateStrEnd;
	}

	public void setArriveDateStrEnd(String arriveDateStrEnd) {
		this.arriveDateStrEnd = arriveDateStrEnd;
	}

	 public String getWorkcenter() {
		return workcenter;
	}

	public void setWorkcenter(String workcenter) {
		this.workcenter = workcenter;
	}

	/**
     * 副表  供应商表
     */
    /**
     * 供应商名称
     */
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	 /**
     * 副表  订单需求关系表
     */
    /**
     * 物流单号
     */
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
     * 副表  例外订单头表
     */
    /**
     * 订单号
     */
	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	/**
     * 副表  例外订单明细需求
     */
    /**
     * 行号
     */
	public String getRowNo() {
		return rowNo;
	}

	public void setRowNo(String rowNo) {
		this.rowNo = rowNo;
	}
    
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
     * @author linzhuo  2015-10-8
     */
    public static void checkImportData(MpExcepOrderModel m) {
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
        if(m.getStorage() == null || "".equals(m.getStorage())){
            checkInfo.append("仓库代码为空;");
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

	public String getUse() {
		return use;
	}

	public void setUse(String use) {
		this.use = use;
	}

	public String getDemandDepartment() {
		return demandDepartment;
	}

	public void setDemandDepartment(String demandDepartment) {
		this.demandDepartment = demandDepartment;
	}

	public String getDemander() {
		return demander;
	}

	public void setDemander(String demander) {
		this.demander = demander;
	}

	public String getConNumber() {
		return conNumber;
	}

	public void setConNumber(String conNumber) {
		this.conNumber = conNumber;
	}

	public String getOptUser() {
		return optUser;
	}

	public void setOptUser(String optUser) {
		this.optUser = optUser;
	}

	public java.util.Date getOptTime() {
		return optTime;
	}

	public void setOptTime(java.util.Date optTime) {
		this.optTime = optTime;
	}

	public String getOptTimeStr() {
		return optTimeStr;
	}

	public void setOptTimeStr(String optTimeStr) {
		this.optTimeStr = optTimeStr;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
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

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	public String getOptTimeStrStart() {
		return optTimeStrStart;
	}

	public void setOptTimeStrStart(String optTimeStrStart) {
		this.optTimeStrStart = optTimeStrStart;
	}

	public String getOptTimeStrEnd() {
		return optTimeStrEnd;
	}

	public void setOptTimeStrEnd(String optTimeStrEnd) {
		this.optTimeStrEnd = optTimeStrEnd;
	}
	
}

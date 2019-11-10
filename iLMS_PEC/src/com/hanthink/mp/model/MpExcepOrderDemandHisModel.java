package com.hanthink.mp.model;

import java.util.Date;

import com.hotent.base.core.model.AbstractModel;

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
public class MpExcepOrderDemandHisModel extends AbstractModel<String>{
    
  

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
    
	
}

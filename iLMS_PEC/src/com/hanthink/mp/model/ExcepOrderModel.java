package com.hanthink.mp.model;

import java.util.Date;

import com.hotent.base.core.model.AbstractModel;

/**
 * 
 * @Desc    : 例外订单Model
 * @CreateOn: 2018年9月7日
 * @author  : WY
 *
 * @ChangeList
 * Date			  Version		   Editor		     ChangeReasons
 *
 */
public class ExcepOrderModel extends AbstractModel<String>{
    
    /**
     * 
     */
    private static final long serialVersionUID = 6237416842566909941L;

    /**
     * 主表   例外需求导入表
     */
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
     * 工厂代码
     */
    private String factoryCode;
    
    /**
     * 到货仓库
     */
    private String storage;
    
    
    /**
     * 创建人
     * @return
     */
    private String createUser;
    
    /**
	 * 副表  数据字典表
	 */
	/**
	 * 数据值
	 */
	private String codeValueNameB;
	/**
	 * 数据值
	 */
	private String codeValueNameC;

	/**
	 * 主表   例外需求导入表
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

    /**
     * 副表  数据字典表
     */
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

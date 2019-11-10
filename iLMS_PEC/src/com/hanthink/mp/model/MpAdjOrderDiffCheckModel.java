package com.hanthink.mp.model;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：计划对比差异查询  实体对象
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public class MpAdjOrderDiffCheckModel extends AbstractModel<String>{
	
	
	/**  
	 * <p>Description: long</p>  
	 * @author linzhuo  
	 * @date 2019年1月2日
	 * @version 1.0  
	 */  
	private static final long serialVersionUID = -2376103555719758288L;
	/**
	 * 主表  计划对比差异表
	 * @return
	 */
	/**
	* 工厂
	*/
	private String factoryCode; 
	
	/**
	* 供应商代码
	*/
	private String supplierNo; 
	
	/**
	* 出货地
	*/
	private String supFactory; 
	
	/**
	* 计算队列
	*/
	private String unloadPort;
	
	/**
	* 零件号
	*/
	private String partNo;
	
	private String adjDiffNum;
	
	/**
	 * 车间
	 */
	private String workcenter;
	
	/**
	 * 车型
	 */
	private String modelCode;
	
	/**
	 * 简号
	 */
	private String partShortNo;
	
	/**
	 * 零件名称
	 */
	private String partName;
	
	/**
	 * 手工调整差异数量
	 */
	private String manuNum;
	
	/**
	 * 调整后数量
	 */
	private String afAdjNum;
	
	/**
	 * 创建人
	 */
	private String creationUser;
	
	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
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

	public String getUnloadPort() {
		return unloadPort;
	}

	public void setUnloadPort(String unloadPort) {
		this.unloadPort = unloadPort;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getAdjDiffNum() {
        return adjDiffNum;
    }

    public void setAdjDiffNum(String adjDiffNum) {
        this.adjDiffNum = adjDiffNum;
    }

    @Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

    public String getWorkcenter() {
        return workcenter;
    }

    public void setWorkcenter(String workcenter) {
        this.workcenter = workcenter;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getPartShortNo() {
        return partShortNo;
    }

    public void setPartShortNo(String partShortNo) {
        this.partShortNo = partShortNo;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getManuNum() {
        return manuNum;
    }

    public void setManuNum(String manuNum) {
        this.manuNum = manuNum;
    }

    public String getAfAdjNum() {
        return afAdjNum;
    }

    public void setAfAdjNum(String afAdjNum) {
        this.afAdjNum = afAdjNum;
    }

    public String getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(String creationUser) {
        this.creationUser = creationUser;
    } 
	
}
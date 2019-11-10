package com.hanthink.pub.model;
import com.hotent.base.core.model.AbstractModel;


/**
 *              
 * @Desc    : 首页 查询Model
 * @CreateOn: 2019年1月5日
 * @author  : WY
 *
 * @ChangeList
 * Date			  Version		   Editor		     ChangeReasons
 *
 */
public class WelModel extends AbstractModel<Integer>{

    /**
     * 
     */
    private static final long serialVersionUID = 193561349649999249L;
    
    /**
     * 工厂代码
     */
    private String factoryCode;
    
    /**
     * 提案状态
     */
    private String proposalStatus;
    
    /**
     * 任务数量
     */
    private String taskNum;
   
    /**
     * 当前登录供应商
     */
    private String supplier;
    
    /**
     * 订单类型
     */
    private String orderType;
    private String orderTypeName;
    
    /**
     * 订单数量
     */
    private String orderNum;
    
    /**
     * 未打印数量
     */
    private String notPrintNum;
    
    /**
     * 已打印数量
     */
    private String printNum;
    
    /**
     * 未收货数量
     */
    private String notRecNum;
    
    /**
     * 部分收货数量
     */
    private String portionRecNum;
    
    /**
     * 全部收货数量
     */
    private String allRecNum;
    
    /**
     * 别名
     */
    private String alias;
    
    private String userType;
    
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getNotPrintNum() {
        return notPrintNum;
    }

    public void setNotPrintNum(String notPrintNum) {
        this.notPrintNum = notPrintNum;
    }

    public String getPrintNum() {
        return printNum;
    }

    public void setPrintNum(String printNum) {
        this.printNum = printNum;
    }

    public String getNotRecNum() {
        return notRecNum;
    }

    public void setNotRecNum(String notRecNum) {
        this.notRecNum = notRecNum;
    }

    public String getPortionRecNum() {
        return portionRecNum;
    }

    public void setPortionRecNum(String portionRecNum) {
        this.portionRecNum = portionRecNum;
    }

    public String getAllRecNum() {
        return allRecNum;
    }

    public void setAllRecNum(String allRecNum) {
        this.allRecNum = allRecNum;
    }

    public String getProposalStatus() {
        return proposalStatus;
    }

    public void setProposalStatus(String proposalStatus) {
        this.proposalStatus = proposalStatus;
    }

    public String getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(String taskNum) {
        this.taskNum = taskNum;
    }

    public String getFactoryCode() {
        return factoryCode;
    }

    public void setFactoryCode(String factoryCode) {
        this.factoryCode = factoryCode;
    }

    public String getOrderTypeName() {
        return orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    @Override
    public void setId(Integer id) {
        
    }

    public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Override
    public Integer getId() {
        return null;
    }
	
}
package com.hanthink.pup.model;

import com.hotent.base.core.model.AbstractModel;

/**
 *          
 * @Desc    : DCS封条号管理MODEL 
 * @CreateOn: 2019年1月4日
 * @author  : WY
 *
 * @ChangeList
 * Date			  Version		   Editor		     ChangeReasons
 *
 */
public class PupDcsSealModel extends AbstractModel<String>{
	/**
     * 
     */
    private static final long serialVersionUID = -7891489143611287232L;

    @Override
    public void setId(String id) {
        
    }

    @Override
    public String getId() {
        return null;
    }
    
    /**
     * 封条号
     */
    private String sealNo;
    
    /**
     * 使用标识 0 未使用 1 已使用
     */
    private String dealFlag;
    private String dealFlagStr;
    
    /**
     * 工厂
     */
    private String factoryCode;
    
    /**
     * 创建人
     */
    private String createUser;

    public String getSealNo() {
        return sealNo;
    }

    public void setSealNo(String sealNo) {
        this.sealNo = sealNo;
    }

    public String getDealFlag() {
        return dealFlag;
    }

    public void setDealFlag(String dealFlag) {
        this.dealFlag = dealFlag;
    }

    public String getDealFlagStr() {
        return dealFlagStr;
    }

    public void setDealFlagStr(String dealFlagStr) {
        this.dealFlagStr = dealFlagStr;
    }

    public String getFactoryCode() {
        return factoryCode;
    }

    public void setFactoryCode(String factoryCode) {
        this.factoryCode = factoryCode;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    
}

package com.hanthink.pup.model;


/**
 * 
 * @Desc    : DCS导入Model
 * @CreateOn: 2019年1月7日
 * @author  : WY
 *
 * @ChangeList
 * Date			  Version		   Editor		     ChangeReasons
 *
 */
public class PupDcsModelImport extends PupDcsModel {


    /**
     * 
     */
    private static final long serialVersionUID = 7765598758583274688L;

    /** 导入UUID */
    private String uuid;

    /** 检查结果(0错误;1：通过;2:重复存在) */
    private String checkResult;

    /** 检查结果信息 */
    private String checkInfo;

    /** 导入状态 */
    private String importStatus;
    
    /**
     * OPE_TYPE
     * @return
     */
    private String opeType;

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

}

package com.hanthink.mp.model;

import java.util.Date;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：车辆计划  实体对象
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public class MpVehPlanModel extends AbstractModel<String>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9202141385383296430L;

	/**
	 * 主表  车辆计划
	 * @return
	 */
	/**
	* 生产排序号
	*/
	private String sortId; 
	
	/**
	 * 生产排序号
	 */
	private String sortIdStr;
	
	/**
	 * MES生产排序号
	 */
	private String mesSortId;
	
	/**
	 * 生产排序号(开始）
	 */
	private String sortIdStrStart;
	
	/**
	 * 生产排序号(结束）
	 */
	private String sortIdStrEnd;
	
	/**
	* 工厂
	*/
	private String factoryCode; 
	
	/**
	* 车型
	*/
	private String carType; 
	
	/**
	* 订单号
	*/
	private String orderNo; 
	
	/**
	* 车型代码
	*/
	private String mtoc; 
	
	/**
	* 总装下线时间
	*/
	private java.util.Date afoffTime; 
	
	private String afoffTimeStr;
	
	/**
	 * 焊装上线时间
	 */
	private String weOnTimeStr;
	private Date weOnTime;
	
	/**
	* 总装下线时间开始
	*/
	private java.util.Date afoffTimeStart; 
	private String afoffTimeStartStr;
	
	/**
	* 总装下线时间结束
	*/
	private java.util.Date afoffTimeEnd; 
	private String afoffTimeEndStr;
	
	/**
	* 生产阶段
	*/
	private String proPhase; 
	
	/**
	* 计算状态
	*/
	private String calStatus; 
	
	/**
	* 计算时间
	*/
	private java.util.Date calTime; 
	
	/**
	* 创建人
	*/
	private String creationUser; 
	
	/**
	* 创建时间
	*/
	private java.util.Date creationTime; 
	
	/**
	* 最后修改人
	*/
	private String lastModifiedUser; 
	
	/**
	* 最后修改时间
	*/
	private java.util.Date lastModifiedTime;
	
	/**
	 * 副表  车辆计划分车型队列
	 * @return
	 */
	private String carTypeSortId;
	
	/**
	 * 副表  数据字典
	 * @return
	 */
	/**
	* 类型名称
	*/
	private String codeTypeName;
	/**
	* 编码值名称
	*/
	private String codeValueName;
	/**
	* 编码值名称
	*/
	private String codeValueNameD;
	
	/**
	 * 主表  车辆计划
	 * @return
	 */
	public String getSortId() {
		return sortId;
	}

	public void setSortId(String sortId) {
		this.sortId = sortId;
	}
	
	public String getSortIdStr() {
		return sortIdStr;
	}

	public void setSortIdStr(String sortIdStr) {
		this.sortIdStr = sortIdStr;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getMtoc() {
		return mtoc;
	}

	public void setMtoc(String mtoc) {
		this.mtoc = mtoc;
	}

	public java.util.Date getAfoffTime() {
		return afoffTime;
	}

	public void setAfoffTime(java.util.Date afoffTime) {
		this.afoffTime = afoffTime;
	}

	public java.util.Date getAfoffTimeStart() {
		return afoffTimeStart;
	}

	public void setAfoffTimeStart(java.util.Date afoffTimeStart) {
		this.afoffTimeStart = afoffTimeStart;
	}

	public java.util.Date getAfoffTimeEnd() {
		return afoffTimeEnd;
	}

	public void setAfoffTimeEnd(java.util.Date afoffTimeEnd) {
		this.afoffTimeEnd = afoffTimeEnd;
	}

	public String getProPhase() {
		return proPhase;
	}

	public void setProPhase(String proPhase) {
		this.proPhase = proPhase;
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

	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	public java.util.Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(java.util.Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getLastModifiedUser() {
		return lastModifiedUser;
	}

	public void setLastModifiedUser(String lastModifiedUser) {
		this.lastModifiedUser = lastModifiedUser;
	}

	public java.util.Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(java.util.Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	/**
	 * 副表 车辆计划分车型队列 
	 * @return
	 */
	public String getCarTypeSortId() {
		return carTypeSortId;
	}

	public void setCarTypeSortId(String carTypeSortId) {
		this.carTypeSortId = carTypeSortId;
	}

	public String getAfoffTimeStr() {
		return afoffTimeStr;
	}

	public void setAfoffTimeStr(String afoffTimeStr) {
		this.afoffTimeStr = afoffTimeStr;
	}

	public String getAfoffTimeStartStr() {
		return afoffTimeStartStr;
	}

	public void setAfoffTimeStartStr(String afoffTimeStartStr) {
		this.afoffTimeStartStr = afoffTimeStartStr;
	}

	public String getAfoffTimeEndStr() {
		return afoffTimeEndStr;
	}

	public void setAfoffTimeEndStr(String afoffTimeEndStr) {
		this.afoffTimeEndStr = afoffTimeEndStr;
	}
	/**
	 * 副表  数据字典
	 * @return
	 */
	public String getCodeTypeName() {
		return codeTypeName;
	}

	public void setCodeTypeName(String codeTypeName) {
		this.codeTypeName = codeTypeName;
	}

	
	public String getCodeValueName() {
		return codeValueName;
	}

	public void setCodeValueName(String codeValueName) {
		this.codeValueName = codeValueName;
	}
	
	public String getCodeValueNameD() {
		return codeValueNameD;
	}

	public void setCodeValueNameD(String codeValueNameD) {
		this.codeValueNameD = codeValueNameD;
	}

	public String getSortIdStrStart() {
		return sortIdStrStart;
	}

	public void setSortIdStrStart(String sortIdStrStart) {
		this.sortIdStrStart = sortIdStrStart;
	}

	public String getSortIdStrEnd() {
		return sortIdStrEnd;
	}

	public void setSortIdStrEnd(String sortIdStrEnd) {
		this.sortIdStrEnd = sortIdStrEnd;
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

    public String getWeOnTimeStr() {
        return weOnTimeStr;
    }

    public void setWeOnTimeStr(String weOnTimeStr) {
        this.weOnTimeStr = weOnTimeStr;
    }

    public Date getWeOnTime() {
        return weOnTime;
    }

    public void setWeOnTime(Date weOnTime) {
        this.weOnTime = weOnTime;
    }

    public String getMesSortId() {
        return mesSortId;
    }

    public void setMesSortId(String mesSortId) {
        this.mesSortId = mesSortId;
    }
	
}
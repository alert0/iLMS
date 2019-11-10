package com.hanthink.pub.model;
import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：单车BOM信息表 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-06 00:31:49
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public class PubOrderBomModel extends AbstractModel<Integer>{
	

	/**  
	 * <p>Description: long</p>  
	 * @author linzhuo  
	 * @date 2018年10月1日
	 * @version 1.0  
	 */  
	private static final long serialVersionUID = 8299332018685327427L;

	/**
	 * 主表  单车BOM信息关系表
	 */
	/**
	* 工厂
	*/
	protected String factoryCode ; 
	
	/**
	* 生产订单号
	*/
	protected String orderNo; 
	
	
	/**
	 * 订单号数组
	 */
	private String[] orderNos;
	
	/**
	* 整车物料
	*/
	protected String vehiclePartNo; 
	
	/**
	* 子件号
	*/
	protected String partNo; 
	
	/**
	* 车间
	*/
	private String workcenter;
	
	/**
	* 工位号
	*/
	protected String stationCode; 
	
	/**
	* 用量
	*/
	protected String num; 
	
	/**
	* 子件单位
	*/
	protected String usageAmountUnit; 
	
	/**
	* 采购类型
	*/
	protected String purchaseType; 
	
	/**
	* 创建时间
	*/
	protected java.util.Date creationTime; 
	
	/**
	* 最后修改时间
	*/
	protected java.util.Date lastModifiedTime;

	/**
	 * 副表  物料主数据表
	 */
	/**
	 * 零件名称
	 */
	protected String partNameCn;
    
	/**
	 * 副表  数据字典表
	 */
	/**
	 * 数据值
	 */
	protected String codeValueName;
	
	//过点开始时间
	private String startPassTime;
	//过点结束时间
	private String endPassTime;
	//信息点
	private String planCode;
	
	private String isSummary;
	
	private String partRowNo;
	
	/**
	 * 起始总装下线时间
	 */
	private String startAFOffTime;
	
	/**
	 * 结束总装下线时间
	 */
	private String endAFOffTime;
	
	public String getStartAFOffTime() {
		return startAFOffTime;
	}

	public void setStartAFOffTime(String startAFOffTime) {
		this.startAFOffTime = startAFOffTime;
	}

	public String getEndAFOffTime() {
		return endAFOffTime;
	}

	public void setEndAFOffTime(String endAFOffTime) {
		this.endAFOffTime = endAFOffTime;
	}

	/**
	 * 主表  单车BOM信息表
	 */
	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getVehiclePartNo() {
		return vehiclePartNo;
	}

	public void setVehiclePartNo(String vehiclePartNo) {
		this.vehiclePartNo = vehiclePartNo;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getWorkcenter() {
		return workcenter;
	}

	public void setWorkcenter(String workcenter) {
		this.workcenter = workcenter;
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getUsageAmountUnit() {
		return usageAmountUnit;
	}

	public void setUsageAmountUnit(String usageAmountUnit) {
		this.usageAmountUnit = usageAmountUnit;
	}

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	public java.util.Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(java.util.Date creationTime) {
		this.creationTime = creationTime;
	}

	public java.util.Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(java.util.Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	/**
	 * 副表 物料主数据表
	 */
	public String getPartNameCn() {
		return partNameCn;
	}

	public void setPartNameCn(String partNameCn) {
		this.partNameCn = partNameCn;
	}

	public String getCodeValueName() {
		return codeValueName;
	}

	public void setCodeValueName(String codeValueName) {
		this.codeValueName = codeValueName;
	}

	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}

    public String[] getOrderNos() {
        return orderNos;
    }

    public void setOrderNos(String[] orderNos) {
        this.orderNos = orderNos;
    }

	public String getStartPassTime() {
		return startPassTime;
	}

	public void setStartPassTime(String startPassTime) {
		this.startPassTime = startPassTime;
	}

	public String getEndPassTime() {
		return endPassTime;
	}

	public void setEndPassTime(String endPassTime) {
		this.endPassTime = endPassTime;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getIsSummary() {
		return isSummary;
	}

	public void setIsSummary(String isSummary) {
		this.isSummary = isSummary;
	}

	public String getPartRowNo() {
		return partRowNo;
	}

	public void setPartRowNo(String partRowNo) {
		this.partRowNo = partRowNo;
	}
	
}
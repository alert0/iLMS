package com.hanthink.jit.model;

/**
 * <pre> 
 * 描述：MM_JIT_PART_REMAIN_PROD 实体对象
 * 作者:dtp
 * 日期:2018-09-21 11:51:44
 * </pre>
 */
public class JitPartRemainProdModel {

	/**
	 * ID
     */
	protected Long id; 
	
	/**
	 * 工厂编码
	 */
	protected String factoryCode;
	
	/**
	 * VIN
	 */
	protected String vin;
	
	/**
	 * 零件号
     */
	protected String partNo; 
	
	/**
	 * 简号
	 */
	protected String partShortNo;
	
	/**
	 * 零件中文名
	 */
	protected String partNameCn;
	
	/**
	 * 信息点
     */
	protected String planCode; 
	
	/**
	 * 信息点描述
	 */
	protected String planCodeDesc;
	
	/**
	 * 配送地址
     */
	protected String location; 
	
	/**
	 * 下线批次
	 */
	protected String kbProductSeqno;
	
	/**
	 * 期初看板批次产品流水号
     */
	protected Long sKbProductSeqno; 
	
	/**
	 * 期末看板批次产品流水号
     */
	protected Long eKbProductSeqno; 
	
	/**
	 * 期初零件量
     */
	protected Long startRemain; 
	
	/**
	 * 期末零件量
     */
	protected Long endRemain; 
	
	/**
	 * 单件产品需求量
     */
	protected Long requireNum; 
	
	/**
	 * 配送包装数
     */
	protected Long distriPackage; 
	
	/**
	 * 创建时间
     */
	protected String creationTime;
	
	/**
	 * 车间
	 */
	protected String workcenter;
	
	/**
	 * 零件余量
	 */
	protected String partRemain;
	
	/**
	 * sortId
	 */
	protected String sortid;
	
	/**
	 * 下线时间
	 */
	protected String kbTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Long getsKbProductSeqno() {
		return sKbProductSeqno;
	}

	public void setsKbProductSeqno(Long sKbProductSeqno) {
		this.sKbProductSeqno = sKbProductSeqno;
	}

	public Long geteKbProductSeqno() {
		return eKbProductSeqno;
	}

	public void seteKbProductSeqno(Long eKbProductSeqno) {
		this.eKbProductSeqno = eKbProductSeqno;
	}

	public Long getStartRemain() {
		return startRemain;
	}

	public void setStartRemain(Long startRemain) {
		this.startRemain = startRemain;
	}

	public Long getEndRemain() {
		return endRemain;
	}

	public void setEndRemain(Long endRemain) {
		this.endRemain = endRemain;
	}

	public Long getRequireNum() {
		return requireNum;
	}

	public void setRequireNum(Long requireNum) {
		this.requireNum = requireNum;
	}

	public Long getDistriPackage() {
		return distriPackage;
	}

	public void setDistriPackage(Long distriPackage) {
		this.distriPackage = distriPackage;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public String getWorkcenter() {
		return workcenter;
	}

	public void setWorkcenter(String workcenter) {
		this.workcenter = workcenter;
	}

	public String getPartRemain() {
		return partRemain;
	}

	public void setPartRemain(String partRemain) {
		this.partRemain = partRemain;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getSortid() {
		return sortid;
	}

	public void setSortid(String sortid) {
		this.sortid = sortid;
	}

	public String getKbTime() {
		return kbTime;
	}

	public void setKbTime(String kbTime) {
		this.kbTime = kbTime;
	}

	public String getPartShortNo() {
		return partShortNo;
	}

	public void setPartShortNo(String partShortNo) {
		this.partShortNo = partShortNo;
	}

	public String getPartNameCn() {
		return partNameCn;
	}

	public void setPartNameCn(String partNameCn) {
		this.partNameCn = partNameCn;
	}

	public String getPlanCodeDesc() {
		return planCodeDesc;
	}

	public void setPlanCodeDesc(String planCodeDesc) {
		this.planCodeDesc = planCodeDesc;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getKbProductSeqno() {
		return kbProductSeqno;
	}

	public void setKbProductSeqno(String kbProductSeqno) {
		this.kbProductSeqno = kbProductSeqno;
	} 
	
	
}

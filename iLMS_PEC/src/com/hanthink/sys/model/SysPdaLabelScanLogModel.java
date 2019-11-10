package com.hanthink.sys.model;
import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：PDA标签扫描日志表 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-06 00:31:49
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public class SysPdaLabelScanLogModel extends AbstractModel<Integer>{
	
	

	/**  
	 * <p>Description: long</p>  
	 * @author linzhuo  
	 * @date 2018年9月29日
	 * @version 1.0  
	 */  
	private static final long serialVersionUID = -4774730312502154381L;

	/**
	 * 主表  PDA标签扫描日志表
	 */
	/**
	* 工厂
	*/
	private String factoryCode ; 
	
	/**
	* 二维码
	*/
	private String barCode; 
	
	/**
	* 物流单号
	*/
	private String orderNo; 
	
	/**
	* 订单行号
	*/
	private String rowNo; 
	
	/**
	* 零件编号
	*/
	private String partNo; 
	
	/**
	* 零件简号
	*/
	private String partShortNo; 
	
	/**
	* 零件名称
	*/
	private String partNameCn; 
	
	
	/**
	* 数量
	*/
	private String qty; 
	
	/**
	* 唯一标识
	*/
	private String barUuid; 
	
	/**
	* 类型
	*/
	private String type; 
	
	/**
	* 上架ID
	*/
	private String shelveId;
	
	/**
	* 下架ID
	*/
	private String deliverNo;
	
	/**
	* 托标识
	*/
	private String toraId;
	
	/**
	* 创建人
	*/
	private String createUser;
	
	/**
	* 创建时间
	*/
	private java.util.Date createTime;
	
	/**
	 * 创建时间字符
	 */
	private String createTimeStr;
	
	/**
	 * 创建时间开始
	 */
	private String createTimeStrStart;
	
	/**
	 * 创建时间结束
	 */
	private String createTimeStrEnd;
	
	/**
	* IP地址
	*/
	private String ip;
	
	/**
	* 配送单号
	*/
	private String insNo;

	/**
	 * 副表 数据字典表
	 */
	/**
	* 数据值
	*/
	private String codeValueNameA;
	
	/**
	 * 主表 PDA标签扫描日志
	 */
	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getRowNo() {
		return rowNo;
	}

	public void setRowNo(String rowNo) {
		this.rowNo = rowNo;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getBarUuid() {
		return barUuid;
	}

	public void setBarUuid(String barUuid) {
		this.barUuid = barUuid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getShelveId() {
		return shelveId;
	}

	public void setShelveId(String shelveId) {
		this.shelveId = shelveId;
	}

	public String getDeliverNo() {
		return deliverNo;
	}

	public void setDeliverNo(String deliverNo) {
		this.deliverNo = deliverNo;
	}

	public String getToraId() {
		return toraId;
	}

	public void setToraId(String toraId) {
		this.toraId = toraId;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getInsNo() {
		return insNo;
	}

	public void setInsNo(String insNo) {
		this.insNo = insNo;
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


	public String getPartShortNo() {
		return partShortNo;
	}

	public void setPartShortNo(String partShortNo) {
		this.partShortNo = partShortNo;
	}

	public String getCreateTimeStrStart() {
		return createTimeStrStart;
	}

	public void setCreateTimeStrStart(String createTimeStrStart) {
		this.createTimeStrStart = createTimeStrStart;
	}

	public String getCreateTimeStrEnd() {
		return createTimeStrEnd;
	}

	public void setCreateTimeStrEnd(String createTimeStrEnd) {
		this.createTimeStrEnd = createTimeStrEnd;
	}

	public String getCodeValueNameA() {
		return codeValueNameA;
	}

	public void setCodeValueNameA(String codeValueNameA) {
		this.codeValueNameA = codeValueNameA;
	}

	public String getPartNameCn() {
		return partNameCn;
	}

	public void setPartNameCn(String partNameCn) {
		this.partNameCn = partNameCn;
	}
	
}
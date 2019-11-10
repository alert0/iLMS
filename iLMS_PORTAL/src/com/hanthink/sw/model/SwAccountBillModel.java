package com.hanthink.sw.model;


import com.hotent.base.core.model.AbstractModel;


 /**
  * <pre> 
  * 描述：MM_SW_ACCOUNT_BILL 实体对象
  * 作者:luoxq
  * 日期:2018-10-22 16:05:36
  * </pre>
  */
public class SwAccountBillModel extends AbstractModel<String>{

	private static final long serialVersionUID = 6518899716631007934L;
	
	/**提交标识，存储根据该表示判断是否可以发送给erp 1：已提交**/
	public static final String SUBMIT_STATUS_YES = "1";
	/**id**/
	private String id;
	
	/**
	 * 对账单号     */
	protected String billNo; 
	
	/**对账单号数组**/
	protected String[] billNoArr;
	
	/**
	 * 工厂代码     */
	protected String factoryCode; 
	
	/**
	 * 供应商代码     */
	protected String supplierNo; 
	
	/**
	 * 对账单不含税合计     */
	protected String taxExcluded; 
	
	/**
	 * 对账单含税合计     */
	protected String taxInclusive; 
	
	/**
	 * 对账单税额合计     */
	protected String totalTax; 
	
	/**
	 * 制单日期     */
	protected String makeDate; 
	/**制单日期开始（查询条件)**/
	protected String makeDateStart;
	/**制单日期结束（查询条件）**/
	protected String makeDateEnd;
	
	/**
	 * 货币类型     */
	protected String currencyType; 
	
	/**
	 * 供应商返利值     */
	protected String rebate; 
	
	/**
	 * 供应商返利描述     */
	protected String rebateDesc; 
	
	/**
	 * 特殊扣款值     */
	protected String deductMoney; 
	
	/**
	 * 特殊扣款描述     */
	protected String deductMoneyDesc; 
	
	/**
	 * 年底调差值     */
	protected String yearAdjust; 
	
	/**
	 * 年底调差描述     */
	protected String yearAdjustDesc; 
	
	/**
	 * 模具分摊值     */
	protected String mouldAmount; 
	
	/**
	 * 模具分摊描述     */
	protected String mouldAmountDesc; 
	
	/**
	 * 付款条件     */
	protected String payTerm; 
	
	/**
	 * 0-未反馈
1-已反馈
     */
	protected String invoiceStatus; 
	
	/**
	 * 发票反馈时间     */
	protected String invoiceEntryTime; 
	
	/**
	 * 0-未处理
1-已处理     */
	protected String dealFlag; 
	
	/**
	 * 接口处理时间     */
	protected String dealTime; 
	
	/**
	 * 备注     */
	protected String REMARK; 
	
	/**
	 * 创建人     */
	protected String creationUser; 
	
	/**
	 * 创建时间     */
	protected String creationTime; 
	
	/**
	 * 最后修改用户     */
	protected String lastModifiedUser; 
	
	/**
	 * 最后修改时间     */
	protected String lastModifiedTime; 
	
	/**
	 * 对账单行号     */
	protected String billRowNo; 
	
	/**
	 * 订单号     */
	protected String purchaseNo; 
	
	/**
	 * 订单行号     */
	protected String purchaseRowNo; 
	
	/**
	 * 收货凭证年度     */
	protected String recVoucherYear; 
	
	/**
	 * 物料凭证     */
	protected String recVoucherBillno; 
	
	/**
	 * 物料凭证行项目     */
	protected String recVoucherRowno; 
	
	/**
	 * REF_REC_VOUCHER_BILL     */
	protected String refRecVoucherBill; 
	
	/**
	 * REF_REC_VOUCHER_ROWNO     */
	protected String refRecVoucherRowno; 
	
	/**
	 * 收货凭证日期     */
	protected String recDate; 
	
	/**
	 * 借贷标识     */
	protected String loanFlag; 
	
	/**
	 * 1-暂估价；
2-正式价；
3-调差价  ，价格状态   */
	protected Integer priceStatus; 
	
	/**
	 * 暂估价     */
	protected String evaPrice; 
	
	/**
	 * 正式价     */
	protected String officialPrice; 
	
	/**
	 * 调差价格（不含税）     */
	protected String adjustDiffPrice; 
	
	/**
	 * 定价（暂估价）百分比     */
	protected String evaPricePercent; 
	
	/**
	 * 暂估结算单价     */
	protected String evaSettlePrice; 
	
	/**
	 * 暂估结算单价（折前）
	 */
	protected String evaSettlePriceBf;
	
	/**
	 * ERP_FACTORY_CODE     */
	protected String erpFactoryCode; 
	
	/**
	 * 零件号     */
	protected String partNo; 

	
	/**
	 * 已收货数量     */
	protected String recNum; 
	
	/**
	 * 应付（不含税）金额     */
	protected String payAmount; 
	
	/**
	 * 税率     */
	protected String taxRate; 
	
	/**
	 * 税额     */
	protected String taxAmount; 
	
	/**
	 * 物料基本单位     */
	protected String partUnit; 
	/**金税发票号**/
	protected String invoiceNo;
	/**发票代码**/
	protected String invoiceCode;
	/**发票含税总金额**/
	protected String invoiceAmount; 
	/**发票开票时间**/
	protected String invoiceDate;
	/**开票总金额**/
	protected String totalAmount;
	
	/**发票累计金额**/
	protected String totalInvoice;
	
	protected String submitStatus;
	
	protected String accountStatus;
	
	/**用户后面要加的字段**/
	/**校验码**/
	protected String checkCode;
	/**发票不含税金额（净价）**/
	protected String invoiceNetPrice;
	/**零件简号**/
	protected String partShortNo;
	/**零件名称**/
	protected String partNameCn;
	/**
	 * 返回 BILL_NO     * @return
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	public String getBillNo() {
		return this.billNo;
	}
	
	/**
	 * 返回 FACTORY_CODE     * @return
	 */
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	
	public String getFactoryCode() {
		return this.factoryCode;
	}
	
	/**
	 * 返回 SUPPLIER_NO     * @return
	 */
	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}
	
	public String getSupplierNo() {
		return this.supplierNo;
	}
	
	/**
	 * 返回 TAX_EXCLUDED     * @return
	 */
	public void setTaxExcluded(String taxExcluded) {
		this.taxExcluded = taxExcluded;
	}
	
	public String getTaxExcluded() {
		return this.taxExcluded;
	}
	
	/**
	 * 返回 TAX_INCLUSIVE     * @return
	 */
	public void setTaxInclusive(String taxInclusive) {
		this.taxInclusive = taxInclusive;
	}
	
	public String getTaxInclusive() {
		return this.taxInclusive;
	}
	
	/**
	 * 返回 TOTAL_TAX     * @return
	 */
	public void setTotalTax(String totalTax) {
		this.totalTax = totalTax;
	}
	
	public String getTotalTax() {
		return this.totalTax;
	}
	
	/**
	 * 返回 MAKE_DATE     * @return
	 */
	public void setMakeDate(String makeDate) {
		this.makeDate = makeDate;
	}
	
	public String getMakeDate() {
		return this.makeDate;
	}
	
	/**
	 * 返回 CURRENCY_TYPE     * @return
	 */
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
	
	public String getCurrencyType() {
		return this.currencyType;
	}
	
	/**
	 * 返回 REBATE_DESC     * @return
	 */
	public void setRebateDesc(String rebateDesc) {
		this.rebateDesc = rebateDesc;
	}
	
	public String getRebate() {
		return rebate;
	}

	public void setRebate(String rebate) {
		this.rebate = rebate;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public String getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(String invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getRebateDesc() {
		return this.rebateDesc;
	}
	
	/**
	 * 返回 DEDUCT_MONEY     * @return
	 */
	public void setDeductMoney(String deductMoney) {
		this.deductMoney = deductMoney;
	}
	
	public String getDeductMoney() {
		return this.deductMoney;
	}
	
	/**
	 * 返回 DEDUCT_MONEY_DESC     * @return
	 */
	public void setDeductMoneyDesc(String deductMoneyDesc) {
		this.deductMoneyDesc = deductMoneyDesc;
	}
	
	public String getDeductMoneyDesc() {
		return this.deductMoneyDesc;
	}
	
	/**
	 * 返回 YEAR_ADJUST     * @return
	 */
	public void setYearAdjust(String yearAdjust) {
		this.yearAdjust = yearAdjust;
	}
	
	public String getYearAdjust() {
		return this.yearAdjust;
	}
	
	/**
	 * 返回 YEAR_ADJUST_DESC     * @return
	 */
	public void setYearAdjustDesc(String yearAdjustDesc) {
		this.yearAdjustDesc = yearAdjustDesc;
	}
	
	public String getYearAdjustDesc() {
		return this.yearAdjustDesc;
	}
	
	/**
	 * 返回 MOULD_AMOUNT     * @return
	 */
	public void setMouldAmount(String mouldAmount) {
		this.mouldAmount = mouldAmount;
	}
	
	public String getMouldAmount() {
		return this.mouldAmount;
	}
	
	/**
	 * 返回 MOULD_AMOUNT_DESC     * @return
	 */
	public void setMouldAmountDesc(String mouldAmountDesc) {
		this.mouldAmountDesc = mouldAmountDesc;
	}
	
	public String getMouldAmountDesc() {
		return this.mouldAmountDesc;
	}
	
	/**
	 * 返回 PAY_TERM     * @return
	 */
	public void setPayTerm(String payTerm) {
		this.payTerm = payTerm;
	}
	
	public String getPayTerm() {
		return this.payTerm;
	}
	
	/**
	 * 返回 0-未反馈
1-已反馈
     * @return
	 */
	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	
	public String getInvoiceStatus() {
		return this.invoiceStatus;
	}
	
	/**
	 * 返回 INVOICE_ENTRY_TIME     * @return
	 */
	public void setInvoiceEntryTime(String invoiceEntryTime) {
		this.invoiceEntryTime = invoiceEntryTime;
	}
	
	public String getInvoiceEntryTime() {
		return this.invoiceEntryTime;
	}
	
	/**
	 * 返回 0-未处理
1-已处理     * @return
	 */
	public void setDealFlag(String dealFlag) {
		this.dealFlag = dealFlag;
	}
	
	public String getDealFlag() {
		return this.dealFlag;
	}
	
	/**
	 * 返回 DEAL_TIME     * @return
	 */
	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}
	
	public String getDealTime() {
		return this.dealTime;
	}
	
	/**
	 * 返回 REMARK     * @return
	 */
	public void setREMARK(String REMARK) {
		this.REMARK = REMARK;
	}
	
	public String getREMARK() {
		return this.REMARK;
	}
	
	/**
	 * 返回 CREATION_USER     * @return
	 */
	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}
	
	public String getCreationUser() {
		return this.creationUser;
	}
	
	/**
	 * 返回 CREATION_TIME     * @return
	 */
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}
	
	public String getCreationTime() {
		return this.creationTime;
	}
	
	/**
	 * 返回 LAST_MODIFIED_USER     * @return
	 */
	public void setLastModifiedUser(String lastModifiedUser) {
		this.lastModifiedUser = lastModifiedUser;
	}
	
	public String getLastModifiedUser() {
		return this.lastModifiedUser;
	}
	
	/**
	 * 返回 LAST_MODIFIED_TIME     * @return
	 */
	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	
	public String getLastModifiedTime() {
		return this.lastModifiedTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMakeDateStart() {
		return makeDateStart;
	}

	public void setMakeDateStart(String makeDateStart) {
		this.makeDateStart = makeDateStart;
	}

	public String getMakeDateEnd() {
		return makeDateEnd;
	}

	public void setMakeDateEnd(String makeDateEnd) {
		this.makeDateEnd = makeDateEnd;
	}

	public String getBillRowNo() {
		return billRowNo;
	}

	public void setBillRowNo(String billRowNo) {
		this.billRowNo = billRowNo;
	}

	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public String getPurchaseRowNo() {
		return purchaseRowNo;
	}

	public void setPurchaseRowNo(String purchaseRowNo) {
		this.purchaseRowNo = purchaseRowNo;
	}

	public String getRecVoucherYear() {
		return recVoucherYear;
	}

	public void setRecVoucherYear(String recVoucherYear) {
		this.recVoucherYear = recVoucherYear;
	}

	public String getRecVoucherBillno() {
		return recVoucherBillno;
	}

	public void setRecVoucherBillno(String recVoucherBillno) {
		this.recVoucherBillno = recVoucherBillno;
	}

	public String getRecVoucherRowno() {
		return recVoucherRowno;
	}

	public void setRecVoucherRowno(String recVoucherRowno) {
		this.recVoucherRowno = recVoucherRowno;
	}

	public String getRefRecVoucherBill() {
		return refRecVoucherBill;
	}

	public void setRefRecVoucherBill(String refRecVoucherBill) {
		this.refRecVoucherBill = refRecVoucherBill;
	}

	public String getRefRecVoucherRowno() {
		return refRecVoucherRowno;
	}

	public void setRefRecVoucherRowno(String refRecVoucherRowno) {
		this.refRecVoucherRowno = refRecVoucherRowno;
	}

	public String getRecDate() {
		return recDate;
	}

	public void setRecDate(String recDate) {
		this.recDate = recDate;
	}

	public String getLoanFlag() {
		return loanFlag;
	}

	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}

	public Integer getPriceStatus() {
		return priceStatus;
	}

	public void setPriceStatus(Integer priceStatus) {
		this.priceStatus = priceStatus;
	}

	public String getEvaPrice() {
		return evaPrice;
	}

	public void setEvaPrice(String evaPrice) {
		this.evaPrice = evaPrice;
	}

	public String getOfficialPrice() {
		return officialPrice;
	}

	public void setOfficialPrice(String officialPrice) {
		this.officialPrice = officialPrice;
	}

	public String getAdjustDiffPrice() {
		return adjustDiffPrice;
	}

	public void setAdjustDiffPrice(String adjustDiffPrice) {
		this.adjustDiffPrice = adjustDiffPrice;
	}

	public String getEvaPricePercent() {
		return evaPricePercent;
	}

	public void setEvaPricePercent(String evaPricePercent) {
		this.evaPricePercent = evaPricePercent;
	}

	public String getEvaSettlePrice() {
		return evaSettlePrice;
	}

	public void setEvaSettlePrice(String evaSettlePrice) {
		this.evaSettlePrice = evaSettlePrice;
	}

	public String getErpFactoryCode() {
		return erpFactoryCode;
	}

	public void setErpFactoryCode(String erpFactoryCode) {
		this.erpFactoryCode = erpFactoryCode;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getRecNum() {
		return recNum;
	}

	public void setRecNum(String recNum) {
		this.recNum = recNum;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public String getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}

	public String getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(String taxAmount) {
		this.taxAmount = taxAmount;
	}

	public String getPartUnit() {
		return partUnit;
	}

	public void setPartUnit(String partUnit) {
		this.partUnit = partUnit;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTotalInvoice() {
		return totalInvoice;
	}

	public void setTotalInvoice(String totalInvoice) {
		this.totalInvoice = totalInvoice;
	}

	public String getSubmitStatus() {
		return submitStatus;
	}

	public void setSubmitStatus(String submitStatus) {
		this.submitStatus = submitStatus;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public String getInvoiceNetPrice() {
		return invoiceNetPrice;
	}

	public void setInvoiceNetPrice(String invoiceNetPrice) {
		this.invoiceNetPrice = invoiceNetPrice;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
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

	public String[] getBillNoArr() {
		return billNoArr;
	}

	public void setBillNoArr(String[] billNoArr) {
		this.billNoArr = billNoArr;
	}

	public String getEvaSettlePriceBf() {
		return evaSettlePriceBf;
	}

	public void setEvaSettlePriceBf(String evaSettlePriceBf) {
		this.evaSettlePriceBf = evaSettlePriceBf;
	}

	
}
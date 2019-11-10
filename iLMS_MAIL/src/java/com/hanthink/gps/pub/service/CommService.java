package com.hanthink.gps.pub.service;

import java.util.List;

import com.hanthink.gps.pub.service.BaseService;
import com.hanthink.gps.pub.vo.CmbItemVO;
import com.hanthink.gps.pub.vo.SysMessageVO;
import com.hanthink.gps.pub.vo.SystemParamVO;

public interface CommService extends BaseService{

	
	
	/**
	 * 系统信息表中插入数据
	 * @param message 信息数据
	 * @return 插入的信息数据
	 */
	public SysMessageVO insertSysMessage(SysMessageVO message);
	public List<?> queryCmbItems(String type,String params, String supplierNo);
	
	public List<?> queryCmbItemsByFactory(String type,String params, String factory);
	
	/**
	 * 系统参数查询
	 * @return
	 */
	public SystemParamVO querySystemParam();
	
	/**
	 * 订购供应商下拉框
	 * @return
	 */
	public CmbItemVO querySupplierNameById(String code);
	
	/**
	 * SPS零件组下拉框
	 * @return
	 */
	public CmbItemVO queryPartGroupNoById(String code);
	
	public CmbItemVO querySpsPartById(String code);
	
	public CmbItemVO queryShipDepotById(CmbItemVO tmp);
	
	
}

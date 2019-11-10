package com.hanthink.gps.pub.dao;

import java.util.List;

import com.hanthink.gps.pub.vo.CmbItemVO;
import com.hanthink.gps.pub.vo.FactoryParamItemVO;
import com.hanthink.gps.pub.vo.SeqVO;
import com.hanthink.gps.pub.vo.SysMessageVO;
import com.hanthink.gps.pub.vo.SystemParamVO;

public interface CommDao {
	
	public List<?> queryCmbItems(String sqlId);
	public List<?> queryCmbItems(String sqlId, String param);
	public List<?> queryCmbItems(String sqlId, FactoryParamItemVO vo);

	public String querySeq(SeqVO seqVO);
	/**
	 * 系统信息表中插入数据
	 * @param message 信息数据
	 * @return 插入的信息数据
	 */
	public SysMessageVO insertSysMessage(SysMessageVO message);
	
	/**
	 * 系统参数查询
	 * @return
	 */
	public SystemParamVO querySystemParam();
	
	/**
	 * 订购供应商
	 * @return
	 */
	public CmbItemVO querySupplierNameById(String supplierId);
	
	public CmbItemVO queryPartGroupNoById(String code);
	public CmbItemVO querySpsPartById(String code);
	
	public CmbItemVO queryShipDepotById(CmbItemVO tmp);
	
	
}

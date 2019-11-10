package com.hanthink.gps.pub.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.hanthink.gps.pub.dao.BaseDaoSupport;
import com.hanthink.gps.pub.vo.SeqVO;
import com.hanthink.gps.pub.dao.CommDao;
import com.hanthink.gps.util.Constants;
import com.hanthink.gps.util.annotation.VoInsert;
import com.hanthink.gps.pub.vo.CmbItemVO;
import com.hanthink.gps.pub.vo.FactoryParamItemVO;
import com.hanthink.gps.pub.vo.SysMessageVO;
import com.hanthink.gps.pub.vo.SystemParamVO;

public class CommDaoImpl extends BaseDaoSupport implements CommDao {

	public List<?> queryCmbItems(String sqlId){
		return this.executeQueryForList(sqlId);
	}
	
	public String querySeq(SeqVO seqVO){
		return (String)this.executeQueryForObject("comm.select_sequence",seqVO);
	}

	public List<?> queryCmbItems(String sqlId, String param) {
		return this.executeQueryForList(sqlId, param);
	}
	
	/**
	 * 带工厂+其他参数动态查询
	 */
	public List<?> queryCmbItems(String sqlId, FactoryParamItemVO vo) {
		return this.executeQueryForList(sqlId, vo);
	}
	
	/**
	 * 系统信息表中插入数据
	 * @param message 信息数据
	 * @return 插入的信息数据
	 */
	@VoInsert
	public SysMessageVO insertSysMessage(SysMessageVO message){
		return (SysMessageVO)executeInsert("comm.insert_sysMessage", message);
	}

	/**
	 * 系统参数查询
	 * @return
	 */
	public SystemParamVO querySystemParam(){
		return (SystemParamVO)executeQueryForObject("comm.select_systemParam");
	}

	/**
	 * 订购供应商
	 * @return
	 * @date 2016-04-06
	 * @author anMin
	 */
	public CmbItemVO querySupplierNameById(String supplierId) {
		return (CmbItemVO)this.executeQueryForObject("comm.querySupplierNameById", supplierId);
	}

	public CmbItemVO queryPartGroupNoById(String code) {
		return (CmbItemVO)this.executeQueryForObject("comm.queryPartGroupNoById", code);
	}

	public CmbItemVO querySpsPartById(String code) {
		return (CmbItemVO)this.executeQueryForObject("comm.select_SpsPartId", code);
	}

	

	public CmbItemVO queryShipDepotById(CmbItemVO tmp) {
		
		String[] parm = tmp.getName().split("[.]");
		String userType = parm[1];
		
		CmbItemVO temp = new CmbItemVO();
		temp.setCode(tmp.getCode());
		temp.setName(parm[0]);
		
		CmbItemVO returnVO= new CmbItemVO();
		if(Constants.USER_TYPE_SUPPLIER.equals(userType)){
			returnVO =(CmbItemVO)this.executeQueryForObject("comm.select_ShipDepotBySupplier", temp);
		}else{
			if(!"".equals(tmp.getCode().trim()) ){
				returnVO =(CmbItemVO)this.executeQueryForObject("comm.select_ShipDepotByUser", temp);
			}else{
				returnVO =(CmbItemVO)this.executeQueryForObject("comm.select_ShipDepotByUserName", temp);
			}
		} 
		return returnVO;
	}

}

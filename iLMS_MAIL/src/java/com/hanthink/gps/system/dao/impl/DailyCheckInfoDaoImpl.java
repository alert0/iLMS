/**
 * 
 */
package com.hanthink.gps.system.dao.impl;

import java.util.List;

import com.hanthink.gps.pub.dao.BaseDaoSupport;
import com.hanthink.gps.pub.vo.SystemParamVO;
import com.hanthink.gps.system.dao.DailyCheckInfoDao;
import com.hanthink.gps.system.vo.CheckDBObjectSpaceVO;
import com.hanthink.gps.system.vo.CheckProcedureTimeVO;
import com.hanthink.gps.system.vo.CheckTableRowNumVO;
import com.hanthink.gps.system.vo.CheckTableSpaceVO;

/**
 * 描述：每日点检邮件发送信息DAO
 * @author chenyong
 * @date   2016-10-10
 */
public class DailyCheckInfoDaoImpl extends BaseDaoSupport implements DailyCheckInfoDao{


	//统计表空间信息
	@SuppressWarnings("unchecked")
	@Override
	public List<CheckTableSpaceVO> checkTableSpace(CheckTableSpaceVO vo) {
		return (List<CheckTableSpaceVO>) this.executeQueryForList("system.checkTableSpace", vo);
	}

	
	//统计存储过程执行时间
	@SuppressWarnings("unchecked")
	@Override
	public List<CheckProcedureTimeVO> checkProcedureTime() {
		
		return (List<CheckProcedureTimeVO>) this.executeQueryForList("system.checkProcedureTime");
	}


	//统计数据库对象占用空间信息
	@SuppressWarnings("unchecked")
	@Override
	public List<CheckDBObjectSpaceVO> checkDBObjectSpace(CheckDBObjectSpaceVO userSegmentsVO) {
		
		return  (List<CheckDBObjectSpaceVO>) this.executeQueryForList("system.checkDBObjectSpace", userSegmentsVO);
	}


    //统计数据表行数信息
	@SuppressWarnings("unchecked")
	@Override
	public List<CheckTableRowNumVO> checkTableRowNum(CheckTableRowNumVO checkTableRowNumVO) {
		
		return (List<CheckTableRowNumVO>) this.executeQueryForList("system.checkTableRowNum", checkTableRowNumVO);
	}

	/**
	 * 根据系统参数代码获取系统参数
	 */
	@Override
	public SystemParamVO queryParamByParamCode(SystemParamVO vo) {
		return (SystemParamVO) this.executeQueryForObject("system.queryParamByParamCode", vo);
	}
    
	
}

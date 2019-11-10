package com.hanthink.gps.jis.dao.impl;

import java.util.List;

import com.hanthink.gps.jis.dao.JisExceDao;
import com.hanthink.gps.jis.vo.JisOutReckonStopVO;
import com.hanthink.gps.jis.vo.JisPartVO;
import com.hanthink.gps.pub.dao.BaseDaoSupport;

/**
 * @Desc    : 同步异常提醒DAO
 * @FileName: JisExceDaoImpl.java 
 * @CreateOn: 2016-7-27 下午03:40:26
 * @author  : zuosl  
 *
 * @ChangeList
 * Date			Version		Editor		ChangeReasons
 * 2016-7-27	V1.0		zuosl		新建
 * 
 *
 */
public class JisExceDaoImpl extends BaseDaoSupport implements JisExceDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<JisPartVO> queryJisoMtocPartInfo(JisPartVO qvo) {
		return (List<JisPartVO>)this.executeQueryForList("jis.select_queryJisoMtocPartInfo", qvo);
	}

	/**厂外同步推算服务停止提醒信息
	 *  add   by chenyong
	 *  date  2016-09-21
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JisOutReckonStopVO> queryJisOutReckonStopInfo(JisOutReckonStopVO jvo) {
		// TODO Auto-generated method stub
		return (List<JisOutReckonStopVO>) this.executeQueryForList("jis.select_queryJisOutReckonStopInfo",jvo);
	}

}

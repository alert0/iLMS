package com.hanthink.gps.jis.service.impl;

import java.util.List;

import com.hanthink.gps.jis.dao.JisExceDao;
import com.hanthink.gps.jis.service.JisExceService;
import com.hanthink.gps.jis.vo.JisOutReckonStopVO;
import com.hanthink.gps.jis.vo.JisPartVO;
import com.hanthink.gps.pub.service.impl.BaseServiceImpl;

/**
 * @Desc    : 同步异常提醒Service
 * @FileName: JisExceServiceImpl.java 
 * @CreateOn: 2016-7-27 下午03:38:37
 * @author  : zuosl  
 *
 * @ChangeList
 * Date			Version		Editor		ChangeReasons
 * 2016-7-27	V1.0		zuosl		新建
 * 
 *
 */
public class JisExceServiceImpl extends BaseServiceImpl implements
		JisExceService {

	private JisExceDao dao;

	public JisExceDao getDao() {
		return dao;
	}
	public void setDao(JisExceDao dao) {
		this.dao = dao;
	}
	
	@Override
	public List<JisPartVO> queryJisoMtocPartInfo(JisPartVO qvo) {
		return dao.queryJisoMtocPartInfo(qvo);
	}
	
	/**
	 * 厂内同步推算停止异常信息查询
	 * add by chenyong
	 * date 2016-09-21
	 */
@Override
	public List<JisOutReckonStopVO> queryJisOutReckonStopInfo(JisOutReckonStopVO jvo) {
		
		return dao.queryJisOutReckonStopInfo(jvo);
	}
	
	
	
}

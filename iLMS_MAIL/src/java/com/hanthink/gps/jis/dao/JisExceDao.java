package com.hanthink.gps.jis.dao;

import java.util.List;

import com.hanthink.gps.jis.vo.JisOutReckonStopVO;
import com.hanthink.gps.jis.vo.JisPartVO;

/**
 * @Desc    : 同步异常提醒DAO 
 * @FileName: JisExceDao.java 
 * @CreateOn: 2016-7-27 下午03:39:48
 * @author  : zuosl  
 *
 * @ChangeList
 * Date			Version		Editor		ChangeReasons
 * 2016-7-27	V1.0		zuosl		新建
 * 
 *
 */
public interface JisExceDao {

	/**
	 * 查询厂外同步MTOC零件明细未维护信息
	 * @param qvo
	 * @return
	 * @author zuosl 2016-7-27
	 */
	List<JisPartVO> queryJisoMtocPartInfo(JisPartVO qvo);
	
	/**
	 * 厂外同步推算服务停止提醒信息
	 * @param jvo
	 * @return
	 * @author chenyong 
	 * @date 2016-09-21
	 */
	List<JisOutReckonStopVO> queryJisOutReckonStopInfo(JisOutReckonStopVO jvo);
	

}

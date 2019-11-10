package com.hanthink.gps.jis.service;

import java.util.List;

import com.hanthink.gps.jis.vo.JisOutReckonStopVO;
import com.hanthink.gps.jis.vo.JisPartVO;
import com.hanthink.gps.pub.service.BaseService;

/**
 * @Desc    : 同步异常提醒信息Service
 * @FileName: JisExceService.java 
 * @CreateOn: 2016-7-27 下午03:37:37
 * @author  : zuosl  
 *
 * @ChangeList
 * Date			Version		Editor		ChangeReasons
 * 2016-7-27	V1.0		zuosl		新建
 * 
 *
 */
public interface JisExceService extends BaseService {

	/**
	 * 查询厂外同步MTOC零件明细未维护信息
	 * @param qvo
	 * @return
	 * @author zuosl 2016-7-27
	 */
	List<JisPartVO> queryJisoMtocPartInfo(JisPartVO qvo);
	
	/**
	 * 厂外推算服务停止提示消息信息查询
	 * @param
	 * @return
	 * @author chenyong 
	 * @date 2016-09-21
	 */
	public List<JisOutReckonStopVO> queryJisOutReckonStopInfo(JisOutReckonStopVO jVo);

}

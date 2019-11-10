/**
 * 
 */
package com.hanthink.gps.system.service.impl;

import java.util.List;

import com.hanthink.gps.pub.service.impl.BaseServiceImpl;
import com.hanthink.gps.system.dao.InterfErrorDao;
import com.hanthink.gps.system.service.InterfErrorService;
import com.hanthink.gps.system.vo.InterfErrorVO;

/**
 * 接口异常信息邮件提醒service
 * @author chenyong
 * @date 2016-09-21
 *
 */
public class InterfErrorServiceImpl extends BaseServiceImpl implements InterfErrorService{

	private InterfErrorDao dao;
	
	//查询接口异常信息
	@Override
	public List<InterfErrorVO> queryG1interfErrorInfo() {
		
		return dao.queryG1interfErrorInfo();
	}

	
	//get  和  set 方法
	public InterfErrorDao getDao() {
		return dao;
	}

	public void setDao(InterfErrorDao dao) {
		this.dao = dao;
	}
  
}

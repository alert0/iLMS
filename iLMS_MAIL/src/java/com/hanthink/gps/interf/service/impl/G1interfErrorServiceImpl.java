/**
 * 
 */
package com.hanthink.gps.interf.service.impl;

import java.util.List;

import com.hanthink.gps.interf.dao.G1interfErrorDao;
import com.hanthink.gps.interf.service.G1interfErrorService;
import com.hanthink.gps.interf.vo.G1interfErrorVO;
import com.hanthink.gps.pub.service.impl.BaseServiceImpl;

/**
 * 接口异常信息邮件提醒service
 * @author chenyong
 * @date 2016-09-21
 *
 */
public class G1interfErrorServiceImpl extends BaseServiceImpl implements G1interfErrorService{

	private G1interfErrorDao dao;
	
	//查询接口异常信息
	@Override
	public List<G1interfErrorVO> queryG1interfErrorInfo() {
		
		return dao.queryG1interfErrorInfo();
	}

	
	//get  和  set 方法
	public G1interfErrorDao getDao() {
		return dao;
	}

	public void setDao(G1interfErrorDao dao) {
		this.dao = dao;
	}
  
}

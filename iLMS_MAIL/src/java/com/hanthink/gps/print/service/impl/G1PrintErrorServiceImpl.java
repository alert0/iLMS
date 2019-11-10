/**
 * 
 */
package com.hanthink.gps.print.service.impl;

import java.util.List;

import com.hanthink.gps.print.dao.G1PrintErrorDao;
import com.hanthink.gps.print.service.G1PrintErrorService;
import com.hanthink.gps.print.vo.G1PrintErrorVO;
import com.hanthink.gps.pub.service.impl.BaseServiceImpl;

/**
 * 自动打印异常邮件信息提醒
 * @author chenyong
 * @date   2016-09-22
 */
public class G1PrintErrorServiceImpl extends BaseServiceImpl implements G1PrintErrorService{
	
  private G1PrintErrorDao dao;
  
  //自动打印异常信息查询
@Override
public List<G1PrintErrorVO> queryG1PrintErrorInfo(G1PrintErrorVO pVo) {
	
	return dao.queryG1PrintErrorInfo(pVo);
}
  

public G1PrintErrorDao getDao() {
	return dao;
}

public void setDao(G1PrintErrorDao dao) {
	this.dao = dao;
}
  
  
}

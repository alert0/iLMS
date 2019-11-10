/**
 * 
 */
package com.hanthink.gps.system.dao.impl;

import java.util.List;

import com.hanthink.gps.pub.dao.BaseDaoSupport;
import com.hanthink.gps.system.dao.InterfErrorDao;
import com.hanthink.gps.system.vo.InterfErrorVO;

/**
 * 二线接口定时器异常信息邮件提示
 * @author chenyong 
 * @date 2016-09-22
 */
public class InterfErrorDaoImpl extends BaseDaoSupport implements InterfErrorDao{

	//查询接口异常信息
	@SuppressWarnings("unchecked")
	@Override
	public List<InterfErrorVO> queryG1interfErrorInfo() {
	
		return (List<InterfErrorVO>) this.executeQueryForList("system.select_interfErrorInfo");
	}

}

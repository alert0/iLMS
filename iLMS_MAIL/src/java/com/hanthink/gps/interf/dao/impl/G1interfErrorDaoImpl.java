/**
 * 
 */
package com.hanthink.gps.interf.dao.impl;

import java.util.List;

import com.hanthink.gps.interf.dao.G1interfErrorDao;
import com.hanthink.gps.interf.vo.G1interfErrorVO;

import com.hanthink.gps.pub.dao.BaseDaoSupport;

/**
 * 二线接口定时器异常信息邮件提示
 * @author chenyong 
 * @date 2016-09-22
 */
public class G1interfErrorDaoImpl extends BaseDaoSupport implements G1interfErrorDao{

	//查询接口异常信息
	@SuppressWarnings("unchecked")
	@Override
	public List<G1interfErrorVO> queryG1interfErrorInfo() {
	
		return (List<G1interfErrorVO>) this.executeQueryForList("if.select_G2interfErrorInfo");
	}

}

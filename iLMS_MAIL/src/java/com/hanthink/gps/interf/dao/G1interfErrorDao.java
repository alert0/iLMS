/**
 * 
 */
package com.hanthink.gps.interf.dao;

import java.util.List;

import com.hanthink.gps.interf.vo.G1interfErrorVO;

/**
 * 接口异常邮件提醒dao
 * @author chenyong
 * @date   2016-09-21
 */
public interface G1interfErrorDao {
    
	public List<G1interfErrorVO> queryG1interfErrorInfo();
}

/**
 * 
 */
package com.hanthink.gps.system.dao;

import java.util.List;

import com.hanthink.gps.system.vo.InterfErrorVO;

/**
 * 接口异常邮件提醒dao
 * @author chenyong
 * @date   2016-09-21
 */
public interface InterfErrorDao {
    
	public List<InterfErrorVO> queryG1interfErrorInfo();
}

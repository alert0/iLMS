/**
 * 
 */
package com.hanthink.gps.system.dao;

import java.util.List;

import com.hanthink.gps.system.vo.PrintErrorVO;

/**
 * 自动打印异常Dao
 * @author chenyong
 * @date 2016-09-21
 *
 */
public interface PrintErrorDao {
  
	public List<PrintErrorVO> queryG1PrintErrorInfo(PrintErrorVO pVo);
}

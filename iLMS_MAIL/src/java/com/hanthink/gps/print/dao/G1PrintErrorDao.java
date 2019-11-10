/**
 * 
 */
package com.hanthink.gps.print.dao;

import java.util.List;

import com.hanthink.gps.print.vo.G1PrintErrorVO;

/**
 * 一线自动打印异常Dao
 * @author chenyong
 * @date 2016-09-21
 *
 */
public interface G1PrintErrorDao {
  
	public List<G1PrintErrorVO> queryG1PrintErrorInfo(G1PrintErrorVO pVo);
}

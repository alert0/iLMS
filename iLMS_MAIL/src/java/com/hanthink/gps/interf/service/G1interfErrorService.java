/**
 * 
 */
package com.hanthink.gps.interf.service;

import java.util.List;

import com.hanthink.gps.interf.vo.G1interfErrorVO;
import com.hanthink.gps.pub.service.BaseService;

/**
 * 接口异常哟见提醒service
 * @author chenyong
 * @date   2016-09-21
 *
 */
public interface G1interfErrorService extends BaseService{

	public List<G1interfErrorVO> queryG1interfErrorInfo();
}

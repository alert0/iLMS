/**
 * 
 */
package com.hanthink.gps.system.service;

import java.util.List;

import com.hanthink.gps.pub.service.BaseService;
import com.hanthink.gps.system.vo.InterfErrorVO;

/**
 * 接口异常提醒service
 * @author chenyong
 * @date   2016-09-21
 *
 */
public interface InterfErrorService extends BaseService{

	public List<InterfErrorVO> queryG1interfErrorInfo();
}

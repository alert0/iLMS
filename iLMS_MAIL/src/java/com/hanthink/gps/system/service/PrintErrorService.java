/**
 * 
 */
package com.hanthink.gps.system.service;

import java.util.List;

import com.hanthink.gps.pub.service.BaseService;
import com.hanthink.gps.system.vo.PrintErrorVO;

/**
 * 自动打印异常邮件提醒
 * @author chenyong
 * @date 2016-09-22
 *
 */
public interface PrintErrorService extends BaseService{

	public List<PrintErrorVO> queryG1PrintErrorInfo(PrintErrorVO pVo);
}

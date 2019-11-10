/**
 * 
 */
package com.hanthink.gps.print.service;

import java.util.List;

import com.hanthink.gps.print.vo.G1PrintErrorVO;
import com.hanthink.gps.pub.service.BaseService;

/**
 * 自动打印异常邮件提醒Vo
 * @author chenyong
 * @date 2016-09-22
 *
 */
public interface G1PrintErrorService extends BaseService{

	public List<G1PrintErrorVO> queryG1PrintErrorInfo(G1PrintErrorVO pVo);
}

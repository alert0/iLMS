/**
 * 
 */
package com.hanthink.gps.interf.dao;

import java.util.List;

import com.hanthink.gps.interf.vo.PECGpsMesIfErrorVO;

/**
 * 描述：PECGPS和MES接口异常信息邮件提醒DAO
 * @author chenyong
 * @date   2016-10-18
 * 
 * 备注：此处描述写的为接口异常提醒，是因为前期按接口异常设计编写，
 * 后续优化改造已将该邮件封装为公用的系统警讯邮件提醒，即每个业务如果有异常，
 * 只要将异常信息写入到指定的系统警讯数据表，则系统就会发出当前编写的提醒邮件。
 * 因后续优化改造对前期的文件变量命名等涉及比较多修改困难，故还是保持原来接口异常的命名。
 */
public interface PECGpsMesIfErrorDao {
    
	/**
	 * 查询A级警讯信息
	 * @return  PECGpsMesIfErrorVO A级警讯信息
	 */
	public List<PECGpsMesIfErrorVO> queryPECGpsMesIfErrorA();
	
	/**
	 * 查询非A级警讯信息
	 * @return PECGpsMesIfErrorVO 非A级警讯信息
	 */
	public List<PECGpsMesIfErrorVO> queryPECGpsMesIfErrorNotA();
	
	/**
	 * 修改非A级别的数据为已处理
	 */
	public void updateNotAIsHandle();
}

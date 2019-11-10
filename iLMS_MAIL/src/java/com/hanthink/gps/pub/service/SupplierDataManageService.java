package com.hanthink.gps.pub.service;

import java.util.List;

import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.vo.SupplierVO;

public interface SupplierDataManageService extends BaseService {

	/**
	 * 分页查询供应商数据
	 * @param supVO
	 * @param start
	 * @param limit
	 * @return
	 * @author zuosl 2016-5-9
	 */
	PageObject querySupplierDataForPage(SupplierVO supVO, int start, int limit);

	/**
	 * 供应商激活处理
	 * @param supVO
	 * @param genPwd
	 * @param roleIds
	 * @return
	 * @author zuosl 2016-5-10
	 */
	boolean activeSupplier(SupplierVO supVO, String genPwd, List<String> roleIds);

	/**
	 * 根据供应商代码查询供应商信息
	 * @param supplierNo
	 * @return
	 * @author zuosl 2016-5-10
	 */
	SupplierVO querySupplierInfoBySupplierNo(String supplierNo);

	/**
	 * 更新供应商激活状态
	 * @param supVO
	 * @return
	 * @author zuosl 2016-5-10
	 */
	int updateSupplierActiveStatus(SupplierVO supVO);

	/**
	 * 更新供应商信息
	 * @param supVO
	 * @return
	 * @author zuosl 2016-5-10
	 */
	int updateSupplierInfoBySupplierNo(SupplierVO supVO);
	

}

package com.hanthink.mon.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.HashBiMap;
import com.hanthink.mon.dao.MonEmptyReturnKbDao;
import com.hanthink.mon.model.EmptyReturnKbModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;


/**
 * 
 * <pre> 
 * 描述：空箱返还看板查询 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Repository
public class MonEmptyReturnKbDaoImpl extends MyBatisDaoImpl<String, EmptyReturnKbModel> implements MonEmptyReturnKbDao{

    @Override
    public String getNamespace() {
        return EmptyReturnKbModel.class.getName();
    }

    /**
     * 查询DCS单头信息
     */
	@Override
	public List<EmptyReturnKbModel> selectDCS(String retEmptyPlatform, String workCenter)throws Exception {
		Map<String,Object> map = new HashMap<>();
		map.put("retEmptyPlatform", retEmptyPlatform);
		map.put("workCenter", workCenter); 
		return (List<EmptyReturnKbModel>) this.getList("selectDCS", map);
	}

	/**
     * 查询DCS单明细信息
     */
	@Override
	public List<EmptyReturnKbModel> selectDCSDetail(EmptyReturnKbModel model) throws Exception{
		return (List<EmptyReturnKbModel>) this.getList("selectDCSDetail", model);
	}

	/**
     * 若为取货订单
     */
	@Override
	public List<EmptyReturnKbModel> selectOrderNoBySw(EmptyReturnKbModel emptyReturnKbModel) throws Exception{
		return (List<EmptyReturnKbModel>) this.getList("selectOrderNoBySw", emptyReturnKbModel);
	}

	/**
	 * 若为拉动订单
	 */
	@Override
	public List<EmptyReturnKbModel> selectOrderNoByJit(EmptyReturnKbModel emptyReturnKbModel)throws Exception {
		return (List<EmptyReturnKbModel>) this.getList("selectOrderNoByJit", emptyReturnKbModel);
	}

	/**
	 * 若为同步订单
	 */
	@Override
	public List<EmptyReturnKbModel> selectOrderNoByJiso(EmptyReturnKbModel emptyReturnKbModel)throws Exception {
		return (List<EmptyReturnKbModel>) this.getList("selectOrderNoByJiso", emptyReturnKbModel);
	}

	/**
	 * 根据IP获取站台
	 */
	@Override
	public EmptyReturnKbModel getShowMessage(Map<String, String> paramMap)throws Exception {
		return this.sqlSessionTemplate.selectOne("getShowMessage", paramMap);
	}

	/**
	 * 查询订单
	 */
	@Override
	public List<EmptyReturnKbModel> selectOrderNoByNull() {
		return (List<EmptyReturnKbModel>) this.getList("selectOrderNoByNull",null);
	}

	/**
	 * 根据comnIp查询IP地址
	 */
	@Override
	public EmptyReturnKbModel selectIpByCombIp(String combIp) {
		return this.sqlSessionTemplate.selectOne("selectIpByCombIp",combIp);
	}

	/**
	 * 更新DCS单状态
	 */
	@Override
	public void updateDCS(EmptyReturnKbModel model) {
		this.updateByKey("updateDCS",model);
	}

	/**
	 * 获取各种时间
	 */
	@Override
	public List<EmptyReturnKbModel> selectPubSysParam() {
		return (List<EmptyReturnKbModel>) this.getList("selectPubSysParam",null);
	}

	/**
	 * 根据IP获取车间
	 */
	@Override
	public String selectWorkCenterByIp(String ip) {
		return this.sqlSessionTemplate.selectOne("selectWorkCenterByIpEmpty", ip);
	}

	/**
	 * 查询DCS单头信息
	 */
	@Override
	public List<EmptyReturnKbModel> selectDCSTop(String retEmptyPlatform, String workCenter) {
		Map<String,Object> map = new HashMap<>();
		map.put("retEmptyPlatform", retEmptyPlatform);
		map.put("workCenter", workCenter); 
		return (List<EmptyReturnKbModel>) this.getList("selectDCSTop", map);
	}

	/**
	 * 拉绳查询
	 */
	@Override
	public List<EmptyReturnKbModel> selectDCSUp(String retEmptyPlatform) {
		return (List<EmptyReturnKbModel>) this.getList("selectDCSUp", retEmptyPlatform);
	}

}


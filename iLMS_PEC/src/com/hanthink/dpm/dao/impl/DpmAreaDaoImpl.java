package com.hanthink.dpm.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.base.model.DictVO;
import com.hanthink.dpm.dao.DpmAreaDao;
import com.hanthink.dpm.model.DpmAreaModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre>
 * Description:
 * Company: HanThink
 * Date: 2018年9月11日 下午4:06:29
 * </pre>
 * @author luoxq
 */
@Repository
public class DpmAreaDaoImpl extends MyBatisDaoImpl<String, DpmAreaModel> implements DpmAreaDao{


	@Override
	public String getNamespace() {
		
		return DpmAreaModel.class.getName();
	}

	@Override
	public DpmAreaModel getByCode(String areaCode) {
		
		return this.getUnique("getByCode", areaCode);
	}

	@Override
	public List getUnloadCode() {
		IUser user = ContextUtil.getCurrentUser();
		Map<String,Object> map=new HashMap<>();
		map.put("factoryCode", user.getCurFactoryCode());
		return this.getList("getUnloadCode", map);
	}

	@Override
	public PageList<DpmAreaModel> queryDpmAreaForPage(DpmAreaModel model, DefaultPage p) {
		
		return (PageList<DpmAreaModel>) this.getByKey("queryDpmAreaForPage", model, p);
	}

	@Override
	public DpmAreaModel getWareNameByCode(String wareCode) {
		return this.getUnique("getWareNameByCode", wareCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DictVO> getUnloadWareCode() {
		IUser user = ContextUtil.getCurrentUser();
		Map<String,Object> map=new HashMap<>();
		map.put("factoryCode", user.getCurFactoryCode());
		return (List<DictVO>) this.getList("getUnloadWareCode", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DictVO> getUnloadWorkcenter(String factoryCode) {
		Map<String,Object> map=new HashMap<>();
		map.put("factoryCode", factoryCode);
		return (List<DictVO>) this.getList("getUnloadWorkcenter", map);
	}

}

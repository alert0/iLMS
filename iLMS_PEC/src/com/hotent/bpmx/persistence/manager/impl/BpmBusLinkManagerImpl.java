package com.hotent.bpmx.persistence.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oracle.net.aso.e;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.datasource.DataSourceUtil;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bo.api.bodef.BoDefService;
import com.hotent.bo.api.model.BaseBoEnt;
import com.hotent.bpmx.core.defxml.entity.ext.BoSaveMode;
import com.hotent.bpmx.persistence.dao.BpmBusLinkDao;
import com.hotent.bpmx.persistence.manager.BpmBusLinkManager;
import com.hotent.bpmx.persistence.model.BpmBusLink;

@Service("bpmBusLinkManager")
public class BpmBusLinkManagerImpl extends AbstractManagerImpl<String, BpmBusLink> implements BpmBusLinkManager {
	@Resource
	BpmBusLinkDao bpmBusLinkDao;
	@Resource
	JdbcTemplate jdbcTemplate;
	@Resource
	BoDefService boDefService;

	@Override
	protected Dao<String, BpmBusLink> getDao() {
		return bpmBusLinkDao;
	}

	@Override
	public BpmBusLink getByBusinesKey(String businessKey, String formIdentity, boolean isNumber) {
		return bpmBusLinkDao.getByBusinesKey(businessKey, formIdentity, isNumber);

	}

	@Override
	public void delByBusinesKey(String businessKey, String formIdentity, boolean isNumber) {
		bpmBusLinkDao.delByBusinesKey(businessKey, formIdentity, isNumber);
	}

	@Override
	public List<BpmBusLink> getByInstId(String instId) {
		return bpmBusLinkDao.getByInstId(instId);
	}

	@Override
	public BpmBusLink getByBusinesKey(String businessKey, boolean isNumber) {
		return bpmBusLinkDao.getByBusinesKey(businessKey, isNumber);
	}

	@Override
	public void create(BpmBusLink entity) {
		super.create(entity);
	}

	@Override
	public void removeDataByDefId(String defId) {
		// 删除bpm_bus_link和对应的表数据
		List<BpmBusLink> links = getByDefId(defId);
		for (BpmBusLink link : links) {
			String idVal = StringUtil.isEmpty(link.getBusinesskeyStr()) ? link.getBusinesskey().toString() : link.getBusinesskeyStr();
			if (link.getSaveMode().equals(BoSaveMode.BO_OBJECT.value())) {
				// 删除xbo_inst的数据
				String sql = "delete from xbo_inst where id_ = '" + idVal + "'";
				jdbcTemplate.execute(sql);
			} else if (link.getSaveMode().equals(BoSaveMode.DATABASE.value())) {
				BaseBoEnt ent = boDefService.getEntByName(link.getFormIdentify());
				if(BeanUtils.isNotEmpty(ent)){
					String sql = "delete from " + ent.getTableName() + " where " + ent.getPkKey() + " = '" + idVal + "'";
					if (ent.isExternal()) {// 外部表
						try {
							DataSourceUtil.getJdbcTempByDsAlias(ent.getDsName()).execute(sql);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						jdbcTemplate.execute(sql);
					}
				}
			}

			// 删除Bpm_bus_link数据
			remove(link.getId());
		}
	}

	@Override
	public List<BpmBusLink> getByDefId(String defId) {
		return bpmBusLinkDao.getByDefId(defId);
	}

	@Override
	public Map<String, BpmBusLink> getMapByInstId(String instId) {
		Map<String, BpmBusLink> map = new HashMap<String, BpmBusLink>();
		List<BpmBusLink> list = this.getByInstId(instId);
		for (BpmBusLink busLink : list) {
			map.put(busLink.getBoDefCode(), busLink);
		}
		return map;
	}

}

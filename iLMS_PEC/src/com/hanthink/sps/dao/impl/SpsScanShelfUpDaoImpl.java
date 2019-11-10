package com.hanthink.sps.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.sps.dao.SpsScanShelfUpDao;
import com.hanthink.sps.model.SpsScanShelfUpModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: SpsScanShelfUpDaoImpl
 * @Description: SPS过点车序查询
 * @author dtp
 * @date 2018年10月16日
 */
@Repository
public class SpsScanShelfUpDaoImpl extends MyBatisDaoImpl<String, SpsScanShelfUpModel> implements SpsScanShelfUpDao {

	@Override
	public String getNamespace() {
		return SpsScanShelfUpModel.class.getName();
	}

	/**
	 * SPS扫描上架
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<SpsScanShelfUpModel> querySpsScanShelfUpPage(SpsScanShelfUpModel model, DefaultPage page) {
		return (PageList<SpsScanShelfUpModel>) this.getList("querySpsScanShelfUpPage", model, page);
	}

	/**
	 * 明细导出
	 */
	@Override
	public List<SpsScanShelfUpModel> querySpsScanShelfUpByKey(SpsScanShelfUpModel model) {
		return (List<SpsScanShelfUpModel>) this.getList("querySpsScanShelfUpPage", model);
	}

	/**
	 * 查询默认值
	 */
	@Override
	public SpsScanShelfUpModel selectDefaultValue(SpsScanShelfUpModel model) {
		if (("A1").equals(model.getWorkCenter())) {
			return (SpsScanShelfUpModel) this.sqlSessionTemplate.selectOne("selectDefaultValueAC", model);
		} else if (("W1").equals(model.getWorkCenter())) {
			return (SpsScanShelfUpModel) this.sqlSessionTemplate.selectOne("selectDefaultValueWC", model);
		}else {
			return null;
		}
	}

}

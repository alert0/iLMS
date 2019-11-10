package com.hanthink.mp.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.mp.dao.MpOrderBomDao;
import com.hanthink.mp.model.MpOrderBomModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;


/**
 * 订购使用单车BOM
 * @author WY
 *
 */
@Repository
public class MpOrderBomDaoImpl extends MyBatisDaoImpl<String, MpOrderBomModel> implements MpOrderBomDao{

    @Override
    public String getNamespace() {
        return MpOrderBomModel.class.getName();
    }

	@Override
	public List<MpOrderBomModel> queryMpOrderBomForPage(
			MpOrderBomModel model, DefaultPage p) {
		return this.getByKey("queryMpOrderBomForPage", model, p);
	}

	@Override
	public List<MpOrderBomModel> queryMpOrderBomByKey(MpOrderBomModel model) {
		return this.getByKey("queryMpOrderBomForPage", model);
	}

	@Override
	public Integer genOrderBom(HashMap<String, String> paramMap) {
		//获取订购的单车BOM
		this.sqlSessionTemplate.selectOne("genOrderBom",paramMap);
		return Integer.parseInt(paramMap.get("outCode"));
	}
	
}


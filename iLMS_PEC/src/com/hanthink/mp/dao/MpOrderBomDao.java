package com.hanthink.mp.dao;
import java.util.HashMap;
import java.util.List;

import com.hanthink.mp.model.MpOrderBomModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;


/**
 * 订购使用单车BOM
 * @author WY
 *
 */
public interface MpOrderBomDao extends Dao<String, MpOrderBomModel> {

	/**
	 * 查询订购单车BOM
	 * @param model
	 * @param p
	 * @return
	 */
	List<MpOrderBomModel> queryMpOrderBomForPage(MpOrderBomModel model,
			DefaultPage p);

	/**
	 * 查询单车BOM数据导出
	 * @param model
	 * @return
	 */
	List<MpOrderBomModel> queryMpOrderBomByKey(MpOrderBomModel model);

	/**
	 * 获取订购单车BOM
	 * @param paramMap
	 * @return
	 */
	Integer genOrderBom(HashMap<String, String> paramMap);
	
	
}

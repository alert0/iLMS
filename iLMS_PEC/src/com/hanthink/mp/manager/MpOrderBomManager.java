package com.hanthink.mp.manager;

import java.util.List;

import com.hanthink.mp.model.MpOrderBomModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;


/**
 * 订购使用单车BOM
 * @author WY
 *
 */
public interface MpOrderBomManager extends Manager<String, MpOrderBomModel>{

	/**
	 * 分页查询订购单车BOM数据
	 * @param model
	 * @param p
	 * @return
	 */
	PageList<MpOrderBomModel> queryMpOrderBomForPage(MpOrderBomModel model,
			DefaultPage p);

	/**
	 * 查询单车BOM数据导出
	 * @param model
	 * @return
	 */
	List<MpOrderBomModel> queryMpOrderBomByKey(MpOrderBomModel model);

	/**
	 * 生成订购单车BOM
	 * @param curFactoryCode
	 * @param account
	 * @return
	 */
	Integer genOrderBom(String curFactoryCode, String account);

	 

}

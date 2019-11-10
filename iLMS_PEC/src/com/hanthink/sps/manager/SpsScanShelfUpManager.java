package com.hanthink.sps.manager;

import java.util.List;

import com.hanthink.sps.model.SpsScanShelfUpModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

/**
 * @ClassName: SpsScanShelfUpManager
 * @Description: SPS过点车序查询
 * @author dtp
 * @date 2018年10月16日
 */
public interface SpsScanShelfUpManager extends Manager<String, SpsScanShelfUpModel>{

	/**
	 * SPS扫描上架
	 * <p>return: PageList<SpsScanShelfUpModel></p>  
	 * <p>Description: SpsScanShelfUpManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年4月4日
	 * @version 1.0
	 */
	PageList<SpsScanShelfUpModel> querySpsScanShelfUpPage(SpsScanShelfUpModel model, DefaultPage page);

	/**
	 * 明细导出
	 * <p>return: List<SpsScanShelfUpModel></p>  
	 * <p>Description: SpsScanShelfUpManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年4月4日
	 * @version 1.0
	 */
	List<SpsScanShelfUpModel> querySpsScanShelfUpByKey(SpsScanShelfUpModel model);

	/**
	 * 查询默认值
	 * @param model
	 * @return
	 */
	SpsScanShelfUpModel selectDefaultValue(SpsScanShelfUpModel model);
}

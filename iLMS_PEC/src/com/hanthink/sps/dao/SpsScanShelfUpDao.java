package com.hanthink.sps.dao;

import java.util.List;

import com.hanthink.base.model.DictVO;
import com.hanthink.sps.model.SpsScanShelfUpModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: SpsScanShelfUpDao
 * @Description: SPS扫描上架
 * @author dtp
 * @date 2018年10月16日
 */
public interface SpsScanShelfUpDao extends Dao<String, SpsScanShelfUpModel>{
	
	/**
	 * SPS扫描上架
	 * <p>return: PageList<SpsScanShelfUpModel></p>  
	 * <p>Description: SpsScanShelfUpDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年4月4日
	 * @version 1.0
	 */
	PageList<SpsScanShelfUpModel> querySpsScanShelfUpPage(SpsScanShelfUpModel model, DefaultPage page);

	/**
	 * 导出
	 * <p>return: List<SpsScanShelfUpModel></p>  
	 * <p>Description: SpsScanShelfUpDao.java</p>  
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

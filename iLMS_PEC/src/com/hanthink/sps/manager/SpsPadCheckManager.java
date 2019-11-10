package com.hanthink.sps.manager;

import java.util.List;

import com.hanthink.base.model.DictVO;
import com.hanthink.sps.model.SpsPadCheckModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

/**
 * @ClassName: SpsPadCheckManager
 * @Description: SPS过点车序查询
 * @author dtp
 * @date 2018年10月16日
 */
public interface SpsPadCheckManager extends Manager<String, SpsPadCheckModel>{

	/**
	 * @Description: SPS过点车序查询
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<SpsPadCheckModel>   
	 * @author: dtp
	 * @date: 2018年10月16日
	 */
	PageList<SpsPadCheckModel> querySpsPadCheckPage(SpsPadCheckModel model, DefaultPage page);

	/**
	 * 获取票据模板填充下拉框
	 * <p>return: List<DictVO></p>  
	 * <p>Description: SpsPadCheckManager.java</p>  
	 * @author linzhuo  
	 * @date 2018年12月7日
	 * @version 1.0
	 */
	List<DictVO> getMouldName();
}

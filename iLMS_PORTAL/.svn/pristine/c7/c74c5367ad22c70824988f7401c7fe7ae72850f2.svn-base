package com.hanthink.dpm.dao;

import com.hanthink.demo.model.DemoModel;
import com.hanthink.dpm.model.DpmItemModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * 
 * <pre>
 * Description:
 * Company: HanThink
 * Date: 2018年9月9日 下午5:23:12
 * </pre>
 * @author luoxq
 */
public interface DpmItemDao extends Dao<String, DpmItemModel>{

	public DpmItemModel getByCode(String itemCode);

	public PageList<DpmItemModel> queryDpmItemForPage(DpmItemModel model, DefaultPage p);

}

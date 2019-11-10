package com.hanthink.pub.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hanthink.pub.dao.PubModelBomDao;
import com.hanthink.pub.model.PubModelBomModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;


/**
 * 
 * <pre> 
 * 描述：BOM基础数据查询 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-29 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Repository
public class PubModelBomDaoImpl extends MyBatisDaoImpl<String, PubModelBomModel> implements PubModelBomDao{

    @Override
    public String getNamespace() {
        return PubModelBomModel.class.getName();
    }

    /**
     * 执行分页查询
     */
	@Override
	public List<PubModelBomModel> queryPubModelBomForPage(PubModelBomModel model, DefaultPage p) {
		
		return this.getByKey("queryPubModelBomForPage", model, p);
	}

	/**
	 * 导出查询
	 */
	@Override
	public List<PubModelBomModel> queryPubModelBomByKey(PubModelBomModel model) {
		
		return this.getByKey("queryPubModelBomForPage", model);
	}
	
}


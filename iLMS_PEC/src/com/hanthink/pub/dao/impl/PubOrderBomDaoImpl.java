package com.hanthink.pub.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.pub.dao.PubOrderBomDao;
import com.hanthink.pub.model.PubOrderBomModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * <pre> 
 * 描述：单车BOM信息查询 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-29 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Repository
public class PubOrderBomDaoImpl extends MyBatisDaoImpl<String, PubOrderBomModel> implements PubOrderBomDao{

    @Override
    public String getNamespace() {
        return PubOrderBomModel.class.getName();
    }

    /**
     * 执行分页查询
     */
	@Override
	public List<PubOrderBomModel> queryPubOrderBomForPage(PubOrderBomModel model, DefaultPage p) {
		
		return this.getByKey("queryPubOrderBomForPage", model, p);
	}

	/**
	 * 导出查询
	 */
	@Override
	public List<PubOrderBomModel> queryPubOrderBomByKey(PubOrderBomModel model) {
		return this.getByKey("queryPubOrderBomForPage", model);
	}

	@Override
	public PageList<PubOrderBomModel> queryNotSummaryOrderBom(
			PubOrderBomModel model, DefaultPage p) {
		return (PageList<PubOrderBomModel>)this.getByKey("queryNotSummaryOrderBom", model, p);
	}

	@Override
	public PageList<PubOrderBomModel> querySummaryOrderBom(PubOrderBomModel model,
			DefaultPage p) {
		return (PageList<PubOrderBomModel>)this.getByKey("querySummaryOrderBom", model, p);
	}

	@Override
	public List<PubOrderBomModel> queryNotSummaryOrderBom(
			PubOrderBomModel model) {
		return (List<PubOrderBomModel>)this.getByKey("queryNotSummaryOrderBom", model);
	}

	@Override
	public List<PubOrderBomModel> querySummaryOrderBom(
			PubOrderBomModel model) {
		return (List<PubOrderBomModel>)this.getByKey("querySummaryOrderBom", model);
	}
	
}


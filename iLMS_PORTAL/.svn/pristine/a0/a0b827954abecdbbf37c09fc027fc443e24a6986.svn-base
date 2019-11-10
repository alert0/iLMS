package com.hanthink.pub.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.pub.dao.PubSupplierDao;
import com.hanthink.pub.model.PubSupplierModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;


/**
 * 
 * <pre> 
 * 描述：供应商主数据查询 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-29 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Repository
public class PubSupplierDaoImpl extends MyBatisDaoImpl<String, PubSupplierModel> implements PubSupplierDao{

    @Override
    public String getNamespace() {
        return PubSupplierModel.class.getName();
    }

    /**
     * 执行分页查询
     */
	@Override
	public List<PubSupplierModel> queryPubSupplierForPage(PubSupplierModel model, DefaultPage p) {
		
		return this.getByKey("queryPubSupplierForPage", model, p);
	}

	/**
	 * 导出数据查询方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月30日 下午4:19:09
	 */
	@Override
	public List<PubSupplierModel> queryPubSupplierByKey(PubSupplierModel model) {
		return this.getByKey("queryPubSupplierForPage", model);
	}

	/**
	 * 通过供应商代码查询供应商信息
	 * @param model
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月28日 上午10:00:21
	 */
	@Override
	public PubSupplierModel qeurySupplierBySupplierNo(PubSupplierModel model) {
		List<PubSupplierModel> list = this.getByKey("queryPubSupplierBySupplierNo", model);
		if(null != list && 0 < list.size()){
			return list.get(0);
		}
		return null;
	}
	
}


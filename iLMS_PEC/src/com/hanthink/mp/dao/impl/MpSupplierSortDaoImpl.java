package com.hanthink.mp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.base.model.DictVO;
import com.hanthink.mp.dao.MpSupplierSortDao;
import com.hanthink.mp.model.MpSupplierSortModel;

import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * <pre> 
 * 描述：供应商分组 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Repository
public class MpSupplierSortDaoImpl extends MyBatisDaoImpl<String, MpSupplierSortModel> implements MpSupplierSortDao{

    @Override
    public String getNamespace() {
        return MpSupplierSortModel.class.getName();
    }
	
    /**
   	 * 查询功能重写
   	 * @param importList
   	 * @author linzhuo	
   	 * @DATE	2018年9月10日 上午11:11:33
   	 */
	@Override
	public List<MpSupplierSortModel> queryMpSupplierSortForPage(MpSupplierSortModel model, DefaultPage p) {
		 return this.getByKey("queryMpSupplierSortForPage", model, p);
	}

	
	/**
	 * 导出数据查询方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public List<MpSupplierSortModel> queryMpSupplierSortByKey(MpSupplierSortModel model) {
		return this.getByKey("queryMpSupplierSortForPage", model);
	}

	/**
	 * 获取计算队列
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public List getUnloadPort() {
		Map<String,Object> map=new HashMap();
		return this.getList("getUnloadPort", map);
	}
	
}


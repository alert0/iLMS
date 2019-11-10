package com.hanthink.pub.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.pub.dao.PubPrPrinterDao;
import com.hanthink.pub.model.PubPrPrinterModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;


/**
 * 
 * <pre> 
 * 描述：打印机配置 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Repository
public class PubPrPrinterDaoImpl extends MyBatisDaoImpl<String, PubPrPrinterModel> implements PubPrPrinterDao{

    @Override
    public String getNamespace() {
        return PubPrPrinterModel.class.getName();
    }
	
    /**
   	 * 查询功能重写
   	 * @param importList
   	 * @author linzhuo	
   	 * @DATE	2018年9月10日 上午11:11:33
   	 */
	@Override
	public List<PubPrPrinterModel> queryPubPrPrinterForPage(PubPrPrinterModel model, DefaultPage p) {
		 return this.getByKey("queryPubPrPrinterForPage", model, p);
	}
    
    /**
     * 批量删除数据
     */
	@Override
	public void deleteByIds(String[] aryIds) throws Exception{
		this.deleteByKey("deleteByIds", aryIds);
	}
}


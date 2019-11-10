package com.hanthink.pub.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.pub.dao.PubSysParamDao;
import com.hanthink.pub.model.PubSysParamModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;


/**
 * 
 * <pre> 
 * 描述：系统参数维护 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Repository
public class PubSysParamDaoImpl extends MyBatisDaoImpl<String, PubSysParamModel> implements PubSysParamDao{

    @Override
    public String getNamespace() {
        return PubSysParamModel.class.getName();
    }
	
    /**
   	 * 查询功能重写
   	 * @param importList
   	 * @author linzhuo	
   	 * @DATE	2018年9月10日 上午11:11:33
   	 */
	@Override
	public List<PubSysParamModel> queryPubSysParamForPage(PubSysParamModel model, DefaultPage p) {
		 return this.getByKey("queryPubSysParamForPage", model, p);
	}
    
    /**
     * 批量删除数据
     */
	@Override
	public void deleteByIds(String[] aryIds) throws Exception{
		this.deleteByKey("deleteByIds", aryIds);
	}

	/**
	 * 主键冲突
	 */
	@Override
	public Integer selectPrimaryKey(PubSysParamModel pubSysParamModel) {
		return this.sqlSessionTemplate.selectOne(getNamespace() + ".selectPrimaryKey", pubSysParamModel);
	}

	/**
	 * 验证SQL是否正确
	 */
	@Override
	public List<LinkedHashMap<String, Object>> sheckParamVal(String value) {
		return (List<LinkedHashMap<String, Object>>) this.getList("sheckParamVal", value);
	}

	/**
	 * 验证SQL是否有返回值
	 */
	@Override
	public Integer sheckParamValReturn(String value) {
		return this.sqlSessionTemplate.selectOne(getNamespace() + ".sheckParamValReturn", value);
	}
	
	/**
	 * 用户修改
	 */
	@Override
	public void userUpdate(PubSysParamModel pubSysParamModel) {
		this.updateByKey("userUpdate", pubSysParamModel);
	}

	/**
	 * 根据参数值和参数代码获取校验表达式和校验依据
	 */
	@Override
	public PubSysParamModel getCheckBy(PubSysParamModel model) {
		return (PubSysParamModel) this.getByKey("getCheckBy", model);
	}

}


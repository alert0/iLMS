package com.hanthink.jit.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hanthink.jit.dao.JitVehScrapDao;
import com.hanthink.jit.model.JitVehScrapModel;
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
public class JitVehScrapDaoImpl extends MyBatisDaoImpl<String, JitVehScrapModel> implements JitVehScrapDao{

    @Override
    public String getNamespace() {
        return JitVehScrapModel.class.getName();
    }

    /**
     * 执行分页查询
     */
	@Override
	public List<JitVehScrapModel> queryJitVehScrapForPage(JitVehScrapModel model, DefaultPage p) {
		
		return this.getByKey("queryJitVehScrapForPage", model, p);
	}

	/**
	 * 导出数据查询方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月30日 下午4:19:09
	 */
	@Override
	public List<JitVehScrapModel> queryJitVehScrapByKey(JitVehScrapModel model) {
		return this.getByKey("queryJitVehScrapForPage", model);
	}

	/**
	 * @Description: 手工补看板 
	 * @param: @param jitVehScrapModel    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年10月28日
	 */
	@Override
	public void updateAdjustKb(JitVehScrapModel jitVehScrapModel) {
		this.updateByKey("updateAdjustKb", jitVehScrapModel);
	}
	
}


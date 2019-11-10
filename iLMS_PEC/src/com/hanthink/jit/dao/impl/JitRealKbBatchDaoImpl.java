package com.hanthink.jit.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hanthink.jit.dao.JitRealKbBatchDao;
import com.hanthink.jit.model.JitRealKbBatchModel;
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
public class JitRealKbBatchDaoImpl extends MyBatisDaoImpl<String, JitRealKbBatchModel> implements JitRealKbBatchDao{

    @Override
    public String getNamespace() {
        return JitRealKbBatchModel.class.getName();
    }

    /**
     * 执行分页查询
     */
	@Override
	public List<JitRealKbBatchModel> queryJitRealKbBatchForPage(JitRealKbBatchModel model, DefaultPage p) {
		
		return this.getByKey("queryJitRealKbBatchForPage", model, p);
	}

	/**
	 * 导出数据查询方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月30日 下午4:19:09
	 */
	@Override
	public List<JitRealKbBatchModel> queryJitRealKbBatchByKey(JitRealKbBatchModel model) {
		return this.getByKey("queryJitRealKbBatchForPage", model);
	}
	
}


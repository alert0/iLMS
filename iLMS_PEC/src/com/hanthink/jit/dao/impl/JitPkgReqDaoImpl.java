package com.hanthink.jit.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hanthink.jit.dao.JitPkgReqDao;
import com.hanthink.jit.model.JitPkgReqModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;


/**
 * 
 * <pre> 
 * 描述：拉动包装明细查询 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-29 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Repository
public class JitPkgReqDaoImpl extends MyBatisDaoImpl<String, JitPkgReqModel> implements JitPkgReqDao{

    @Override
    public String getNamespace() {
        return JitPkgReqModel.class.getName();
    }

    /**
     * 执行分页查询
     */
	@Override
	public List<JitPkgReqModel> queryJitPkgReqForPage(JitPkgReqModel model, DefaultPage p) {
		
		return this.getByKey("queryJitPkgReqForPage", model, p);
	}

	/**
	 * 导出数据查询方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月30日 下午4:19:09
	 */
	@Override
	public List<JitPkgReqModel> queryJitPkgReqByKey(JitPkgReqModel model) {
		return this.getByKey("queryJitPkgReqForPage", model);
	}
	
}


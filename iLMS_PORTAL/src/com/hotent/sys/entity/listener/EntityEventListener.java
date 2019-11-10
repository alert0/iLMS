package com.hotent.sys.entity.listener;

import org.springframework.context.ApplicationListener;

import com.hotent.base.core.model.AbstractModel;
import com.hotent.base.db.event.EntityEvent;
import com.hotent.sys.util.ContextUtil;

/**
 * 处理实体新增修改时添加新增或修改人
 * <pre> 
 * 构建组：x5-sys-core
 * 作者：zhangxw
 * 邮箱:
 * 日期:2016-12-27-下午14:30:29
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class EntityEventListener implements ApplicationListener<EntityEvent>{

	@SuppressWarnings("rawtypes")
	@Override
	public void onApplicationEvent(EntityEvent ev) {
		try {
			AbstractModel model = (AbstractModel) ev.getSource();
			int type = ev.getActionType();
			if(type==0){ //新增
				//设置新增人ID
				model.setCreateBy(ContextUtil.getCurrentUserId());
			}else{ //更新
				//设置修改人ID
				model.setUpdateBy(ContextUtil.getCurrentUserId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

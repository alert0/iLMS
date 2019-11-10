package com.hotent.form.persistence.manager;

import com.hotent.base.manager.api.Manager;
import com.hotent.form.persistence.model.CombinateDialog;

/**
 * 
 * <pre>
 * 描述：combinate_dialog 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:liyj
 * 邮箱:liyijun@jee-soft.cn
 * 日期:2016-03-04 15:48:38
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface CombinateDialogManager extends Manager<String, CombinateDialog> {
	CombinateDialog getByAlias(String alias);
}

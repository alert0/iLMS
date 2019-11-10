package com.hotent.bpmx.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.bpmx.persistence.model.BpmApprovalItem;

/**
 * 
 * <pre> 
 * 描述：常用语管理 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2014-11-03 10:56:20
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface BpmApprovalItemDao extends Dao<String, BpmApprovalItem> {

	List<BpmApprovalItem> getByDefKeyAndUserAndSys(String defKey,
			String curUserId);
	List<BpmApprovalItem> getItemByType(Short typeFlowtype);
}

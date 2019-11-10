package com.hotent.portal.persistence.manager;

import java.util.List;

import com.hotent.base.manager.api.Manager;
import com.hotent.portal.persistence.model.SysIndexColumn;
import com.hotent.portal.persistence.model.SysIndexMyLayout;
/**
 * 
 * <pre> 
 * 描述：SysIndexMyLayout 处理接口
 * 构建组：x5-mini-platform
 * 日期:2017-08-02 11:05:51
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface SysIndexMyLayoutManager extends Manager<String, SysIndexMyLayout>{
	public void save(String html,String designHtml);
	
	public SysIndexMyLayout getLayoutList(String userId,List<SysIndexColumn>  columnList);
	
	public String obtainMyIndexData(String userId);

	public SysIndexMyLayout getByUser(String currentUserId);

	public String obtainIndexMyData(String layoutId);
	
}

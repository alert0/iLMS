package com.hotent.bpmx.persistence.manager.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.persistence.dao.BpmApprovalItemDao;
import com.hotent.bpmx.persistence.manager.BpmApprovalItemManager;
import com.hotent.bpmx.persistence.model.BpmApprovalItem;
import com.hotent.sys.api.type.ISysTypeService;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre> 
 * 描述：常用语管理 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2014-11-03 10:56:20
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("bpmApprovalItemManager")
public class BpmApprovalItemManagerImpl extends AbstractManagerImpl<String, BpmApprovalItem> implements BpmApprovalItemManager{
	@Resource
	BpmApprovalItemDao bpmApprovalItemDao;
	@Resource
	ISysTypeService sysTypeService;
	
	@Override
	protected Dao<String, BpmApprovalItem> getDao() {
		return bpmApprovalItemDao;
	}
	@Override
	public void addTaskApproval(BpmApprovalItem bpmApprovalItem) {
		short type = bpmApprovalItem.getType();
		String curUserId = ContextUtil.getCurrentUserId();
		String[] expressions={StringUtil.join(bpmApprovalItem.getExpression().split("\n"), "")};
		if(type == 1 || type ==4){
			for (String expression : expressions) {
				if (StringUtil.isNotEmpty(expression)) {
					BpmApprovalItem approvalItem = new BpmApprovalItem();
					approvalItem.setId(UniqueIdUtil.getSuid());
					approvalItem.setType(type);
					approvalItem.setExpression(expression);
					approvalItem.setUserId(curUserId);
					bpmApprovalItemDao.create(approvalItem);
				}
				
			}
		}else if(type == 2) {
			if(StringUtil.isEmpty(bpmApprovalItem.getTypeId()))return;
			String[] typeIds = bpmApprovalItem.getTypeId().split(",");
			for (String typeId : typeIds) {
				for (String expression : expressions) {
					if (StringUtil.isNotEmpty(expression)) {
						BpmApprovalItem approvalItem = new BpmApprovalItem();
						approvalItem.setId(UniqueIdUtil.getSuid());
						approvalItem.setType(type);
						approvalItem.setTypeId(typeId);
						approvalItem.setExpression(expression);
						approvalItem.setUserId(curUserId);
						approvalItem.setDefName(sysTypeService.getById(typeId).getName());
						bpmApprovalItemDao.create(approvalItem);
					}
				}
			}
			
		}else if(type == 3) {
			if(StringUtil.isEmpty(bpmApprovalItem.getDefKey()))return;
			String[] defKeys = bpmApprovalItem.getDefKey().split(",");
			String names = bpmApprovalItem.getDefName();
			String[] defNames = names.split(",");
			for (int i=0;i<defKeys.length;i++) {
				for (String expression : expressions) {
					if (StringUtil.isNotEmpty(expression)) {
						BpmApprovalItem approvalItem = new BpmApprovalItem();
						approvalItem.setId(UniqueIdUtil.getSuid());
						approvalItem.setType(type);
						approvalItem.setDefKey(defKeys[i]);
						approvalItem.setExpression(expression);
						approvalItem.setDefName(defNames[i]);
						approvalItem.setUserId(curUserId);
						bpmApprovalItemDao.create(approvalItem);
					}
				}
			}
		}
		if(StringUtil.isNotEmpty(bpmApprovalItem.getId())){
			bpmApprovalItemDao.remove(bpmApprovalItem.getId());
		}
			
	}
	
	
	/**
	 * 取流程常用语。
	 * @param defKey	流程定义Key。
	 * @param typeIdPath	分类的父路径。
	 * @return
	 */
	@Override
	public List<String> getApprovalByDefKeyAndTypeId(String defKey,String typeId){
		List<String> taskAppItemsList = new ArrayList<String>();
		String curUserId = ContextUtil.getCurrentUserId();
		//先获取本人的，系统全局的，和指定流程下的常用语
		List<BpmApprovalItem> taskAppItem1=bpmApprovalItemDao.getByDefKeyAndUserAndSys(defKey,curUserId);
		//获取分类为2的所有的常用语,指定流程分类的
		List<BpmApprovalItem> taskAppItem2=bpmApprovalItemDao.getItemByType(BpmApprovalItem.TYPE_FLOWTYPE);
		
		if (BeanUtils.isNotEmpty(taskAppItem1)) {
			for(BpmApprovalItem taskAppItem:taskAppItem1){
				taskAppItemsList.add(taskAppItem.getExpression());
			}
		}
		if (BeanUtils.isNotEmpty(taskAppItem2)&&StringUtil.isNotEmpty(typeId)) {
			//获取分类的父路径
			String typeIdPath = sysTypeService.getById(typeId).getPath();
			String[] typeIds=typeIdPath.split("\\.");
			for (int i=1; i< typeIds.length ;i++) {
				for (BpmApprovalItem taskAppItem:taskAppItem2) {
					if ((taskAppItem.getTypeId().toString()).equals(typeIds[i])) {
						taskAppItemsList.add(taskAppItem.getExpression());
					}
				}
			}
		} 
		//去除重复的元素
		this.removeDuplicate(taskAppItemsList);
		return taskAppItemsList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void removeDuplicate (List<?> list){
		  HashSet h = new HashSet(list);
	      list.clear();
	      list.addAll(h);
	}
	
}

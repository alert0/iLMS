package com.hotent.portal.service;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.mail.model.Mail;
import com.hotent.base.core.mail.model.MailSetting;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.util.time.DateFormatUtil;
import com.hotent.base.db.datasource.DbContextHolder;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
//import com.hotent.bpmx.api.model.process.task.BpmTask;
//import com.hotent.bpmx.api.service.BpmInstService;
//import com.hotent.bpmx.api.service.BpmTaskService;
//import com.hotent.bpmx.core.util.BpmUtil;
//import com.hotent.bpmx.persistence.manager.BpmDefUserManager;
//import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
//import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
//import com.hotent.bpmx.persistence.manager.BpmTaskManager;
//import com.hotent.bpmx.persistence.manager.BpmTaskTurnManager;
//import com.hotent.bpmx.persistence.manager.CopyToManager;
//import com.hotent.bpmx.persistence.model.BpmDefAuthorizeType.BPMDEFAUTHORIZE_RIGHT_TYPE;
//import com.hotent.bpmx.persistence.model.BpmDefUser;
//import com.hotent.bpmx.persistence.model.CopyTo;
//import com.hotent.bpmx.persistence.model.DefaultBpmDefinition;
//import com.hotent.bpmx.persistence.model.DefaultBpmProcessInstance;
//import com.hotent.bpmx.persistence.model.DefaultBpmTask;
//import com.hotent.bpmx.persistence.model.DefaultBpmTaskTurn;
//import com.hotent.form.persistence.manager.CustomQueryManager;
//import com.hotent.form.persistence.model.CustomQuery;
//import com.hotent.mail.persistence.manager.MailManager;
//import com.hotent.mail.persistence.manager.MailSettingManager;
import com.hotent.org.persistence.model.Org;
import com.hotent.org.persistence.model.OrgRel;
import com.hotent.org.persistence.model.SysUser;
import com.hotent.portal.index.IndexTab;
import com.hotent.portal.index.IndexTabList;
import com.hotent.portal.index.Infobox;
import com.hotent.portal.persistence.manager.SysIndexToolsManager;
import com.hotent.portal.persistence.manager.SysLayoutToolsManager;
import com.hotent.portal.persistence.model.SysIndexTools;
import com.hotent.portal.persistence.model.SysLayoutTools;
import com.hotent.portal.util.MessageTypeUtil;
import com.hotent.portal.util.PortalUtil;
import com.hotent.sys.persistence.manager.SysMessageManager;
import com.hotent.sys.persistence.model.SysMessage;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysDataSourceUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class IndexService {

//	@Resource
//	BpmTaskManager bpmTaskManager;
//	@Resource
//	BpmProcessInstanceManager bpmProcessInstanceManager;
//	@Resource
//	BpmDefinitionManager bpmDefinitionManager;
//	@Resource
//	BpmTaskTurnManager bpmTaskTurnManager;
//	@Resource
//	CopyToManager copyToManager;
	@Resource
	SysMessageManager sysMessageManager;
//	@Resource
//	BpmTaskService bpmTaskService;
	@Resource
	SysLayoutToolsManager sysLayoutToolsManager;
//	@Resource
//	BpmDefUserManager bpmDefUserManager;
	@Resource
	SysIndexToolsManager sysIndexToolsManager;
//	@Resource
//	CustomQueryManager customQueryManager;
//	@Resource
//	MailManager mailManager;
//	@Resource
//	BpmInstService bpmInstService;
//	@Resource
//	MailSettingManager mailSettingManager;
	
	// 默认的首页工具id
	protected static final List<String> toolsId = Arrays.asList("1","2","3","4","5","6","7","8","9","10","11","12","13");
	public String test(){
		return "测试";
	}
	
	public IndexTabList processCenter(Integer curPage, Integer pageSize,
			String curTab) {
		String userId = ContextUtil.getCurrentUserId();
//		PageList<DefaultBpmTask> list1 = (PageList<DefaultBpmTask>) bpmTaskManager.getByUserId(userId, new DefaultQueryFilter());
		//bpmTaskManager.convertInfo(list1);

//		IndexTab tab1 = new IndexTab();
//		tab1.setTitle("我的待办");
//		tab1.setKey("pendingMatter");
//		tab1.setBadge(list1.getPageResult().getTotalCount() + "");
//		tab1.setActive(true);
//		tab1.setList(list1);

//		PageList<?> list2 = completedMattersPage(curPage, pageSize);
//		IndexTab tab2 = new IndexTab();
//		tab2.setTitle("办结事宜");
//		tab2.setKey("completedMatters");
//		tab2.setBadge(list2.getPageResult().getTotalCount() + "");
//		tab2.setList(list2);

		List<IndexTab> tabList = new ArrayList<IndexTab>();
//		tabList.add(tab1);
//		tabList.add(tab2);

		IndexTabList indexTabList = new IndexTabList();
	
		indexTabList.setCurTab(curTab);
		setIndexTabList(indexTabList, curTab, tabList);
		
		indexTabList.setIndexTabList(tabList);
		return indexTabList;
	}

	private void setIndexTabList(IndexTabList indexTabList, String curTab,
			List<IndexTab> tabList) {
		for (IndexTab indexTab : tabList) {
			if(curTab.equalsIgnoreCase(	indexTab.getKey())){
				if(BeanUtils.isNotEmpty(indexTab.getList()))
					indexTabList.setPageBean(	indexTab.getList().getPageResult());
				indexTab.setActive(true);
			}else{
				indexTab.setActive(false);
			}
		}

	}

//	public PageList<DefaultBpmTask> pendingMatterPage(Integer curPage,
//			Integer pageSize) {
//		String userId = ContextUtil.getCurrentUserId();
//		PageList<DefaultBpmTask> list = (PageList<DefaultBpmTask>) bpmTaskManager.getByUserId(userId, new DefaultQueryFilter());
//
//		/*try {
//			pageBean.(BeanUtils.isNotEmpty(curPage) ? curPage : 1);
//			pageBean.setPagesize(BeanUtils.isNotEmpty(pageSize) ? pageSize : 10);
//			list = (PageList<ProcessTask>) taskDao.getTasks(
//					ContextUtil.getCurrentUserId(), null, null, null, null,
//					"desc", pageBean);
//			// 为待办添加上颜色用Description 字段
//			Map<Integer, WarningSetting> waringSetMap = reminderService
//					.getWaringSetMap();
//			for (ProcessTask task : list) {
//				int priority = task.getPriority();
//				if (waringSetMap.containsKey(priority))
//					task.setDescription(waringSetMap.get(priority).getColor());
//				else
//					task.setDescription("");
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}*/
//		return list;
//	}

//	public List<DefaultBpmTask> pendingMatters(Integer curPage, Integer pageSize) {
//
//		String userId = ContextUtil.getCurrentUserId();
//		PageList<DefaultBpmTask> list = (PageList<DefaultBpmTask>) bpmTaskManager.getByUserId(userId, new DefaultQueryFilter());
//
//		/*try {
//			pb.setCurrentPage(BeanUtils.isNotEmpty(curPage) ? curPage : 1);
//			pb.setPagesize(BeanUtils.isNotEmpty(pageSize) ? pageSize : 10);
//			list = taskDao.getTasks(ContextUtil.getCurrentUserId(), null, null,
//					null, null, "desc", pb);
//			// 为待办添加上颜色用Description 字段
//			Map<Integer, WarningSetting> waringSetMap = reminderService
//					.getWaringSetMap();
//			for (ProcessTask task : list) {
//				int priority = task.getPriority();
//				if (waringSetMap.containsKey(priority))
//					task.setDescription(waringSetMap.get(priority).getColor());
//				else
//					task.setDescription("");
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}*/
//		return list;
//	}

	/**
	 * 待办任务
	 * 
	 * @return
	 */
//	public List<DefaultBpmTask> pendingMatters() {
//		return pendingMatters(1, 10);
//	}
	
	/**
	 * 手机端获取我的代办数据
	 * @return
	 */
//	public List<BpmTask> mobileMyTask(){
//		DefaultQueryFilter queryFilter = new DefaultQueryFilter();
//		queryFilter.addFilter("support_mobile_", 1, QueryOP.EQUAL); 
//		PageList<BpmTask> list= (PageList<BpmTask>)bpmTaskService.getTasksByUserId(ContextUtil.getCurrentUserId(), queryFilter);
//		return list;
//	}
	
	/**
	 * 手机端我的已办事宜
	 * @return
	 */
//	public List<Map<String,Object>> mobileMyAlreadyMattersList(){
//		DefaultQueryFilter queryFilter = new DefaultQueryFilter();
//		queryFilter.addFilter("support_mobile_", 1,QueryOP.EQUAL);
//		List<Map<String,Object>> list =(List<Map<String,Object>>)bpmProcessInstanceManager.getHandledByUserId(ContextUtil.getCurrentUserId(), queryFilter);
//		return new PageList(list);
//	}
	
	/**
	 * 我的请求
	 * @return
	 */
//	public PageList<DefaultBpmProcessInstance> myRequest(){
//		String userId = ContextUtil.getCurrentUserId();
//		PageList<DefaultBpmProcessInstance> list = (PageList<DefaultBpmProcessInstance>) bpmProcessInstanceManager.getMyRequestByUserId(userId, new DefaultQueryFilter());
//		return list;
//	}
	

	/**
	 * 个人信息
	 * 
	 * @return
	 */
	public SysUser userInfo() {
		return (SysUser) ContextUtil.getCurrentUser();
	}

	/**
	 * 内部消息
	 * 
	 * @return
	 */
	public List<?> getMessage() {
		QueryFilter queryFilter = new DefaultQueryFilter();
		queryFilter.addFilter("receiverId", ContextUtil.getCurrentUserId(), QueryOP.EQUAL);
		PageList<SysMessage> sysMessageList = (PageList<SysMessage>) sysMessageManager.getMsgByUserId(queryFilter);
		PageList<SysMessage> newSysMessageList = new PageList<SysMessage>();
		//消息类型值得转换
		for (int i = 0; i < sysMessageList.size(); i++) {
			SysMessage sysMessage = sysMessageList.get(i);
			String messageType= MessageTypeUtil.getValue(sysMessage.getMessageType());
			sysMessage.setMessageType(messageType);
			newSysMessageList.add(sysMessage);
		}
		newSysMessageList.setPageResult(sysMessageList.getPageResult());

		return newSysMessageList;
	}

//
//	/**
//	 * 内部消息
//	 * 
//	 * @return
//	 */
//	public List<?> getMessage() {
//		List<?> list = new ArrayList<MessageSend>();
//
//		PageBean pb = new PageBean();
//		pb.setCurrentPage(1);
//		pb.setPagesize(10);
//		try {
//			list = messageSendDao.getNotReadMsgByUserId(
//					ContextUtil.getCurrentUserId(), pb);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return list;
//	}
//
//	/**
//	 * 我的审批的流程
//	 * 
//	 * @return
//	 */
//	public List<ProcessRun> myAttend() {
//		List<ProcessRun> list = new ArrayList<ProcessRun>();
//		PageBean pb = new PageBean();
//		pb.setCurrentPage(1);
//		pb.setPagesize(10);
//		// 去掉进行分页的总记录数的查询
//		pb.setShowTotal(false);
//		try {
//			list = processRunDao.getMyAttend(ContextUtil.getCurrentUserId(),
//					null, pb);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return list;
//	}

	/**
	 * 我发起的流程
	 * 
	 * @return
	 */
//	public List<DefaultBpmProcessInstance> myStart() {
//		return myRequest();
//	}

//	/**
//	 * 获取用户未读邮件。 以时间降序排序，最多取10条。
//	 * 
//	 * @return 用户未读邮件对象列表
//	 */
//	public List<OutMail> myNewMail() {
//		List<OutMail> list = new ArrayList<OutMail>();
//		PageBean pb = new PageBean();
//		pb.setCurrentPage(1);
//		pb.setPagesize(10);
//		try {
//			list = outMailDao.getMailByUserId(ContextUtil.getCurrentUserId(),
//					pb);
//		} catch (Exception e) {
//			e.getStackTrace();
//		}
//
//		return list;
//
//	}

	/**
	 * 获取用户可以访问的流程定义
	 * 
	 * @return
	 */
//	public PageList<DefaultBpmDefinition> myProcess() {
//		QueryFilter queryFilter = new DefaultQueryFilter();
//		queryFilter.addFilter("is_main_", "Y", QueryOP.EQUAL);
//		queryFilter.addFilter("status_", "deploy", QueryOP.EQUAL);
//		queryFilter.addParamsFilter("bpmDefAuthorizeRightType", BPMDEFAUTHORIZE_RIGHT_TYPE.START);
//
//		// 查询列表
//		PageList<DefaultBpmDefinition> list = (PageList<DefaultBpmDefinition>) bpmDefinitionManager.queryList(queryFilter);
//		return list;
//	}

	/**
	 * 我的办结
	 * 
	 * @return
	 */
//	public List<DefaultBpmProcessInstance> myCompleted() {
//		String userId = ContextUtil.getCurrentUserId();
//		PageList<DefaultBpmProcessInstance> list = (PageList<DefaultBpmProcessInstance>) bpmProcessInstanceManager.getMyCompletedByUserId(userId, new DefaultQueryFilter());
//		return list;
//	}

	/**
	 * 已办事宜
	 * 
	 * @return
	 */
//	public PageList<Map<String,Object>> alreadyMatters() {
//		String userId = ContextUtil.getCurrentUserId();
//		PageList<Map<String,Object>> list = (PageList<Map<String,Object>>)  bpmProcessInstanceManager.getHandledByUserId(userId, new DefaultQueryFilter());
//		
//		return list;
//	}

//	public PageList<DefaultBpmProcessInstance> completedMattersPage(Integer curPage,
//			Integer pageSize) {
//		String userId = ContextUtil.getCurrentUserId();
//		PageList<DefaultBpmProcessInstance> list = (PageList<DefaultBpmProcessInstance>) bpmProcessInstanceManager.getCompletedByUserId(userId, new DefaultQueryFilter());
//		return list;
//	}

	/**
	 * 办结事宜
	 * 
	 * @return
	 */
//	public List<DefaultBpmProcessInstance> completedMatters() {
//		String userId = ContextUtil.getCurrentUserId();
//		PageList<DefaultBpmProcessInstance> list = (PageList<DefaultBpmProcessInstance>) bpmProcessInstanceManager.getCompletedByUserId(userId, new DefaultQueryFilter());
//		return list;
//	}

//	/**
//	 * 公告信息
//	 * 
//	 * @return
//	 */
//	public List<SysBulletin> getBulletin(String alias) {
//		List<SysBulletin> list = sysBulletinService.getAllByAlias(alias);
//		return list;
//	}

	/**
	 * 个人信息
	 * 
	 * @return
	 */
	public Map<String, Object> getCurUser() {
		Map<String, Object> root = new HashMap<String, Object>();
		SysUser user = (SysUser) ContextUtil.getCurrentUser();
		root.put("user", user);
		Org org = (Org) ContextUtil.getCurrentGroup();
		root.put("org", org);
		OrgRel pos = (OrgRel) ContextUtil.getCurrentGroup();
		root.put("pos", pos);
		return root;
	}

/*	*//**
	 * 信息盒子
	 * 
	 * @return
	 *//*
	public List<Infobox> getInfobox() {
		List<Infobox> list = new ArrayList<Infobox>();
		try {
			Infobox myTaksBox = this.getMyTaksBox();	//	我的待办
			Infobox myMessBox = this.getMyMessBox();	//	内部消息
			Infobox myProCopytoBox = this.getMyProCopytoBox();	//	抄送转发
			Infobox myAlreadyCompletedBox = getMyAlreadyCompletedBox();	//	已办、办结事宜
			Infobox myCompletedBox = getMyCompletedBox();	//	我的办结
			
			Infobox myAccordingMattersBox = this.getMyAccordingMattersBox();	//	转办代理事宜
			Infobox myRequestBox = this.getMyRequestBox();	//	 我的请求
			Infobox myDraftBox = this.getMyDraftBox();	//	 我的草稿
//			Infobox myPlanBox = this.getMyPlanBox();	//	我的日程
//			Infobox onLineUsersBox = getOnLineUsersBox(); //	在线人数
			
			list.add(myTaksBox);	//	我的待办
			list.add(myMessBox);	//	内部消息
			list.add(myProCopytoBox);	//	抄送转发
			list.add(myAlreadyCompletedBox);	//	已办、办结事宜
			list.add(myCompletedBox);	//	我的办结
			
			list.add(myAccordingMattersBox);	//	转办代理事宜
			list.add(myRequestBox);	//	我的请求
			list.add(myDraftBox);	//	我的草稿
//			list.add(myPlanBox);	//	我的日程
//			list.add(onLineUsersBox);	//	在线人数
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}*/
	
//	/**
//	 * 在线人数
//	 * @return
//	 */
//	private Infobox getOnLineUsersBox() {
//		Infobox infobox = new Infobox();
//		infobox.setType(2);
//		infobox.setColor(Infobox.COLOR_LIGHT_BROWN);
//		infobox.setDataText(UserSessionListener.getOnLineUsers().size() + "");
//		infobox.setDataContent("在线人数");
//		infobox.setData("196,128,202,177,154,94,100,170,224");
//		return infobox;
//	}

//	/**
//	 * 我的日程
//	 * @return
//	 */
//	private Infobox getMyPlanBox() {
//		Infobox infobox = new Infobox();
//		infobox.setIcon("fa-check-square-o");
//		infobox.setColor(Infobox.COLOR_BROWN);
//		infobox.setDataContent("我的日程");
//		infobox.setUrl("/platform/system/sysPlan/myList.ht");
//		
//		QueryFilter filter = new QueryFilter(new JSONObject());
//		filter.addFilter("rate", 0);
//		filter.addFilter("userId", ContextUtil.getCurrentUserId());
//		List plans = planService.getMyList(filter);
//		infobox.setDataText(plans.size() + "");
//		return infobox;
//	}

	/**
	 * 我的草稿
	 * @return
	 */
//	private Infobox getMyDraftBox() {
//		String userId = ContextUtil.getCurrentUserId();
//		PageList<DefaultBpmProcessInstance> list = (PageList<DefaultBpmProcessInstance>) bpmProcessInstanceManager.getDraftsByUserId(userId, new DefaultQueryFilter());
//		Infobox infobox = new Infobox();
//		infobox.setIcon("fa-pencil-square-o");
//		infobox.setColor(Infobox.COLOR_WOOD);
//		infobox.setDataText(list.size() + "");
//		infobox.setDataContent("我的草稿");
//		infobox.setUrl("/office/initiatedProcess/myDraft");
//		return infobox;
//	}
	
	/**
	 * 我的待办
	 * @return
	 */
//	private Infobox getMyTaksBox() {
//		String userId = ContextUtil.getCurrentUserId();
//		PageList<DefaultBpmTask> list = (PageList<DefaultBpmTask>) bpmTaskManager.getByUserId(userId, new DefaultQueryFilter());
//		Infobox infobox = new Infobox();
//		infobox.setIcon("fa-pencil-square-o");
//		infobox.setColor(Infobox.COLOR_WOOD);
//		infobox.setDataText(list.size() + "");
//		infobox.setDataContent("我的待办");
//		infobox.setUrl("/office/receivedProcess/pending");
//		return infobox;
//	}


	/**
	 * 我的办结
	 * @return
	 */
//	private Infobox getMyCompletedBox() {
//		String userId = ContextUtil.getCurrentUserId();
//		PageList<DefaultBpmProcessInstance> list = (PageList<DefaultBpmProcessInstance>) bpmProcessInstanceManager.getMyCompletedByUserId(userId, new DefaultQueryFilter());
//		Infobox infobox = new Infobox();
//		infobox.setIcon("fa-check-square-o");
//		infobox.setColor(Infobox.COLOR_BROWN);
//		infobox.setDataText(list.size() + "");
//		infobox.setDataContent("我的办结");
//		infobox.setUrl("/office/initiatedProcess/myCompleted");
//		return infobox;
//	}

	/**
	 * 我的请求
	 * @return
	 */
//	private Infobox getMyRequestBox() {
//		String userId = ContextUtil.getCurrentUserId();
//		PageList<DefaultBpmProcessInstance> list = (PageList<DefaultBpmProcessInstance>) bpmProcessInstanceManager.getMyRequestByUserId(userId, new DefaultQueryFilter());
//		Infobox infobox = new Infobox();
//		infobox.setIcon("fa-hand-o-up");
//		infobox.setColor(Infobox.COLOR_BLUE2);
//		infobox.setDataText(list.size() + "");
//		infobox.setDataContent("我的请求");
//		infobox.setUrl("/office/initiatedProcess/myRequest");
//		return infobox;
//	}

	/**
	 * 转办代理事宜
	 * @return
	 */
//	private Infobox getMyAccordingMattersBox() {
//		String userId = ContextUtil.getCurrentUserId();
//		PageList<DefaultBpmTaskTurn> list = (PageList<DefaultBpmTaskTurn>) bpmTaskTurnManager.getMyDelegate(userId, new DefaultQueryFilter());
//		Infobox infobox = new Infobox();
//		infobox.setIcon("fa-share");
//		infobox.setColor(Infobox.COLOR_PINK);
//		infobox.setDataText(list.size() + "");
//		infobox.setDataContent("转办代理事宜");
//		infobox.setUrl("/office/initiatedProcess/delegateMatters");
//		return infobox;
//	}

	/**
	 * 办结事宜
	 * @return
	 */
//	private Infobox getMyCompletedMattersBox() {
//		String userId = ContextUtil.getCurrentUserId();
//		PageList<DefaultBpmProcessInstance> list = (PageList<DefaultBpmProcessInstance>) bpmProcessInstanceManager.getCompletedByUserId(userId, new DefaultQueryFilter());
//		Infobox infobox = new Infobox();
//		infobox.setIcon("fa-check");
//		infobox.setColor(Infobox.COLOR_GREEN);
//		infobox.setDataText(list.size() + "");
//		infobox.setDataContent("办结事宜");
//		infobox.setUrl("/office/receivedProcess/completed");
//		return infobox;
//	}

	/**
	 * 已办事宜
	 * @return
	 */
//	private Infobox getMyAlreadyBox() {
//		String userId = ContextUtil.getCurrentUserId();
//		PageList<Map<String,Object>> list = (PageList<Map<String,Object>>)  bpmProcessInstanceManager.getHandledByUserId(userId, new DefaultQueryFilter());
//		Infobox infobox = new Infobox();
//		infobox.setIcon("fa-flag");
//		infobox.setColor(Infobox.COLOR_RED);
//		infobox.setDataText(list.size() + "");
//		infobox.setDataContent("已办事宜");
//		infobox.setUrl("/office/receivedProcess/handled");
//		return infobox;
//	}

	/**
	 * 已办、办结事宜
	 * @return
	 */
//	private Infobox getMyAlreadyCompletedBox() {
//		String userId = ContextUtil.getCurrentUserId();
//		PageList<DefaultBpmProcessInstance> list = (PageList<DefaultBpmProcessInstance>) bpmProcessInstanceManager.getMyCompletedByUserId(userId, new DefaultQueryFilter());
//		Infobox infobox = new Infobox();
//		infobox.setIcon("fa-flag");
//		infobox.setColor(Infobox.COLOR_RED);
//		infobox.setDataText(list.size() + "");
//		infobox.setDataContent("已办、办结事宜");
//		infobox.setUrl("/office/initiatedProcess/myCompleted");
//		return infobox;
//	}

	/**
	 *  查看抄送转发
	 * @return
	 */
//	private Infobox getMyProCopytoBox() {
//		String userId = ContextUtil.getCurrentUserId();
//		PageList<CopyTo> list = (PageList<CopyTo>) copyToManager.getMyCopyTo(userId, new DefaultQueryFilter());
//		/*Integer proCount = bpmProCopytoDao.getCountByUser(ContextUtil
//				.getCurrentUserId());
//		Integer noReadProCount = bpmProCopytoDao
//				.getCountNotReadByUser(ContextUtil.getCurrentUserId());*/
//		Integer proCount = list.size();
//		Integer noReadProCount = list.size();
//		Infobox infobox = new Infobox();
//		infobox.setIcon("fa-comments");
//		infobox.setColor(Infobox.COLOR_BLUE3);
//		infobox.setDataText("(" + noReadProCount + "/" + proCount + ")");
//		infobox.setDataContent("抄送转发");
//		infobox.setUrl("/office/initiatedProcess/myCopyTo");
//		return infobox;
//	}

	/**
	 * 查看内部消息
	 * @return
	 */
	private Infobox getMyMessBox() {
		/*Integer messCount = messageSendDao
				.getCountReceiverByUser(ContextUtil.getCurrentUserId());
		Integer noReadMessCount = messageSendDao
				.getCountNotReadMsg(ContextUtil.getCurrentUserId());*/
		Integer messCount = 0;
		Integer noReadMessCount = 0;
		Infobox infobox = new Infobox();
		infobox.setIcon("fa-comments");
		infobox.setColor(Infobox.COLOR_BLUE2);
		infobox.setDataText("(" + noReadMessCount + "/" + messCount + ")");
		infobox.setDataContent("内部消息");
		infobox.setUrl("/innermsg/messageReceiverList");
		return infobox;
	}

//	/**
//	 * 获取我的待办消息盒子
//	 * @return
//	 */
//	private Infobox getMyTaksBox() {
//		List<TaskAmount> countlist = bpmService.getMyTasksCount(ContextUtil.getCurrentUserId());
//		int count = 0;
//		int notRead = 0;
//		for (TaskAmount taskAmount : countlist) {
//			count += taskAmount.getTotal();
//			notRead += taskAmount.getNotRead();
//		}
//		Infobox infobox = new Infobox();
//		infobox.setIcon("fa-comments");
//		infobox.setColor(Infobox.COLOR_BLUE);
//		infobox.setDataText("(" + notRead + "/" + count + ")");
//		infobox.setDataContent("待办事宜");
//		infobox.setUrl("/platform/bpm/task/pendingMatters.ht");
//		return infobox;
//	}
//	

	/**
	 * 
	 * 我的日历例子 [{ title: '所有事件', start: new Date(y, m, 1), backgroundColor:
	 * Theme.colors.blue }, { title: '长事件', start: new Date(y, m, d-5), end: new
	 * Date(y, m, d-2), backgroundColor: Theme.colors.red }, { id: 999, title:
	 * '重复事件', start: new Date(y, m, d-3, 16, 0), allDay: false,
	 * backgroundColor: Theme.colors.yellow }, { id: 999, title: '重复事件', start:
	 * new Date(y, m, d+4, 16, 0), allDay: false, backgroundColor:
	 * Theme.colors.primary }, { title: '会议', start: new Date(y, m, d, 10, 30),
	 * allDay: false, backgroundColor: Theme.colors.green }, { title: '午餐',
	 * start: new Date(y, m, d, 12, 0), end: new Date(y, m, d, 14, 0), allDay:
	 * false, backgroundColor: Theme.colors.red }, { title: '生日聚会', start: new
	 * Date(y, m, d+1, 19, 0), end: new Date(y, m, d+1, 22, 30), allDay: false,
	 * backgroundColor: Theme.colors.gray }, { title: '链接到百度', start: new
	 * Date(y, m, 28), end: new Date(y, m, 29), url: 'http://www.baidu.com/',
	 * backgroundColor: Theme.colors.green }
	 * 
	 * 
	 * @return
	 */
	public String myCalendar() {

		JSONArray jsonAry = new JSONArray();

		// {title: '所有事件',start: new Date(y, m, 1),backgroundColor:
		// Theme.colors.blue

		for (int i = 0; i < 100; i++) {
			JSONObject json = new JSONObject();
			json.accumulate("title", "所有事件")
					.accumulate("start",
							DateFormatUtil.format(new Date(), "yyyy-MM-dd"))
					.accumulate("backgroundColor", "#70AFC4")
					.accumulate("eventClick",
							"function(calEvent){" + "alert(calEvent.title)}");
			jsonAry.add(json);

		}

		//
		JSONObject json2 = new JSONObject();
		Calendar ca = Calendar.getInstance();

		// {title: '所有事件',start: new Date(y, m, 1),backgroundColor:
		// Theme.colors.blue
		json2.accumulate("title", "长事件").accumulate("start",
				DateFormatUtil.format(ca.getTime(), "yyyy-MM-dd"));
		ca.add(Calendar.DAY_OF_MONTH, 3);
		json2.accumulate("end",
				DateFormatUtil.format(ca.getTime(), "yyyy-MM-dd")).accumulate(
				"backgroundColor", "#D9534F");
		jsonAry.add(json2);

		JSONObject json3 = new JSONObject();
		ca.add(Calendar.DAY_OF_MONTH, 6);

		// {title: '所有事件',start: new Date(y, m, 1),backgroundColor:
		// Theme.colors.blue
		json3.accumulate("title", "连接到公司网站")
				.accumulate("start",
						DateFormatUtil.format(ca.getTime(), "yyyy-MM-dd"))
				.accumulate("url", "http://www.jee-soft.cn/")
				.accumulate("backgroundColor", "#A8BC7B");
		jsonAry.add(json3);
		return jsonAry.toString();
	}

	/**
	 * 柱状图例子
	 * 
	 * @return
	 */
	public String barChart() {
		float data1[] = new float[12];
		float data2[] = new float[12];
		// 随机
		for (int i = 0; i <= 11; i++) {
			DecimalFormat dcmFmt = new DecimalFormat("0.0");
			float f1 = new Random().nextFloat() * 1000;
			float f2 = new Random().nextFloat() * 2000;
			data1[i] = Float.parseFloat(dcmFmt.format(f1));
			data2[i] = Float.parseFloat(dcmFmt.format(f2));
		}
		String d1 = JSONArray.fromObject(data1).toString();
		String d2 = JSONArray.fromObject(data2).toString();
		String data = "{title:{text:'',subtext:'纯属虚构'},tooltip:{trigger:'axis'},legend:{data:['蒸发量','降水量']},toolbox:{sho:true,feature:{mark:{sho:true},dataVie:{sho:true,readOnly:false},magicType:{sho:true,type:['line','bar']},restore:{sho:true},saveAsImage:{sho:true}}},calculable:true,xAxis:[{type:'category',data:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']}],yAxis:[{type:'value'}],series:[{name:'蒸发量',type:'bar',"
				+ "data:"
				+ d1
				+ ",markPoint:{data:[{type:'max',name:'最大值'},{type:'min',name:'最小值'}]},markLine:{data:[{type:'average',name:'平均值'}]}},{name:'降水量',type:'bar',data:"
				+ d2
				+ ",markPoint:{data:[{name:'年最高',value:182.2,xAxis:7,yAxis:183,symbolSize:18},{name:'年最低',value:2.3,xAxis:11,yAxis:3}]},markLine:{data:[{type:'average',name:'平均值'}]}}]}";

		return data;
	}

	/**
	 * 折线图例子
	 * 
	 * @return
	 */
	public String lineChart() {
		int data1[] = new int[7];
		int data2[] = new int[7];
		// 随机
		for (int i = 0; i <= 6; i++) {
			data1[i] = (int) (Math.random() * 10) + 15;
			data2[i] = (int) (Math.random() * 10);
		}
		String d1 = JSONArray.fromObject(data1).toString();
		String d2 = JSONArray.fromObject(data2).toString();
		String data = "{" + "title:{" + "text:\"\"," + "subtext:\"纯属虚构\""
				+ "}," + "tooltip:{" + "trigger:\"axis\"" + "}," + "legend:{"
				+ "data:[\"最高气温\",\"最低气温\"]" + "}," + "calculable:\"true\","
				+ "xAxis:[" + "{" + "type:\"category\","
				+ "boundaryGap:\"false\","
				+ "data:[\"周一\",\"周二\",\"周三\",\"周四\",\"周五\",\"周六\",\"周日\"]"
				+ "}" + "]," + "yAxis:[" + "{" + "type:\"value\","
				+ "axisLabel:{" + "formatter:\"{value} °C\"" + "}" + "}" + "],"
				+ "series:[" + "{" + "name:\"最高气温\"," + "type:\"line\","
				+ "data:"
				+ d1
				+ ","
				+ "markPoint:{"
				+ "data:["
				+ "{type:\"max\",name:\"最大值\"},"
				+ "{type:\"min\",name:\"最小值\"}"
				+ "]"
				+ "},markLine:{"
				+ "data:["
				+ "{type:\"average\",name:\"平均值\"}"
				+ "]"
				+ "}"
				+ "},"
				+ "{name:\"最低气温\","
				+ "type:\"line\","
				+ "data:"
				+ d2
				+ ","
				+ "markPoint:{"
				+ "data:["
				+ "{name:\"周最低\",value:\"-2\",xAxis:\"1\",yAxis:\"-1.5\"}"
				+ "]"
				+ "},"
				+ "markLine:{"
				+ "data:["
				+ "{type:\"average\",name:\"平均值\"}" + "]" + "}" + "}" + "]}";
		return data;

	}
	/**
	 * 返回有权限的首页工具
	 * @return
	 */
	public List<SysIndexTools> getIndexToolsList(){
		List<SysIndexTools> sysIts = new ArrayList<SysIndexTools>();
//		try {
//			SysLayoutTools sysLayoutTools = sysLayoutToolsManager.getByLayoutID(PortalUtil.getCurrentUserSetting().getLayoutId(), "统计栏目");
//			String[] SysIndexToolsIds = sysLayoutTools.getToolsIds().split(",");
//			List<String> authorizeIdsByUserMap = bpmDefUserManager.getAuthorizeIdsByUserMap(SysIndexTools.INDEX_TOOLS);
//			for (String SysIndexToolsId : SysIndexToolsIds) {
//				if(authorizeIdsByUserMap.contains(SysIndexToolsId)||toolsId.contains(SysIndexToolsId)){
//					Object data = null;
//					SysIndexTools sysIndexTools = sysIndexToolsManager.get(SysIndexToolsId);
//					if(sysIndexTools.getCountMode().equals(SysIndexTools.SERVICE)){
//						data = getModelByHandler(sysIndexTools.getCounting(),null,null);
//					}else if(sysIndexTools.getCountMode().equals(SysIndexTools.SLEF_QUERY)){
//						data = getQueryPage(sysIndexTools.getCounting());
//					}
//					if(data != null) {
//						if (data instanceof List) {
//							sysIndexTools.setStatistics(((List) data).size() * 1L);							
//						} else {
//							sysIndexTools.setStatistics(Long.valueOf(data.toString()));
//						}
//					}
//					sysIts.add(sysIndexTools);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return sysIts;
	}
	/**
	 * 根据handler取得数据。
	 * 
	 * <pre>
	 * handler 为 service +"." + 方法名称。
	 * </pre>
	 * 
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	private Object getModelByHandler(String handler, Object[] args,
			Class<?>[] parameterTypes) throws Exception {
		Object model = null;
		if (StringUtil.isEmpty(handler))
			return model;
//		int rtn = BpmUtil.isHandlerValidNoCmd(handler, parameterTypes);
//		if (rtn != 0)
//			return model;
		String[] aryHandler = handler.split("[.]");
		if (aryHandler == null)
			return model;
		String beanId = aryHandler[0];
		String method = aryHandler[1];
		// 触发该Bean下的业务方法
		Object serviceBean = AppUtil.getBean(beanId);
		// 如果配置数据来源的方法带有参数的时候

		if (serviceBean == null)
			return model;
		if (args == null || args.length <= 0) {
			parameterTypes = new Class<?>[0];
		}
		Method invokeMethod = serviceBean.getClass().getDeclaredMethod(method,
				parameterTypes);

		model = invokeMethod.invoke(serviceBean, args);
		if (BeanUtils.isEmpty(model))
			model = null;
		return model;
	}
	/**
	 * 获取自定义查询列表
	 * @param alias
	 * @return
	 */
//	private  List getQueryPage(String alias){
//		List pageList = new PageList<>();
//		try {
//			CustomQuery customQuery = customQueryManager.getByAlias(alias);
//			if (customQuery==null) {
//				return null;
//			}
//			String dbType= SysDataSourceUtil. getDbType(customQuery.getDsalias());
//			customQuery.setNeedPage(0);
//			// 切换这次进程的数据源
//			DbContextHolder.setDataSource(customQuery.getDsalias(),dbType);
//			pageList = customQueryManager.getData(customQuery, null,dbType, 1, 20);
//		} catch (Exception e) {}
//		return pageList;
//	}
	
	/**
	 *
	 * Title:返回有权限的首页工具
	 * Description:
	 * Created On: 2017/9/8 上午11:00
	 * @author mahongcao
	 * @return
	 */
	public JSONObject getIndexToolsList(String toolsType){
		JSONObject result = new JSONObject();
		result.put("msgCode",0);
		result.put("msg","ok");
		if(StringUtils.isNotBlank(toolsType)){
			List<SysIndexTools> sysIts = getIndexToolsList2(toolsType);
			result.put(toolsType,sysIts);
		}
		return result;
	}
	/**
	 * 返回有权限的首页工具
	 * @return
	 */
	public List<SysIndexTools> getIndexToolsList2(String toolsType){
		List<SysIndexTools> sysIts = new ArrayList<SysIndexTools>();
//		try {
//			SysLayoutTools sysLayoutTools = sysLayoutToolsManager.getByLayoutID(PortalUtil.getCurrentUserSetting().getLayoutId(), toolsType);
//			String[] SysIndexToolsIds = sysLayoutTools.getToolsIds().split(",");
//			List<String> authorizeIdsByUserMap = bpmDefUserManager.getAuthorizeIdsByUserMap(BpmDefUser.BPMDEFUSER_OBJ_TYPE.INDEX_TOOLS);
//			for (String SysIndexToolsId : SysIndexToolsIds) {
//				if(authorizeIdsByUserMap.contains(SysIndexToolsId)||toolsId.contains(SysIndexToolsId)){
//					Object data = null;
//					SysIndexTools sysIndexTools = sysIndexToolsManager.get(SysIndexToolsId);
//					if(sysIndexTools == null){
//						continue;
//					}
//					if(sysIndexTools.getCountMode().equals(SysIndexTools.SERVICE)){
//						data = getModelByHandler(sysIndexTools.getCounting(),null,null);
//					}else if(sysIndexTools.getCountMode().equals(SysIndexTools.SLEF_QUERY)){
//						data = getQueryPage(sysIndexTools.getCounting());
//					}
//					if(data != null) {
//						if (data instanceof List) {
//							sysIndexTools.setStatistics(((List) data).size() * 1L);							
//						} else {
//							sysIndexTools.setStatistics(Long.valueOf(data.toString()));
//						}
//					}
//					sysIts.add(sysIndexTools);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return sysIts;
	}
	
	/**
	 *
	 * Title:
	 * Description:
	 * Created On: 2017/9/2 下午3:27
	 * @author mahongcao
	 * @return
	 */
	public JSONObject index(){
		JSONObject result = new JSONObject();
		result.put("msgCode",0);
		result.put("msg","ok");

		try{
//			PageList<DefaultBpmTask> todo = (PageList<DefaultBpmTask>) bpmTaskManager.getByUserId(ContextUtil.getCurrentUserId(), new DefaultQueryFilter());
//			int oaToDo = todo.getPageResult().getTotalCount();
//			result.put("oaToDo",oaToDo);
//			result.put("oaToDoUrl","/office/receivedProcess/pending");

//			int mailToRead = getToReadMailsNum();
//			result.put("mailToRead",mailToRead);
//			result.put("mailToReadUrl","/mail/mail/mail/mailTree");

//			int needAttendMeetingNum = getNeedAttendMeetingNum();
//			result.put("needAttendMeeting",needAttendMeetingNum);
//			result.put("needAttendMeetingUrl","/business/meeting/meeting/needAttendMeeting");
			
			int msgSize =sysMessageManager.getMsgSize(ContextUtil.getCurrentUserId());
			result.put("msgSize",msgSize);
			result.put("msgUrl","/innermsg/messageReceiverList");
		}catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}
	/**
	 *
	 * Title:获取未读邮件数
	 * Description:
	 * Created On: 2017/9/4 上午9:48
	 * @author mahongcao
	 * @return
	 */
//	public int getToReadMailsNum(){
//		PageList<Mail> mailList = getToReadMails(1,1);
//		return mailList.getPageResult().getTotalCount();
//	}
	/**
	 *
	 * Title:获取未读邮件
	 * Description:
	 * Created On: 2017/9/4 上午9:30
	 * @author mahongcao
	 * @return
	 */
//	public PageList<Mail> getToReadMails(Integer curPage, Integer pageSize){
//		String userId=ContextUtil.getCurrentUserId();
//		DefaultQueryFilter queryFilter = new DefaultQueryFilter();
//		DefaultPage page = new DefaultPage(curPage,pageSize);
//		queryFilter.setPage(page);
//		queryFilter.addFilter("USER_ID_", userId, QueryOP.EQUAL);
//		queryFilter.addFilter("TYPE_", 1, QueryOP.EQUAL);
//		queryFilter.addFilter("IS_READ_", 0, QueryOP.EQUAL);
//		PageList<Mail> mailList=(PageList<Mail>)mailManager.query(queryFilter);
//		if(mailList != null && mailList.size() > 0){
//			for (Mail mail:mailList) {
//				String detailUrl = "/mail/mail/mail/mailGet?id="+mail.getId()+"&parentGridId=grid&keyId="+mail.getId();
//				mail.setDetailUrl(detailUrl);
//			}
//		}
//		return mailList;
//	}
	
	/**
	 *
	 * Title:获取待参加会议数
	 * Description:
	 * Created On: 2017/9/4 上午10:14
	 * @author mahongcao
	 * @return
	 */
//	public int getNeedAttendMeetingNum(){
//		DefaultQueryFilter queryFilter = new DefaultQueryFilter();
//		DefaultPage page = new DefaultPage(1,1);
//		queryFilter.setPage(page);
//		queryFilter.addFilter("PROC_DEF_KEY_", "hylc",QueryOP.EQUAL);
//		String userId = ContextUtil.getCurrentUserId();
//		// 查询列表
//		PageList<DefaultBpmTask> list = (PageList<DefaultBpmTask>) bpmTaskManager.getByNeedPendMeetingUserId(userId, queryFilter);
//		return list.getPageResult().getTotalCount();
//	}
	
	/**
	 *
	 * Title: 未读邮件列表
	 * Description:
	 * Created On: 2017/9/5 上午10:42
	 * @author mahongcao
	 * @return
	 */
//	public JSONObject getToReadMailList(Integer curPage, Integer pageSize){
//		JSONObject result = new JSONObject();
//		result.put("msgCode",0);
//		result.put("msg","ok");
//
//		try {
//			PageList<Mail> mailList = getToReadMails(curPage, pageSize);
//			
//			result.put("mailList", mailList != null ? mailList : new PageList<Mail>());
//
//			int mailToRead = mailList.getPageResult().getTotalCount();
//			result.put("mailToRead", mailToRead);
//			result.put("moreUrl", "/mail/mail/mail/mailTree");
//
//		}catch (Exception e){
//			e.printStackTrace();
//		}
//		return result;
//	}
	
	/**
	 *
	 * Title:OA流程
	 * Description:
	 * Created On: 2017/8/25 下午3:03
	 * @author mahongcao
	 * @return
	 */
//	public JSONObject myTask(Integer curPage, Integer pageSize){
//		String userId = ContextUtil.getCurrentUserId();
//		JSONObject result = new JSONObject();
//		result.put("msgCode",0);
//		result.put("msg","ok");
//		try{
//			DefaultQueryFilter queryFilter = new DefaultQueryFilter();
//			DefaultPage page = new DefaultPage(curPage,pageSize);
//			queryFilter.setPage(page);
//			PageList<DefaultBpmTask> todo = (PageList<DefaultBpmTask>) bpmTaskManager.getByUserId(userId, queryFilter);
//			PageList<Map<String,Object>> todo2 = new PageList();
//			if(todo != null && todo.size() > 0){
//				for (DefaultBpmTask defaultBpmTask:todo
//					 ) {
//					Map<String, Object> map = transBean2Map(defaultBpmTask);
//					String taskId = defaultBpmTask.getTaskId();
//					map.put("rtnStatus",canLock(taskId));
//					map.put("toDoDetailUrl","/flow/task/taskApprove?id="+defaultBpmTask.getId());
//					map.put("name",defaultBpmTask.getName());
//					map.put("procDefName",defaultBpmTask.getProcDefName());
//					todo2.add(map);
//				}
//			}
//			todo2.setPageResult(todo.getPageResult());
//			result.put("todo",todo2);
//			result.put("toDoUrl","/office/receivedProcess/pending");
//			PageList<Map<String,Object>> alreadyDo = (PageList<Map<String,Object>>)  bpmProcessInstanceManager.getHandledByUserId(userId, queryFilter);
//			if(alreadyDo != null && alreadyDo.size() > 0){
//				for (Map<String,Object> map:alreadyDo
//						) {
//					String taskKey = (String) map.get("taskKey");
//					String id = (String) map.get("id");
//					String detailUrl ="/flow/instance/instanceGet?id="+id+"&taskKey="+taskKey+"&parentGridId=grid&keyId="+id;
//					map.put("alreadyDoDetailUrl",detailUrl);
//				}
//			}
//			result.put("alreadyDo",alreadyDo);
//			result.put("alreadyDoUrl","/office/receivedProcess/handled");
//		}catch (Exception e){
//			e.printStackTrace();
//		}
//		return result;
//	}
	//利用Introspector和PropertyDescriptor 将Bean --> Map
		public  Map<String, Object> transBean2Map(Object obj) {

			if(obj == null){
				return null;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
				PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
				for (PropertyDescriptor property : propertyDescriptors) {
					String key = property.getName();

					// 过滤class属性
					if (!key.equals("class")) {
						// 得到property对应的getter方法
						Method getter = property.getReadMethod();
						if(getter == null){
							continue;
						}
						Object value = getter.invoke(obj);
						if(value == null){
							continue;
						}
						map.put(key, value);
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return map;

		}
		/**
		 *
		 * Title:OA权限
		 * Description:
		 * Created On: 2017/9/12 上午10:27
		 * @return
		 */
//		public int canLock(String taskId){
//			DefaultBpmTask task = bpmTaskManager.get(taskId);
//			boolean isForbindden = bpmInstService.isSuspendByInstId(task.getProcInstId());
//			if(isForbindden){//流程已经被禁止
//				return 6;
//			}
//			int rtn = bpmTaskManager.canLockTask(taskId);
//			return rtn;
//		}

		/**
		 *
		 * Title: 未读消息列表
		 * Description:
		 * Created On: 2017/9/5 上午10:42
		 * @return
		 */
		public JSONObject msgList(Integer curPage, Integer pageSize){
			JSONObject result = new JSONObject();
			result.put("msgCode",0);
			result.put("msg","ok");

			try {
				PageList<SysMessage> msgList = getToReadMsg(curPage, pageSize);
				result.put("msgList", msgList != null ? msgList : new PageList<SysMessage>());
				result.put("moreUrl", "/innermsg/messageReceiverList");

			}catch (Exception e){
				e.printStackTrace();
			}
			return result;
		}
		
		/**
		 *
		 * Title:获取未读消息
		 * Description:
		 * @return
		 */
		public PageList<SysMessage> getToReadMsg(Integer curPage, Integer pageSize){
			String userId=ContextUtil.getCurrentUserId();
			DefaultQueryFilter queryFilter = new DefaultQueryFilter();
			DefaultPage page = new DefaultPage(curPage,pageSize);
			queryFilter.setPage(page);
			queryFilter.addFilter("receiverId", ContextUtil.getCurrentUserId(), QueryOP.EQUAL);
			PageList<SysMessage> sysMessageList = (PageList<SysMessage>) sysMessageManager.getMsgByUserId(queryFilter);
			Collections.sort(sysMessageList, new ReceiveTimeComparator());
			PageList<SysMessage> newSysMessageList = new PageList<SysMessage>();
			//消息类型值得转换
			for (int i = 0; i < sysMessageList.size(); i++) {
				SysMessage sysMessage = sysMessageList.get(i);
				String messageType= MessageTypeUtil.getValue(sysMessage.getMessageType());
				sysMessage.setMessageType(messageType);
				newSysMessageList.add(sysMessage);
			}
			newSysMessageList.setPageResult(sysMessageList.getPageResult());
			if(newSysMessageList != null && newSysMessageList.size() > 0){
				for (SysMessage msg:newSysMessageList) {
					String detailUrl = "/innermsg/messageRead/get?id="+msg.getId()+"&isPublic="+msg.getIsPublic()+"&canReply="+msg.getCanReply();
					msg.setDetailUrl(detailUrl);
				}
			}
			return newSysMessageList;
		}
		//（1）未读的消息排在前面，未读的消息按照“发信时间”从后往前排序   （2）已读的消息只按照“发信时间”从后往前排序；
		static class ReceiveTimeComparator implements Comparator {  
			@Override
			public int compare(Object o1, Object o2) {
				SysMessage p1 = (SysMessage) o1; // 强制转换  
				SysMessage p2 = (SysMessage) o2;  
				Date date1 = p1.getReceiveTime();
				Date date2 = p2.getReceiveTime();
				int flag = 1;
				if((date1!=null&&date2!=null)||(date1==null&&date2==null)){
					flag = -p1.getCreateTime().compareTo(p2.getCreateTime());
				}else if(date2!=null){
					flag = -1;
				}
	            return flag;  
			}  
	    } 
		
		/**
		 * 获取用户可以访问的流程定义
		 * 
		 * @return
		 */
//		public JSONObject newProcess(Integer curPage, Integer pageSize) {
//			JSONObject result = new JSONObject();
//			result.put("msgCode",0);
//			result.put("msg","ok");
//			DefaultQueryFilter queryFilter = new DefaultQueryFilter();
//			DefaultPage page = new DefaultPage(curPage,pageSize);
//			queryFilter.setPage(page);
//			queryFilter.addFilter("is_main_", "Y", QueryOP.EQUAL);
//			queryFilter.addFilter("status_", "deploy", QueryOP.EQUAL);
//			queryFilter.addParamsFilter("bpmDefAuthorizeRightType", BPMDEFAUTHORIZE_RIGHT_TYPE.START);
//
//			// 查询列表 
//			PageList<DefaultBpmDefinition> list = (PageList<DefaultBpmDefinition>) bpmDefinitionManager.queryList(queryFilter);
//			result.put("processList", list != null ? list : new PageList<DefaultBpmDefinition>());
//			result.put("moreUrl", "/office/initiatedProcess/newProcess");
//			return result;
//		}
		
		/**
		 * 获取用户可以访问的流程定义
		 * 
		 * @return
		 */
//		public PageList<DefaultBpmDefinition> newportalProcess(Integer curPage, Integer pageSize) {
//			DefaultQueryFilter queryFilter = new DefaultQueryFilter();
//			DefaultPage page = new DefaultPage(curPage,pageSize);
//			queryFilter.setPage(page);
//			queryFilter.addFilter("is_main_", "Y", QueryOP.EQUAL);
//			queryFilter.addFilter("status_", "deploy", QueryOP.EQUAL);
//			queryFilter.addParamsFilter("bpmDefAuthorizeRightType", BPMDEFAUTHORIZE_RIGHT_TYPE.START);
//
//			// 查询列表 
//			PageList<DefaultBpmDefinition> list = (PageList<DefaultBpmDefinition>) bpmDefinitionManager.queryList(queryFilter);
//			return list;
//		}
		
		
		/**
		 * 信息盒子
		 * 
		 * @return
		 * @throws Exception
		 */
		public List<Infobox> getInfobox() throws Exception {
			List<Infobox> list = new ArrayList<Infobox>();
			List<SysIndexTools> tools = this.getIndexToolsList();
			for (SysIndexTools tool : tools) {
				Infobox infobox = new Infobox();
				infobox.setId(tool.getId());
				infobox.setIcon(tool.getIcon());
				infobox.setFontStyle(tool.getFontStyle());
				infobox.setNumberStyle(tool.getNumberStyle());
				if (BeanUtils.isEmpty(tool.getStatistics()) || tool.getStatistics() == 0) {
					if ("待办事项,未读邮件".contains(tool.getName())) {
						infobox.setDataText(tool.getStatistics() + "");
					}
				} else {
					infobox.setDataText(tool.getStatistics() + "");
				}
				infobox.setDataContent(tool.getName());
				if (BeanUtils.isEmpty(tool.getUrl())) {
					infobox.setUrl("/noFunction");
				} else {
					infobox.setUrl(tool.getUrl());
				}
				list.add(infobox);
			}
			return list;

		}
		// 待办事项
//		public int countBpmTask() {
//			List<DefaultBpmTask> list = this.pendingMatters(0, 0);
//			return BeanUtils.isEmpty(list) ? 0 : list.size();
//		}
		// 未读邮件
//		public int countEmailNotRead() {
//			int count = 0;
//			String userId = ContextUtil.getCurrentUserId();
//			MailSetting defaultMail = mailSettingManager.getByIsDefault(userId);
//			if (BeanUtils.isNotEmpty(defaultMail)) {
//				QueryFilter queryFilter = new DefaultQueryFilter();
//				queryFilter.addParamsFilter("userId", userId);
//				queryFilter.addParamsFilter("setId", defaultMail.getId());
//				queryFilter.addParamsFilter("type", 1);
//				List<Mail> list = mailManager.getFolderList(queryFilter);
//				for (Mail mail : list) {
//					if (mail.getIsRead() == 0) {
//						count++;
//					}
//				}
//			}
//			return count;
//		}
		// 会议日程
//		public int countMeetingNeed() {
//			QueryFilter queryFilter = new DefaultQueryFilter();
//			queryFilter.addFilter("PROC_DEF_KEY_", "hylc", QueryOP.EQUAL);
//			List<DefaultBpmTask> list = bpmTaskManager.getByNeedPendMeetingUserId(ContextUtil.getCurrentUserId(),
//					queryFilter);
//			return BeanUtils.isEmpty(list) ? 0 : list.size();
//		}
		// 抄送
//		public int MyCopyTo() {
//			String userId = ContextUtil.getCurrentUserId();
//			List<CopyTo> list = copyToManager.getMyCopyTo(userId, new DefaultQueryFilter());
//			return BeanUtils.isEmpty(list) ? 0 : list.size();
//		}
		/**
		 * 我的请求
		 * @return
		 */
//		public int myRequestsize(){
//			String userId = ContextUtil.getCurrentUserId();
//			List<DefaultBpmProcessInstance> list =  bpmProcessInstanceManager.getMyRequestByUserId(userId, new DefaultQueryFilter());
//			return BeanUtils.isEmpty(list) ? 0 : list.size();
//		}
		/**
		 * 我的草稿
		 * @return
		 */
//		public int myDraftssize(){
//			String userId = ContextUtil.getCurrentUserId();
//			List<DefaultBpmProcessInstance> list = bpmProcessInstanceManager.getDraftsByUserId(userId, new DefaultQueryFilter());
//			return BeanUtils.isEmpty(list) ? 0 : list.size();
//		}
		/**
		 * 我的草稿
		 * @return
		 */
//		public int myCompletedsize(){
//			String userId = ContextUtil.getCurrentUserId();
//			List<DefaultBpmProcessInstance> list = bpmProcessInstanceManager.getMyCompletedByUserId(userId, new DefaultQueryFilter());
//			return BeanUtils.isEmpty(list) ? 0 : list.size();
//		}
		
		/**
		 * 我的会议
		 * @return
		 */
//		private Infobox getMyMeetingBox() {
//			Infobox infobox = new Infobox();
//			infobox.setIcon("fa-check-square-o");
//			infobox.setColor(Infobox.COLOR_BROWN);
//			infobox.setDataContent("我的会议");
//			infobox.setUrl("/business/meeting/meeting/needAttendMeeting");
//			int needAttendMeetingNum = getNeedAttendMeetingNum();;
//			infobox.setDataText(needAttendMeetingNum + "");
//			return infobox;
//		}
		
		/**
		 * 信息盒子
		 * 
		 * @return
		 */
//		public List<Infobox> getDefaultInfobox() {
//			List<Infobox> list = new ArrayList<Infobox>();
//			try {
//				Infobox myTaksBox = this.getMyTaksBox();	//	我的待办
//				Infobox myMessBox = this.getMyMessBox();	//	内部消息
//				Infobox myProCopytoBox = this.getMyProCopytoBox();	//	抄送转发
//				Infobox myAlreadyCompletedBox = getMyAlreadyCompletedBox();	//	已办、办结事宜
//				Infobox myCompletedBox = getMyCompletedBox();	//	我的办结
//				
//				Infobox myAccordingMattersBox = this.getMyAccordingMattersBox();	//	转办代理事宜
//				Infobox myRequestBox = this.getMyRequestBox();	//	 我的请求
//				Infobox myDraftBox = this.getMyDraftBox();	//	 我的草稿
//				Infobox MyMeeting = this.getMyMeetingBox();	//	我的会议
//				
//				list.add(myTaksBox);	//	我的待办
//				list.add(myMessBox);	//	内部消息
//				list.add(myProCopytoBox);	//	抄送转发
//				list.add(myAlreadyCompletedBox);	//	已办、办结事宜
//				list.add(myCompletedBox);	//	我的办结
//				
//				list.add(myAccordingMattersBox);	//	转办代理事宜
//				list.add(myRequestBox);	//	我的请求
//				list.add(myDraftBox);	//	我的草稿
//				list.add(MyMeeting);	//	我的会议
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return list;
//
//		}

}

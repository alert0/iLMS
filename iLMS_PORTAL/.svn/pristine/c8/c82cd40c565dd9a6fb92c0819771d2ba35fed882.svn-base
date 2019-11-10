package com.hotent.bpmx.api.model.process.def;

import java.io.Serializable;

import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bpmx.api.constant.BpmConstants;

/**
 * 节点的其他属性。
 * <pre> 
 * 构建组：x5-bpmx-api
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-8-18-下午3:47:21
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class NodeProperties implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3157546646728816168L;
	
	//父定义KEY
	private String parentDefKey="";
	//节点ID
	private String nodeId="";
	//跳转类型(common,free,select)
	private String jumpType="";
	
	//前置处理器
	private String prevHandler="";
	//后置处理器
	private String postHandler="";
	//允许执行人空
	private boolean allowExecutorEmpty=false;
	//执行人为空跳过
	private boolean skipExecutorEmpty=false;
	//通知类型
	private String notifyType="";
	//返回模式(direct:直来直往,normal:流程图)
	private String backMode="normal";
	//返回节点
	private String backNode="";
	
	//驳回处理人模式： 历史处理人：history, normal：正常节点上配置的人员
	private String backUserMode="";
	
	// 计算审批期限的类型   worktime, caltime
	private String dateType = "worktime";
	
	// 节点审批期限
	private int dueTime = 0;
	
	// 是否弹窗 默认弹窗
	private boolean popWin = true;
	
	public String getParentDefKey() {
		if(StringUtil.isEmpty(parentDefKey)){
			return BpmConstants.LOCAL;
		}
		return parentDefKey;
	}
	public void setParentDefKey(String parentDefKey) {
		this.parentDefKey = parentDefKey;
	}
	
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getJumpType() {
		return jumpType;
	}
	public void setJumpType(String jumpType) {
		this.jumpType = jumpType;
	}
	
	
	
	public String getPrevHandler() {
		return prevHandler;
	}
	public void setPrevHandler(String prevHandler) {
		this.prevHandler = prevHandler;
	}
	
	public String getPostHandler() {
		return postHandler;
	}
	public void setPostHandler(String postHandler) {
		this.postHandler = postHandler;
	}
	
	public boolean isAllowExecutorEmpty() {
		return allowExecutorEmpty;
	}
	public void setAllowExecutorEmpty(boolean allowExecutorEmpty) {
		this.allowExecutorEmpty = allowExecutorEmpty;
	}
	public boolean isSkipExecutorEmpty() {
		return skipExecutorEmpty;
	}
	public void setSkipExecutorEmpty(boolean skipExecutorEmpty) {
		this.skipExecutorEmpty = skipExecutorEmpty;
	}
	public String getNotifyType() {
		return notifyType;
	}
	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}
	public String getBackMode() {
		return backMode;
	}
	public void setBackMode(String backMode) {
		this.backMode = backMode;
	}
	public String getBackNode() {
		return backNode;
	}
	public void setBackNode(String backNode) {
		this.backNode = backNode;
	}
	public String getBackUserMode() {
		return backUserMode;
	}
	public void setBackUserMode(String backUserMode) {
		this.backUserMode = backUserMode;
	}
	public String getDateType() {
		return dateType;
	}
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	public int getDueTime() {
		return dueTime;
	}
	public void setDueTime(int dueTime) {
		this.dueTime = dueTime;
	}
	
	public boolean isPopWin() {
		return popWin;
	}
	public void setPopWin(boolean popWin) {
		this.popWin = popWin;
	}

}

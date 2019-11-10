package com.hotent.bpmx.plugin.task.tasknotify.def.model;

import java.util.ArrayList;
import java.util.List;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringCollections;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.UserAssignRule;

public class NotifyItem {
    /**
     * 用户分配规则（用于查询用户）
     */
    private List<UserAssignRule> userAssignRules;
    
    /**
     * 消息类型（mail=邮件；inner=内部消息；sms=短信）
     */
	private List<String> msgTypes = new ArrayList<String>();
    
    
    public List<UserAssignRule> getUserAssignRules() {
		return userAssignRules;
	}
	public void setUserAssignRules(List<UserAssignRule> userAssignRules) {
		this.userAssignRules = userAssignRules;
	}
	public List<String> getMsgTypes() {
		return msgTypes;
	}
	
	public String getMessageTypes(){
		if(BeanUtils.isEmpty(msgTypes)) return "";
		String str="";
		for(String msg:msgTypes){
			str+=msg +",";
		}
		return str.substring(0,str.length()-1);
	}
	
	public static void main(String[] args) {
		List<String> list=new ArrayList<String>();
		list.add("aa");
		list.add("bb");
		list.add("cc");
		String str="";
		for(String msg:list){
			str+=msg +",";
		}
		
		System.out.println( str.substring(0,str.length()-1));
	}
	
	public void setMsgTypes(String msgTypes){
		this.msgTypes = StringCollections.toList(msgTypes, ",");
	}

}

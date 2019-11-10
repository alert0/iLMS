package com.hotent.form.api.service;



/**
 * 表单权限接口
 * @author heyifan
 * @version 创建时间: 2014-11-27
 */
public interface BpmFormRightsService {
	/**
	 * 获取表单权限
	 * <pre>
	 * {
	 * 	field：{"NAME": "w", "SEX": "r"}
	 * 	table：{"TABLE1": "r", "TABLE2": "w"}
	 * 	opinion：{"领导意见": "w", "部门意见": "r"}
	 * }
	 * </pre>
	 * @param formKey 表单KEY 对应BPM_FROM key字段。
	 * @param userId 用户ID
	 * @param flowKey 流程KEY
	 * @param nodeId 节点ID
	 * @return
	 */
	String getPermission(String formKey, String userId, String flowKey,String parentFlowKey, String nodeId);
	
	/**
	 * 获取流程实例表单的权限。
	 * <pre>
	 * {
	 * 	field：{"NAME": "w", "SEX": "r"}
	 * 	table：{"TABLE1": "r", "TABLE2": "w"}
	 * 	opinion：{"领导意见": "w", "部门意见": "r"}
	 * }
	 * </pre>
	 * @param formKey	表单KEY 对应BPM_FROM key字段。
	 * @param userId
	 * @param flowKey
	 * @return
	 */
	String getInstPermission(String formKey, String userId, String flowKey);

	String getStartPermission(String formKey,String flowKey, String nodeId, String nextNodeId);
}

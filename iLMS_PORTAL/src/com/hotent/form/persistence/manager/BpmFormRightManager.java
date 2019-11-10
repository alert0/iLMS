package com.hotent.form.persistence.manager;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hotent.base.manager.api.Manager;
import com.hotent.form.persistence.model.BpmFormRight;

/**
 * 
 * <pre> 
 * 描述：BPM_FORM_RIGHT 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-04-19 14:22:02
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface BpmFormRightManager extends Manager<String, BpmFormRight>{
	
	
	
	/**
	 * 删除实例权限。
	 * @param flowKey
	 */
	void removeInst(String flowKey);
	
	void remove(String formKey,String flowKey,String nodeId,String parentFlowKey);
	
	/**
	 * 删除流程节点上的权限。
	 * @param flowKey
	 */
	void remove(String flowKey,String nodeId, String parentFlowKey);
	
	/**
	 * 删除流程定义权限。
	 * @param flowKey
	 * @param parentFlowKey
	 */
	void remove(String flowKey,String parentFlowKey);
	
	/**
	 * 保存表单权限。
	 * @param formKey			表单key		对应bpm_form 的key 字段。
	 * @param flowKey			流程key
	 * @param parentFlowKey		父流程key
	 * @param nodeId			节点ID
	 * @param permission		流程权限
	 * @param type				类型(1,流程权限,2,实例权限)
	 */
	void save(String formKey, String flowKey,String parentFlowKey,
			String nodeId,String permission,int type);
	
	/**
	 * 获取表单权限设定。
	 * {
		    "table": {
		        "gerejianli": {
		            "description": "个人简历",
		            "fields":{
				            "name": {
				                "description": "姓名",
				                "read": [
				                    {
				                        "type": "everyone"
				                    }
				                ],
				                "write": [
				                    {
				                        "type": "user",
				                        "id":"1,2,..."
				                        "name":"a,b,..."
				                    }
				                ],
				                "required": [
				                    {
				                        "type": "script",
				                        "id":"xxxxxxxxx"
				                    }
				                ]
				            },
				            "age": {
				                "description": "年龄",
				                "read": [
				                    {
				                        "type": "everyone"
				                    }
				                ],
				                "write": [
				                    {
				                        "type": "none"
				                    }
				                ],
				                "required": [
				                    {
				                        "type": "none"
				                    }
				                ]
				            }
				    }
		            ,
		            "main": true
		        },
		        "education": {
		            "description": "教育经历",
		            fields":{
			            "school": {
			                "description": "毕业学校",
			                "read": [
			                    {
			                        "type": "everyone"
			                    }
			                ],
			                "write": [
			                    {
			                        "type": "none"
			                    }
			                ],
			                "required": [
			                    {
			                        "type": "none"
			                    }
			                ]
			            }
			        }
		            ,
		            "main": false,
		            "rights": {
		                "hidden": false,
		                "add": true,
		                "del": true,
		                "required": true
		            }
		        }
		    },
		    "opinion": {
		        "bumenjingli": {
		            "description": "部门经理",
		            "read": [
		                {
		                    "type": "everyone"
		                }
		            ],
		            "write": [
		                {
		                    "type": "none"
		                }
		            ],
		            "required": [
		                {
		                    "type": "none"
		                }
		            ]
		        },
		        "caiwu": {
		            "description": "财务意见",
		            "read": [
		                {
		                    "type": "everyone"
		                }
		            ],
		            "write": [
		                {
		                    "type": "none"
		                }
		            ],
		            "required": [
		                {
		                    "type": "none"
		                }
		            ]
		        }
		    }
		}
	 * @param formKey				BPM_FORM 的key字段。
	 * @param flowKey
	 * @param parentFlowKey
	 * @param nodeId
	 * @return
	 */
	JSONObject getPermissionSetting(String formKey,String flowKey,String parentFlowKey,String nodeId,int type);
	
	
	
	/**
	 * 获取默认表单权限。
	 * {
		    "fields": {
		        "table1": {
		            "name": "w",
		            "age": "b"
		        },
		        "table2": {
		            "name": "w",
		            "age": "r"
		        },
		        "table3": {
		            "name": "w",
		            "age": "w"
		        }
		    },
		    "table":{
		     "table1":{"hidden":true}
		     "table2":{"hidden":false,"add":"true","del":"true","required":"true"}
		    },
		    "opinion":{"jzyj":"w","zxyj":"r","zxyj":"b"}
		}	
	 * @param formKey			表单KEY 对应BPM_FROM key字段。
	 * @param flowKey
	 * @param parentFlowKey
	 * @param nodeId
	 * @param type
	 * @return
	 */
	JSONObject getPermission(String formKey,String flowKey,String parentFlowKey,String nodeId,int type);
	/**
	 * 获取表单权限设定。
	 * {
		    "table": {
		        "gerejianli": {
		            "description": "个人简历",
		            "fields":{
				            "name": {
				                "description": "姓名",
				                "read": [
				                    {
				                        "type": "everyone"
				                    }
				                ],
				                "write": [
				                    {
				                        "type": "user",
				                        "id":"1,2,..."
				                        "name":"a,b,..."
				                    }
				                ],
				                "required": [
				                    {
				                        "type": "script",
				                        "id":"xxxxxxxxx"
				                    }
				                ]
				            },
				            "age": {
				                "description": "年龄",
				                "read": [
				                    {
				                        "type": "everyone"
				                    }
				                ],
				                "write": [
				                    {
				                        "type": "none"
				                    }
				                ],
				                "required": [
				                    {
				                        "type": "none"
				                    }
				                ]
				            }
				    }
		            ,
		            "main": true
		        },
		        "education": {
		            "description": "教育经历",
		            fields":{
			            "school": {
			                "description": "毕业学校",
			                "read": [
			                    {
			                        "type": "everyone"
			                    }
			                ],
			                "write": [
			                    {
			                        "type": "none"
			                    }
			                ],
			                "required": [
			                    {
			                        "type": "none"
			                    }
			                ]
			            }
			        }
		            ,
		            "main": false,
		            "rights": {
		                "hidden": false,
		                "add": true,
		                "del": true,
		                "required": true
		            }
		        }
		    },
		    "opinion": {
		        "bumenjingli": {
		            "description": "部门经理",
		            "read": [
		                {
		                    "type": "everyone"
		                }
		            ],
		            "write": [
		                {
		                    "type": "none"
		                }
		            ],
		            "required": [
		                {
		                    "type": "none"
		                }
		            ]
		        },
		        "caiwu": {
		            "description": "财务意见",
		            "read": [
		                {
		                    "type": "everyone"
		                }
		            ],
		            "write": [
		                {
		                    "type": "none"
		                }
		            ],
		            "required": [
		                {
		                    "type": "none"
		                }
		            ]
		        }
		    }
		}
	 * @param formDefKey 表单定义Key
	 * @param isInstance
	 * @return
	 */
	JSONObject getDefaultByFormDefKey(String formKey,boolean isInstance);
	/**
	 * 通过表单key获取表单权限
	 * @param formKey
	 * @param isInstance
	 * @return
	 */
	JSONObject getByFormKey(String formKey,boolean isReadOnly);
	
	/**
	 * 通过权限配置计算表单权限
	 * @param permissionConf
	 * @return
	 */
	JSONObject calcFormPermission(JSONObject permissionConf);
	
	/**
	 * 
	 * 通过表单key删除表单权限
	 * @param formKey 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	void removeByFormKey(String formKey);

	JSONObject getStartPermission(String formKey, String flowKey, String nodeId, String nextNodeId);

	List<BpmFormRight> getByFlowKey(String defKey);

	void importFormRights(String formRightsXml);
	
	/**
	 * 
	 * TODO方法名称描述  获取表单授权页面中表的排序顺序
	 * @param formKey
	 * @return 
	 * List<Map<String,String>>
	 * @exception 
	 * @since  1.0.0
	 */
	List<Map<String,String>> getTableOrderBySn(String formKey);
}

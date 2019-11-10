package com.hotent.bpmx.api.model.process.def;

import com.hotent.base.api.BaseModel;
import com.hotent.bpmx.api.constant.DesignerType;

/**
 * 
 * 构建组：x5-bpmx-api
 * 描述：流程定义实体接口
 * 作者：csx
 * 邮箱:chensx@jee-soft.cn
 * 日期:2013-11-7-下午2:38:36
 * 版权：广州宏天软件有限公司版权所有
 */
public interface BpmDefinition extends BaseModel{
	
	/**
	 * 流程定义。
	 */
	public final static String BPM_DEFINITION="PROC_DEF_";
	
	public final static String BPM_BPMN_ID="bpmn_id_";
	
	public final static class STATUS{
		/**
		 * 草稿
		 */
		public final static String DRAFT="draft";
		/**
		 * 已发布
		 */
		public final static String DEPLOY="deploy";
		/**
		 * 禁用
		 */
		public final static String FORBIDDEN="forbidden";
		/**
		 * 禁用流程实例
		 */
		public final static String FORBIDDEN_INSTANCE="forbidden_instance";	
		/**
		 * 删除
		 */
		public final static String DELETED="deleted";
	}
	
	
	public final static class TEST_STATUS{
		/**
		 * 测试状态
		 */
		public final static String TEST="test";
		/**
		 * 运营状态
		 */
		public final static String RUN="run";
	}
	/**
	 * 流程定义ID
	 * @return
	 */
	String getDefId();
	/**
	 * 流程名称
	 * @return
	 */
	String getName();
	
	/**
	 * 流程定义key
	 * @return
	 */
	String getDefKey();
	/**
	 * 流程定义描述
	 * @return
	 */
	String getDesc();
	
	
	/**
	 * 返回流程状态。
	 * <pre>
	 * draft :草稿
	 * deploy:已发布
	 * forbidden:禁止
	 * forbidden_instance:禁止实例
	 * deleted:删除
	 * </pre>
	 * @return
	 */
	String getStatus();
	
	/**
	 * 测试状态。
	 * run:生产流程。
	 * test:测试流程
	 * @return
	 */
	String getTestStatus();
	
	/**
	 * 设计器。
	 * {@linkplain DesignerType 设计器类型}
	 * @param designer
	 * @return  String
	 */
	String getDesigner();
	
	/**
	 * 数据 - 返回流程定义的XML
	 * @return
	 */
	String getDefXml();
	/**
	 * 数据 - 返回流程定义的JSON
	 * @return
	 */
	String getDefJson();
	
	/**
	 * 数据 - 获取转化成BPMN的XML
	 * @return
	 */
	String getBpmnXml();
	
	/**
	 * BPMN - 流程定义ID 
	 * @return
	 */
	String getBpmnDefId();
	
	/**
	 * BPMN - 流程发布ID
	 * @return
	 */
	String getBpmnDeployId();
	
	/**
	 * 版本 - 当前版本号
	 * @return
	 */
	Integer getVersion();
	
	/**
	 * 版本 - 主版本定义ID
	 * @return
	 */
	String getMainDefId();
	
	/**
	 * 版本 - 是否主版本
	 * @return
	 */
	boolean isMain();	
	
	/**
	 * 版本 - 变更原因
	 * @return
	 */
	String getReason();
	
	
	
	
}

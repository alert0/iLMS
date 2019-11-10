package com.hotent.bpmx.api.constant;
/**
 * 审批状态
 * <pre>
 * 构建组：x5-bpmx-api
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-4-1-下午5:54:18
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 *
 */
public enum OpinionStatus {
	/**
	 * 提交
	 */
	START("start","提交"),
	
	/**
	 * 结束
	 */
	END("end","结束"),
	
	/**
	 * 待审批
	 */
	AWAITING_CHECK("awaiting_check","待审批"),
	/**
	 * 发起沟通
	 */
	START_COMMU("start_commu","发起沟通"),
	/**
	 * 待反馈
	 */
	AWAITING_FEEDBACK("awaiting_feedback","待反馈"),
	/**
	 * 待反馈
	 */
	FEEDBACK("feedback","已反馈"),
	/**
	 * 同意
	 */
	AGREE("agree","同意"),
	/**
	 * 反对
	 */
	OPPOSE("oppose","反对"),
	/**
	 * 弃权
	 */
	ABANDON("abandon","弃权"),
	/**
	 * 驳回
	 */
	REJECT("reject","驳回"),
	/**
	 * 驳回到发起人
	 */
	BACK_TO_START("backToStart","驳回到发起人"),
	/**
	 * 重新提交
	 */
	RE_SUBMIT("reSubmit","重新提交"),
	/**
	 * 撤回
	 */
	REVOKER("revoker","撤回"),
	/**
	 * 撤销流转
	 */
	TRANS_REVOKER("transRevoker","撤销流转"),
	/**
	 * 撤销到发起人
	 */
	REVOKER_TO_START("revoker_to_start","撤回到发起人"),
	/**
	 * 会签通过
	 */
	SIGN_PASSED("signPass","会签通过"),
	/**
	 * 会签不通过
	 */
	SIGN_NOT_PASSED("signNotPass","会签不通过"),
	/**
	 * 驳回取消
	 */
	SIGN_BACK_CANCEL("signBackCancel","驳回取消"),
	/**
	 * 撤销取消
	 */
	SIGN_RECOVER_CANCEL("signRecoverCancel","撤销取消"),
	/**
	 * 通过取消
	 */
	SIGN_PASS_CANCEL("passCancel","通过取消"),
	/**
	 * 不通过取消
	 */
	SIGN_NOPASS_CANCEL("notPassCancel","不通过取消"),
	/**
	 * 流转
	 */
	TRANS_FORMING("transforming","流转"),
	/**
	 * 转办任务
	 */
	DELIVERTO("deliverto","转办任务"),
	/**
	 * 转办任务取消
	 */
	DELIVERTO_CANCEL("deliverto_cancel","转办任务取消"),
	/**
	 * 同意
	 */
	TRANS_AGREE("transAgree","同意"),
	/**
	 * 反对
	 */
	TRANS_OPPOSE("transOppose","反对"),
	/**
	 * 跳过执行
	 */
	SKIP("skip","跳过执行"),
	/**
	 * 人工终止
	 */
	MANUAL_END("manual_end","人工终止");
	// 键
	private String key = "";
	// 值
	private String value = "";

	// 构造方法
	private OpinionStatus(String key, String value) {
		this.key = key;
		this.value = value;
	}

	// =====getting and setting=====
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return key;
	}

	/**
	 * 通过key获取对象
	 * 
	 * @param key
	 * @return
	 */
	public static OpinionStatus fromKey(String key) {
	for (OpinionStatus c : OpinionStatus.values()) {
			if (c.getKey().equalsIgnoreCase(key))
				return c;
		}
		throw new IllegalArgumentException(key);
	}
}

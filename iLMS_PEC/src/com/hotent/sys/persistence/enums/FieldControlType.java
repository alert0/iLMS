package com.hotent.sys.persistence.enums;

/**
 * 
 * <pre>
 * 描述：表单字段控件类型泛型
 * 构建组：x5-form-api
 * 作者：aschs
 * 邮箱:liyj@jee-soft.cn
 * 日期:2016年6月15日-上午9:01:09
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public enum FieldControlType {
	/**
	 * "onetext", "单行文本", new String[] { "varchar", "number" }
	 */
	ONETEXT("onetext", "单行文本", new String[] { "varchar", "number" }),
	/**
	 * "multitext", "多行文本", new String[] { "varchar", "clob" }
	 */
	MULTITEXT("multitext", "多行文本", new String[] { "varchar", "clob" }),
	/**
	 * "select", "下拉框", new String[] { "varchar", "number" }
	 */
	SELECT("select", "下拉框", new String[] { "varchar", "number" }),
	/**
	 * "multiselect", "下拉框多选", new String[] { "varchar"}
	 */
	MULTISELECT("multiselect", "下拉框多选", new String[] { "varchar" }),
	/**
	 * "checkbox", "复选框", new String[] { "varchar" }
	 */
	CHECKBOX("checkbox", "复选框", new String[] { "varchar" }),
	/**
	 * "radio", "单选按钮", new String[] { "varchar", "number" }
	 */
	RADIO("radio", "单选按钮", new String[] { "varchar", "number" }),
	/**
	 * "date", "日期控件", new String[] { "date" }
	 */
	DATE("date", "日期控件", new String[] { "date" }),
	/**
	 * "selector", "选择器", new String[] { "varchar" }
	 */
	SELECTOR("selector", "选择器", new String[] { "varchar" }),
	/**
	 * "dic", "数据字典", new String[] { "varchar", "number" }
	 */
	DIC("dic", "数据字典", new String[] { "varchar", "number" }),
	/**
	 * "identity", "流水号", new String[] { "varchar", "number" }
	 */
	IDENTITY("identity", "流水号", new String[] { "varchar", "number" }),
	/**
	 * "customdialog", "自定义对话框", new String[] { "varchar" }
	 */
	CUSTOMDIALOG("customdialog", "自定义对话框", new String[] { "varchar" }),
	/**
	 * "identity", "流水号", new String[] { "varchar", "number" }
	 */
	FILEUPLOAD("identity", "流水号", new String[] { "varchar", "number" });

	/**
	 * 值
	 */
	public String key;
	/**
	 * 描叙
	 */
	public String desc;
	/**
	 * 支持的数据库类型
	 */
	public String[] supports;

	@Override
	public String toString() {
		return this.key;
	}

	private FieldControlType(String key, String desc, String[] supports) {
		this.key = key;
		this.desc = desc;
		this.supports = supports;
	}

	/**
	 * 根据key获取泛型
	 * 
	 * @param key
	 * @return FieldControlType
	 * @exception
	 * @since 1.0.0
	 */
	public static FieldControlType fromKey(String key) {
		for (FieldControlType f : FieldControlType.values()) {
			if (f.key.equals(key)) {
				return f;
			}
		}
		throw new IllegalArgumentException(key);
	}

	public static void main(String[] args) {
		System.out.println(FieldControlType.CHECKBOX);
	}
}

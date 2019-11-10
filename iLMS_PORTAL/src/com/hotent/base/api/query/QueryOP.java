/**
 * 描述：TODO
 * 包名：com.hotent.base.api.query
 * 文件名：QueryOP.java
 * 作者：win-mailto:chensx@jee-soft.cn
 * 日期2014-1-3-上午11:17:00
 *  2014广州宏天软件有限公司版权所有
 * 
 */
package com.hotent.base.api.query;

/**
 * <pre> 
 * 描述：查询字段和值的操作类型枚举
 * 构建组：x5-base-api
 * 作者：Winston Yan
 * 邮箱：yancm@jee-soft.cn
 * 日期：2014-1-3-上午11:17:00
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public enum QueryOP {
	EQUAL("EQ","=","等于"),
	EQUAL_IGNORE_CASE("EIC","=","等于忽略大小写"),
	LESS("LT","<","小于"),
	GREAT("GT",">","大于"),
	LESS_EQUAL("LE","<=","小于等于"),
	GREAT_EQUAL("GE",">=","大于等于"),
	NOT_EQUAL("NE","!=","不等于"),
	LIKE("LK","like","相似"),
	LEFT_LIKE("LFK","like","左相似"),
	RIGHT_LIKE("RHK","like","右相似"),
	IS_NULL("ISNULL","is null","为空"),
	NOTNULL("NOTNULL","is not null","非空"),
	IN("IN","in","在...中"),
	BETWEEN("BETWEEN","between","在...之间");
	private String val;
	private String op;
	private String desc;
	QueryOP(String _val,String _op,String _desc){
		val = _val;
		op=_op;
		desc=_desc;
	}
	public String value(){
		return val;
	}
	public String op(){
		return op;
	}
	public String desc(){
		return desc;
	}
	/**
	 * 根据运算符获取QueryOp
	 * @param op
	 * @return 
	 * QueryOP
	 * @exception 
	 * @since  1.0.0
	 */
	public static QueryOP getByOP(String op){
		for(QueryOP queryOP:values()){
			if(queryOP.op().equals(op)){
				return queryOP;
			}
		}
		return null;
	}
	
	public static QueryOP getByVal(String val){
		for(QueryOP queryOP:values()){
			if(queryOP.val.equals(val)){
				return queryOP;
			}
		}
		return null;
	}
	
}

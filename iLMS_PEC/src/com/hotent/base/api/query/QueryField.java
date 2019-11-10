package com.hotent.base.api.query;

/**
 * 查询字段接口类。
 * <pre> 
 * 构建组：x5-base-api
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-11-28-上午8:48:27
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public interface QueryField extends WhereClause{
    /**
     * 返回字段名
     * @return
     */
    public String getField();
    /**
     * 比较符
     * @return
     */
    public QueryOP getCompare();
    /**
     * 返回值
     * @return
     */
    public Object getValue();
    
}

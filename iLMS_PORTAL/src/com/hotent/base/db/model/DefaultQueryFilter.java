package com.hotent.base.db.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hotent.base.api.Page;
import com.hotent.base.api.query.Direction;
import com.hotent.base.api.query.FieldLogic;
import com.hotent.base.api.query.FieldSort;
import com.hotent.base.api.query.QueryField;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.api.query.WhereClause;
import com.hotent.base.db.mybatis.domain.DefaultFieldSort;
import com.hotent.base.db.mybatis.domain.DefaultPage;


public class DefaultQueryFilter implements QueryFilter{
	/**
     * 分页组件
     */
    private Page page = new DefaultPage();
    /**
     * 排序字段
     */
    private List<FieldSort> fieldSortList = new ArrayList<FieldSort>();
    /**
     * 字段参数构建列表
     */
    private Map<String, Object> params = new LinkedHashMap<String, Object>();
    /**
     * 字段参数组合关系列表
     */
    private FieldLogic fieldLogic = new DefaultFieldLogic();
 

    public Page getPage() {
        return page;
    }

    @Override
    public void setPage(Page page) {
        this.page = page;
    }

    public Map<String, Object> getParams() {
    	initParams(this.fieldLogic);
    	return params;
    }

    public DefaultQueryFilter() {
    }

    public DefaultQueryFilter(FieldLogic fieldLogic) {
        this.fieldLogic = fieldLogic;
        //initParams(fieldLogic);
    }

    public FieldLogic getFieldLogic() {
        return fieldLogic;
    }

    public void setFieldLogic(FieldLogic fieldLogic) {
        this.fieldLogic = fieldLogic;
        //initParams(fieldLogic);
    }

    //初始化参数
    private void initParams(FieldLogic fedLog) {
        List<WhereClause> list = fedLog.getWhereClauses();
        for (WhereClause clause : list) {
            if (clause instanceof QueryField) {
                QueryField queryField = (QueryField) clause;
                if (QueryOP.IS_NULL.equals(queryField.getCompare())
                        || QueryOP.NOTNULL.equals(queryField.getCompare())) {
                    continue;
                }
                //如果查询字段包含数据库别名，参数设置去掉别名
                String fileNameString = queryField.getField();
                if(fileNameString.indexOf(".")> -1){
                	fileNameString = fileNameString.substring(fileNameString.indexOf(".")+1);
                }
                this.params.put(fileNameString, queryField.getValue());
            } else if (clause instanceof FieldLogic) {
                FieldLogic fdTemp = (FieldLogic) clause;
                initParams(fdTemp);
            }
        }
    }

    public List<FieldSort> getFieldSortList() {
        return fieldSortList;
    }

    public void setFieldSortList(List<FieldSort> fieldSortList) {
        this.fieldSortList = fieldSortList;
    }
    
    
    /**
     * 添加排序配置。
     * @param orderField	排序字段
     * @param orderSeq		排序
     */
    public void addFieldSort(String orderField,String orderSeq){
    	fieldSortList.add(new DefaultFieldSort(orderField, Direction.fromString(orderSeq)));
    }

	public void addFilter(String name, Object obj, QueryOP queryType) {
		fieldLogic.getWhereClauses().add(new DefaultQueryField(name, queryType, obj));
	}
	
	public void addParamsFilter(String key, Object obj) {
		this.params.put(key, obj);
	}

	/**
	 * 清空Page
	 * @author ZUOSL	
	 * @DATE	2018年9月3日 下午5:52:42
	 */
	@Override
	public void clearPage() {
		this.page = null;
	}
}

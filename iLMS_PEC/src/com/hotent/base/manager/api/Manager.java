package com.hotent.base.manager.api;
import java.io.Serializable;
import java.util.List;
import com.hotent.base.api.Page;
import com.hotent.base.api.query.QueryFilter;
/**
 * <pre> 
 * 描述：业务类管理实体类接口
 * 构建组：x5-base-db
 * 作者：csx
 * 邮箱:chensx@jee-soft.cn
 * 日期:2013-12-20-下午8:42:06
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public interface Manager<PK extends Serializable,T> {
	 /**
     * 创建实体对象
     * @param entity
     * @return 
     */
    public void create(T entity);
    /**
     * 更新实体对象
     * @param entity
     * @return 
     */
    public void update(T entity);
    /**
     * 按实体ID删除对象
     * @param entityId 
     */
    public void remove(PK entityId);
    
    /**
     * 按实体ID获取实体
     * @param entityId 
     */
    public T get(PK entityId);
    /**
     * 按实体ID获取实体
     * @param entityId 
     */
    public List<T> getByIds(List<PK> entityIds, String idColumn);
   
    /**
     * 按实体IDs删除记录
     * @param ids 
     */
    public void removeByIds(PK ...ids);
    /**
     * 查询实体对象
     * @param queryFilter
     * @return 
     */
    public List<T> query(QueryFilter queryFilter);
    /**
     * 查询单个实体对象
     * @param queryFilter
     * @return 
     */
    public T getUnique(String column, Object value);
    
    /**
     * 取得所有查询对象
     * @return 
     */
    public List<T> getAll();
    /**
     * 取得所有查询对象并分页查询
     * @param page
     * @return 
     */
    public List<T> getAllByPage(Page page);
}
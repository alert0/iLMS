package com.hotent.bo.api.instance;

import java.util.List;
import java.util.Map;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bo.api.model.BoData;
import com.hotent.bo.api.model.BoResult;

/**
 * bo实例数据处理器。
 * 这个可以有多个实现，目前实现了bo的和
 * @author ray
 *
 */
public interface BoDataHandler {
	
	/**
	 * 保存业务数据。
	 * @param id
	 * @param defId
	 * @param curData
	 * @return
	 */
	List<BoResult> save(String id,String defId,BoData curData) ;
	
	/**
	 * 根据实例ID和bo定义code获取BODATA，只返回两层。
	 * 1.根据bodefCode获取bo定义。
	 * 2.根据bo定义获取数据。
	 * @param instId
	 * @return
	 */
	BoData getById(Object id,String bodefCode);
	
	
	/**
	 * 根据实例ID和bo定义code获取BODATA，递归进行返回。
	 * 1.根据bodefCode获取bo定义。
	 * 2.根据bo定义获取数据。
	 * @param id
	 * @param bodefCode
	 * @return
	 */
	BoData getResById(Object id,String bodefCode);
	
	/**
	 * 根据bo定义code返回 bo数据对象实例。
	 * @param bodefCode
	 * @return
	 */
	BoData getByBoDefCode(String bodefCode);
	
	/**
	 * 存储方式。
	 * @return
	 */
	String saveType();
	/**
	 * 根据bo定义code删除 bo数据对象实例。
	 * @param bodefCode
	 * @param aryIds
	 * @return
	 */
	void removeBoData(String boCode, String[] aryIds);
	/**
	 * 主BO List数据 可以通过参数，
	 * @TODO 分页
	 * @param boCode boCode
	 * @param param 字段名：val  的形式对list数据进行过滤，可以为空
	 */
	List<Map<String,Object>> getList(String boCode, Map<String, Object> param);
	
	/**
	 * 主BO List数据 可以通过参数，
	 * @TODO 分页
	 * @param boCode boCode
	 * @param queryFilter
	 */
	PageList<Map<String, Object>> getList(String boCode, QueryFilter queryFilter);
	
}

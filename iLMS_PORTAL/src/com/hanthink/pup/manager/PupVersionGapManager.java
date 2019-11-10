package com.hanthink.pup.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.model.DictVO;
import com.hanthink.pup.model.PupVersionModel;
import com.hanthink.pup.model.PupVersionPageModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
/**
 * <pre> 
 * 功能描述:版本比对业务层接口类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月28日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface PupVersionGapManager extends Manager<String, PupVersionModel>{
	/**
	 * 订单数据差异分页查询业务层接口
	 * @param pageModel
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月28日
	 */
	PageList<PupVersionModel> queryVersionGapForPage(PupVersionPageModel pageModel, Page page)throws Exception;
	/**
	 * 加载数据字典差异标识接口
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月28日
	 */
	List<DictVO> getDiffFlag()throws Exception;
	/**
	 * 版本差异数据导出业务层接口
	 * @param pageModel
	 * @return
	 * @author zmj
	 * @date 2018年9月28日
	 */
	List<PupVersionModel> exportVersionGapForQuery(PupVersionPageModel pageModel) throws Exception;
	/**
	 * 导入版本数据业务层接口
	 * @param file
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月28日
	 */
	Map<String, Object> importVersion(MultipartFile file)throws Exception;

}

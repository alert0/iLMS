package com.hanthink.gps.gacne.sw.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.gps.gacne.sw.vo.LongOrderVo;

public interface LongOrderDao {

	List<LongOrderVo> getLongOrderSupplier(Map<String, Object> map);

	void updateEmailFlag(Map<String, Object> map);

	List<LongOrderVo> getWeekForecasetSupplier(Map<String, Object> map);

	List<LongOrderVo> getMonthForecasetSupplier(Map<String, Object> map);

	void updateMonthEmailFlag(Map<String, Object> map);

	void updateWeekEmailFlag(Map<String, Object> map);

	List<LongOrderVo> getExcepOrderSupplier(Map<String, Object> map);

	void updateExcepOrderMail(Map<String, Object> map);

	List<LongOrderVo> getAfterOrderSupplier(Map<String, Object> map);

	void updateAfterOrderMail(Map<String, Object> map);

	void updateNonOrderMail(Map<String, Object> map);

	List<LongOrderVo> getNonOrderSupplier(Map<String, Object> map);

}

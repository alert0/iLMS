package com.hanthink.gps.gacne.pecNon.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.gps.gacne.pecNon.vo.NonStandardVo;

public interface NonStandardDao {

	Integer getDateCount(String dateCount);

	List<NonStandardVo> getNonStandardCount(Map<String, Object> map);

	void updateEmailFlag(Map<String, Object> map);

}

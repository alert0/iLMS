package com.hanthink.gps.dpm.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.gps.dpm.vo.DpmInsVo;

public interface DpmInsDao {

	List<DpmInsVo> getDpmUserMail(Map<String, Object> map);

	List<DpmInsVo> getDpmNotSubmit(Map<String, Object> map);

}

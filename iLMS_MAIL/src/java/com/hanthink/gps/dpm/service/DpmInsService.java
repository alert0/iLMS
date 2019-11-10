package com.hanthink.gps.dpm.service;

import java.util.List;
import java.util.Map;

import com.hanthink.gps.dpm.vo.DpmInsVo;
import com.hanthink.gps.pub.service.BaseService;

public interface DpmInsService extends BaseService {

	List<DpmInsVo> getDpmUserMail(Map<String, Object> map);

	List<DpmInsVo> getDpmNotSubmit(Map<String, Object> map);

}

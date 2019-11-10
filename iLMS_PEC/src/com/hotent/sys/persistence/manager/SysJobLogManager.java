package com.hotent.sys.persistence.manager;


import java.io.Serializable;

import org.springframework.stereotype.Service;

import com.hotent.sys.api.job.IJobLogService;
import com.hotent.sys.api.model.SysJobLog;
import com.hotent.base.manager.api.Manager;


/**
 * 对象功能:SYS_JOBLOG Service类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:phl
 * 创建时间:2011-12-28 17:01:51
 */
@Service
public interface SysJobLogManager  <PK extends Serializable, SysJobLog> extends Manager<String,SysJobLog> ,IJobLogService
{
}

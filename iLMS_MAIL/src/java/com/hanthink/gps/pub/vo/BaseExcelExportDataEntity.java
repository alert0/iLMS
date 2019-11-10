package com.hanthink.gps.pub.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * JXLS的Excel导出用data entity
 * @since 2010-03-09
 * @author Zhangyingchun
 */
public abstract class BaseExcelExportDataEntity {

    /** 存储excel数据的data */
    private Map<String, Object> data = new HashMap<String, Object>();

    /**
     * 获得指定key对应的值
     * @param key String
     * */
    protected Object get(String key) {
        return data.get(key);
    }

    /**
     * 设置指定key对应的值
     * @param key String
     * @param value Object
     * */
    protected void set(String key, Object value) {
        data.put(key, value);
    }
}
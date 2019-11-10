package com.hanthink.gps.pub.vo;

import java.util.List;

/**
 * JXLS的EXCEL导出Entity
 * @since 2010-03-09
 * @author Zhangyingchun
 * */
public class BaseExcelExportEntity extends BaseVO{
	
	private static final long serialVersionUID = 2994384853233337564L;
	/** 设置用sheet的标题 */
    private String _title;    
    /** 设置用sheet的副标题 */
    private String _subTitle;
    /** excel导出的时间 */
    private String zhiBiaoRiQi;
    /** 存储检索用数据 */
    BaseExcelExportDataEntity _searchCond;
    /** 导出用数据的List */
    List<BaseVO> _tableData;
    /** 设置用excel文件名 */
    private String _downloadFileName;
    /** 最大件数的消息 */
    private String _maxCountMsg;
    /** 制表人 */
    private String creater;
    /**
     * 取得sheet的标题
     * @return sheet的标题
     */
    public String getTitle() {
        return _title;
    }

    /**
     * 设置sheet的标题
     * @param title sheet的标题
     */
    public void setTitle(String title) {
        _title = title;
    }

    /**
     * 设置sheet的副标题
     * @param subTitle sheet的副标题
     */
    public void setSubTitle(String subTitle) {
        _subTitle = subTitle;
    }

    /**
     * 取得sheet的副标题
     * @return sheet的副标题
     */
    public String getSubTitle() {
        return _subTitle;
    }

    /**
     * 取得导出用数据的List
     * @return 导出用数据的List
     */
    public List<BaseVO> getTableData() {
        return _tableData;
    }

    /**
     * 设置导出用数据的List 
     * @param tableData 导出用数据的List 
     */
    public void setTableData(List<BaseVO> tableData) {
        _tableData = tableData;
    }

    /**
     * 取得检索用数据
     * @return 检索用数据
     */
    public BaseExcelExportDataEntity getSearchCond() {
        return _searchCond;
    }

    /**
     * 设置检索用数据
     * @param searchCond 检索用数据
     */
    public void setSearchCond(BaseExcelExportDataEntity searchCond) {
        _searchCond = searchCond;
    }

    /**
     * 取得excel文件名
     * @return excel文件名
     */
    public String getDownloadFileName() {
        return _downloadFileName;
    }

    /**
     * 设置excel文件名
     * @param downloadFileName excel文件名
     */
    public void setDownloadFileName(String downloadFileName) {
        _downloadFileName = downloadFileName;
    }

    /**
     * 取得最大件数的消息
     * @return 最大件数的消息
     * */
    public String getMaxCountMsg() {
        return _maxCountMsg;
    }

    /**
     * 设置最大件数的消息
     * @param maxCountMsg 最大件数的消息
     * */
    public void setMaxCountMsg(String maxCountMsg) {
        _maxCountMsg = maxCountMsg;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getZhiBiaoRiQi() {
        return zhiBiaoRiQi;
    }

    public void setZhiBiaoRiQi(String zhiBiaoRiQi) {
        this.zhiBiaoRiQi = zhiBiaoRiQi;
    }
}
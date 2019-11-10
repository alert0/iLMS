package com.hanthink.gps.pub.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

import com.hanthink.gps.pub.vo.BaseExcelExportEntity;
import com.hanthink.gps.pub.vo.BaseVO;

/**
 * JXLS的EXCEL导出Action
 * */
public abstract class BaseExcelExportAction extends BaseActionSupport {

	private static final long serialVersionUID = 2611630555189638835L;

  
    @SuppressWarnings("unchecked")
	public final String ExcelExport(BaseVO vo){
        Map bean = new HashMap();
        // 获得要导出的信息的entity
        BaseExcelExportEntity entity = formatExcelExportEntity(getExcelExportEntity(
        		vo));
        entity.setCreater(userInfo.getName());
        Date now = new Date();
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy年MM月dd日");
        entity.setZhiBiaoRiQi(myFmt.format(now));
        // 将此entity放入excel导出map中
        bean.put("entity", entity);
        XLSTransformer transform = new XLSTransformer();
            // JXLS 读bean、写excel文件
            Workbook book = null;
			try {
				book = transform.transformXLS(getClass()
				        .getClassLoader().getResourceAsStream(
				                getExcelTemplateLocation(vo)), bean);
			} catch (ParsePropertyException e) {
				e.printStackTrace();
			} catch (InvalidFormatException e) {
				e.printStackTrace();
			}
            // 对EXCEL工作簿做其他处理
            doWorkWithWorkbook(book,vo);
            process(entity, book);
       return null;
    }

    /**
     * 对EXCEL工作簿做其他处理
     * 
     * @param book
     *            EXCEL工作簿
     * */
    protected void doWorkWithWorkbook(Workbook book, BaseVO vo) {

    }

    /**
     * 获得要导出的信息的EXCEL模板位置
     * 
     * @return EXCEL模板位置
     * */
    protected abstract String getExcelTemplateLocation(BaseVO vo);

    /**
     * 获得要导出的信息的entity(实际上是数据源从service取回来,经过处理后放入entity中)
     * 
     * @param HttpServletRequest
     *            req
     * @param ActionForm
     *            form
     * @return SSHMJXLSExcelExportEntity 信息的entity
     * */
    protected abstract BaseExcelExportEntity getExcelExportEntity(BaseVO vo);

    /**
     * 对entity做处理
     * 
     * @param entity
     *            JXLS的EXCEL导出用entity
     * */
    private BaseExcelExportEntity formatExcelExportEntity(
            BaseExcelExportEntity entity) {
        if (entity != null && entity.getTableData() != null
                && entity.getTableData().size() > 100000) {
            List<BaseVO> lstTableData = entity.getTableData();
            for (int index = lstTableData.size() - 1; index >= 100000; index--) {
                lstTableData.remove(index);
            }
        }
        return entity;
    }
}
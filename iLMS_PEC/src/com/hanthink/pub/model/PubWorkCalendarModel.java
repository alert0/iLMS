package com.hanthink.pub.model;
import java.util.List;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：生产工作日历表 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-06 00:31:49
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public class PubWorkCalendarModel extends AbstractModel<Integer>{
	
	/**  
	 * <p>Description: long</p>  
	 * @author linzhuo  
	 * @date 2018年9月27日
	 * @version 1.0  
	 */  
	private static final long serialVersionUID = 146710511966733061L;

	/**
	 * 主表  工作日历头表
	 */
	/**
	* 主键ID
	*/
	private Integer id; 
	
	/**
	* 工厂
	*/
	private String factoryCode; 
	
	/**
	* 车间
	*/
	private String workCenter; 
	
	/**
	* 班次
	*/
	private String shiftCode; 
	
	/**
	* 工作日
	*/
	private java.util.Date workDate; 
	
	/**
	* 工作日
	*/
	private String workDateStr; 
	
	/**
	* 工作日(开始）
	*/
	private String workDateStrStart; 
	
	/**
	* 工作日(结束)
	*/
	private String workDateStrEnd; 
	
	/**
	* 工作开始时间
	*/
	private java.util.Date workStartTime; 
	
	/**
	* 工作开始时间
	*/
	private String workStartTimeStr; 
	
	/**
	* 工作结束时间
	*/
	private java.util.Date workEndTime; 
	
	/**
	* 工作结束时间
	*/
	private String workEndTimeStr; 
	
	/**
	* 创建时间
	*/
	private java.util.Date creationTime; 
	
	/**
	* 最后修改时间
	*/
	private java.util.Date lastModifiedTime; 
	
	/**
	 * 副表  生产日历明细表
	 */
	/**
	* 工作日历唯一标识
	*/
	private Integer idWorkCalendar; 
	
	/**
	* 休息开始时间
	*/
	private java.util.Date startTime; 
	
	/**
	* 休息开始时间
	*/
	private String startTimeStr; 
	
	/**
	* 休息结束时间
	*/
	private java.util.Date endTime; 
	
	/**
	* 休息结束时间
	*/
	private String endTimeStr; 
	
	/**
	* 时间段类型
	*/
	private String timeType;
	
	/**
	 * 数据字典
	 */
	/**
	 * 数据值
	 */
	private String codeValueName;
	
	private String time;
	
	private List<PubWorkCalendarModel> restTimeList;
	
	private String sortId;
	
	/**
	 * 是否工作日渲染属性
	 */
	private String text;
	private String value;
	private String type;
	private String dClass;
	private String isWorkDay;
	private List<PubWorkCalendarModel> dayData;
	
	public List<PubWorkCalendarModel> getDayData() {
        return dayData;
    }

    public void setDayData(List<PubWorkCalendarModel> dayData) {
        this.dayData = dayData;
    }

    public String getIsWorkDay() {
        return isWorkDay;
    }

    public void setIsWorkDay(String isWorkDay) {
        this.isWorkDay = isWorkDay;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getdClass() {
        return dClass;
    }

    public void setdClass(String dClass) {
        this.dClass = dClass;
    }

    /**
	 * 主表 工作日历头表
	 */
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getWorkCenter() {
		return workCenter;
	}

	public void setWorkCenter(String workCenter) {
		this.workCenter = workCenter;
	}

	public String getShiftCode() {
		return shiftCode;
	}

	public void setShiftCode(String shiftCode) {
		this.shiftCode = shiftCode;
	}

	public java.util.Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(java.util.Date workDate) {
		this.workDate = workDate;
	}

	public String getWorkDateStr() {
		return workDateStr;
	}

	public void setWorkDateStr(String workDateStr) {
		this.workDateStr = workDateStr;
	}

	public String getWorkDateStrEnd() {
		return workDateStrEnd;
	}

	public void setWorkDateStrEnd(String workDateStrEnd) {
		this.workDateStrEnd = workDateStrEnd;
	}

	public java.util.Date getWorkStartTime() {
		return workStartTime;
	}
	
	public void setWorkStartTime(java.util.Date workStartTime) {
		this.workStartTime = workStartTime;
	}

	public String getWorkStartTimeStr() {
		return workStartTimeStr;
	}

	public void setWorkStartTimeStr(String workStartTimeStr) {
		this.workStartTimeStr = workStartTimeStr;
	}

	public java.util.Date getWorkEndTime() {
		return workEndTime;
	}

	public void setWorkEndTime(java.util.Date workEndTime) {
		this.workEndTime = workEndTime;
	}

	public String getWorkEndTimeStr() {
		return workEndTimeStr;
	}

	public void setWorkEndTimeStr(String workEndTimeStr) {
		this.workEndTimeStr = workEndTimeStr;
	}

	public java.util.Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(java.util.Date creationTime) {
		this.creationTime = creationTime;
	}

	public java.util.Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(java.util.Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	/**
	 * 副表 工作日历明细表
	 * <p>return: Integer</p>  
	 * <p>Description: PubWorkCalendarModel.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月27日
	 * @version 1.0
	 */
	public Integer getIdWorkCalendar() {
		return idWorkCalendar;
	}

	public void setIdWorkCalendar(Integer idWorkCalendar) {
		this.idWorkCalendar = idWorkCalendar;
	}

	public java.util.Date getStartTime() {
		return startTime;
	}

	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public java.util.Date getEndTime() {
		return endTime;
	}

	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	public String getWorkDateStrStart() {
		return workDateStrStart;
	}

	public void setWorkDateStrStart(String workDateStrStart) {
		this.workDateStrStart = workDateStrStart;
	}

	public String getCodeValueName() {
		return codeValueName;
	}

	public void setCodeValueName(String codeValueName) {
		this.codeValueName = codeValueName;
	}

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<PubWorkCalendarModel> getRestTimeList() {
        return restTimeList;
    }

    public void setRestTimeList(List<PubWorkCalendarModel> restTimeList) {
        this.restTimeList = restTimeList;
    }

    public String getSortId() {
        return sortId;
    }

    public void setSortId(String sortId) {
        this.sortId = sortId;
    } 

}
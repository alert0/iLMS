package com.hanthink.pub.model;
import com.hotent.base.core.model.AbstractModel;


 /**
  * 
  * @Desc    : W+3周Model
  * @CreateOn: 2019年1月4日
  * @author  : WY
  *
  * @ChangeList
  * Date			  Version		   Editor		     ChangeReasons
  *
  */
public class PubProPlanModel extends AbstractModel<Integer>{
	
	/**
	 * 焊装上线时间
	 */
	private String startPassTime;
	
	/**
	 * 焊装上线时间至
	 */
	private String endPassTime;
	
	/**
     * 
     */
    private static final long serialVersionUID = 3997561779769659364L;

    @Override
    public void setId(Integer id) {
        
    }

    @Override
    public Integer getId() {
        return null;
    }
    
    /**
     * 工厂
     */
    private String factoryCode;
    
    /**
     * 生产单号
     */
    private String orderNo;
    
    /**
     * 车辆类型
     */
    private String orderType;
    private String orderTypeStr;
    
    /**
     * 涂装上线时间
     */
    private String weOnTimeStr;
    
    /**
     * 总装下线时间
     */
    private String afOffTimeStr;
    
    /**
     * 查询条件 总装下线时间起始
     */
    private String afoffTimeStartStr;
    
    /**
     * 查询条件 总装下线时间结束
     */
    private String afoffTimeEndStr;
    
    /**
     * 生产排序号
     */
    private String sortId;
    
    /**
     * mtoc
     */
    private String mtoc;
    
    private String vin;
    
    /**
     * 车型
     */
    private String modelCode;
    
    private String carStatus;
    
    private String carStatusStr;
    
    private String passTime;
    
    private String stationCode;
    
    private String saleNo;
    
    private String saleRowNo;
    
    public String getCarStatusStr() {
		return carStatusStr;
	}

	public void setCarStatusStr(String carStatusStr) {
		this.carStatusStr = carStatusStr;
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getSaleNo() {
		return saleNo;
	}

	public void setSaleNo(String saleNo) {
		this.saleNo = saleNo;
	}

	public String getSaleRowNo() {
		return saleRowNo;
	}

	public void setSaleRowNo(String saleRowNo) {
		this.saleRowNo = saleRowNo;
	}

	public String getPassTime() {
		return passTime;
	}

	public void setPassTime(String passTime) {
		this.passTime = passTime;
	}

	public String getCarStatus() {
		return carStatus;
	}

	public void setCarStatus(String carStatus) {
		this.carStatus = carStatus;
	}

	/**
     * 阶段
     */
    private String phase;
    private String phaseStr;

    public String getFactoryCode() {
        return factoryCode;
    }

    public void setFactoryCode(String factoryCode) {
        this.factoryCode = factoryCode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getWeOnTimeStr() {
        return weOnTimeStr;
    }

    public void setWeOnTimeStr(String weOnTimeStr) {
        this.weOnTimeStr = weOnTimeStr;
    }

    public String getAfOffTimeStr() {
        return afOffTimeStr;
    }

    public void setAfOffTimeStr(String afOffTimeStr) {
        this.afOffTimeStr = afOffTimeStr;
    }

    public String getSortId() {
        return sortId;
    }

    public void setSortId(String sortId) {
        this.sortId = sortId;
    }

    public String getMtoc() {
        return mtoc;
    }

    public void setMtoc(String mtoc) {
        this.mtoc = mtoc;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getAfoffTimeStartStr() {
        return afoffTimeStartStr;
    }

    public void setAfoffTimeStartStr(String afoffTimeStartStr) {
        this.afoffTimeStartStr = afoffTimeStartStr;
    }

    public String getAfoffTimeEndStr() {
        return afoffTimeEndStr;
    }

    public void setAfoffTimeEndStr(String afoffTimeEndStr) {
        this.afoffTimeEndStr = afoffTimeEndStr;
    }

    public String getOrderTypeStr() {
        return orderTypeStr;
    }

    public void setOrderTypeStr(String orderTypeStr) {
        this.orderTypeStr = orderTypeStr;
    }

    public String getPhaseStr() {
        return phaseStr;
    }

    public void setPhaseStr(String phaseStr) {
        this.phaseStr = phaseStr;
    }

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getStartPassTime() {
		return startPassTime;
	}

	public void setStartPassTime(String startPassTime) {
		this.startPassTime = startPassTime;
	}

	public String getEndPassTime() {
		return endPassTime;
	}

	public void setEndPassTime(String endPassTime) {
		this.endPassTime = endPassTime;
	}
	
    
}
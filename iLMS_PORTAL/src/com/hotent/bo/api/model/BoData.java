package com.hotent.bo.api.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringPool;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.util.time.DateFormatUtil;
import com.hotent.bo.api.instance.BoUtil;

/**
 * BoData 描述了一个多层次的主从的数据结构。
 * <pre>
 * 描述：Data
 * 构建组：x5-bo-api
 * 作者：aschs
 * 邮箱:liyj@jee-soft.cn
 * 日期:2016年3月17日-下午3:14:54
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class BoData {
	
	/**
	 * 主表数据。
	 */
	private Map<String, Object> row = new HashMap<String, Object>();
	
	/**
	 * 子表数据。
	 */
	private Map<String, List<BoData>> subMap = new HashMap<String, List<BoData>>();
	
	/**
	 * 子表初始化数据。
	 * <pre>
	 *  键为：子表名称
	 *  值为：子表初始化数据。
	 * </pre>
	 */
	private Map<String,Map<String, Object>> initDataMap=new HashMap<String, Map<String,Object>>();
	
	private BaseBoEnt boEnt=null;
	
	/**
	 * 业务对象定义。
	 */
	private BaseBoDef boDef=null;
	
	
	public void setBoEnt(BaseBoEnt ent){
		this.boEnt=ent;
	}
	
	public BaseBoEnt getBoEnt(){
		return this.boEnt;
	}

	/**
	 * 设置主表值。
	 * @param key	键
	 * @param val	值
	 */
	public void set(String key,Object val){
		row.put(key, val);
	}
	
	/**
	 * 根据键获取主表字段值。
	 * @param key
	 * @return
	 */
	public Object getByKey(String key){
		return row.get(key);
	}
	
	/**
	 * 判断数据是否存在。
	 * @param key
	 * @return
	 */
	public boolean containKey(String key){
		return row.containsKey(key);
	}
	
	public boolean isAdd(){
		Map<String, Object> row = this.getData();
		String pk = boEnt.getPkKey().toLowerCase();
		
		if(row.containsKey(pk) && StringUtil.isNotEmpty(String.valueOf(row.get(pk)))){
			return false;
		}
		return true;
	}
	
	
	/**
	 * 使用BaseAttribute对数据进行处理。
	 * @return
	 * @throws ParseException
	 */
	public Object getValByKey(String key) throws ParseException {
		BaseAttribute attr= boEnt.getAttribute(key);
		Object obj=row.get(key);
		if(obj==null) return obj;
		String dataType = "";
		if(BeanUtils.isNotEmpty(attr)){
			dataType= attr.getDataType();
		}else{//当获取的字段可能是主键时
			dataType= boEnt.getPkType();
		}
		if(BaseAttribute.COLUMN_TYPE_VARCHAR.equals(dataType)  || BaseAttribute.COLUMN_TYPE_CLOB.equals(dataType)){
			return obj.toString();
		}
		else if(BaseAttribute.COLUMN_TYPE_NUMBER.equals(dataType)){
			String val=obj.toString();
			if(StringUtil.isEmpty(val)) return null;
			if(attr.getDecimalLen()==0){
				if(attr.getAttrLength()<=10){
					return Integer.parseInt(obj.toString());
				}
				else{
					return Long.parseLong(obj.toString());
				}
			}
			else{
				return Double.parseDouble(obj.toString());
			}
		}
		else if(BaseAttribute.COLUMN_TYPE_DATE.equals(dataType)){
			if(obj==null) return obj;
			String val=obj.toString();
			if(StringUtil.isEmpty(val)) return null;
			
			String format=attr.getFormat();
			if(StringUtil.isEmpty(format)){
				format=StringPool.DATE_FORMAT_DATETIME;
			}
			return DateFormatUtil.parse(obj.toString(), format);
		}
		return obj.toString();
	}
	
	
	/**
	 * 根据键值获取主对象的值。
	 * @param key
	 * @return
	 */
	public String getString(String key){
		Object obj= row.get(key);
		if(obj!=null){
			return obj.toString();
		}
		return "";
	}
	
	
	/**
	 * 获取一行数据。
	 * @return
	 */
	public Map<String, Object> getData(){
		return row;
	}
	
	/**
	 * 设置一行数据。
	 * @param row
	 */
	public void setData(Map<String, Object> row){
		this. row=row;
	}

	
	/**
	 * 根据键删除主表数据。
	 * @param key
	 */
	public void removeByKey(String key){
		row.remove(key);
	}
	
	/**
	 * 添加子表行数据。
	 * @param key
	 * @param data
	 */
	public void addSubRow(String key,BoData data){
		if(subMap.containsKey(key)){
			List<BoData> list=subMap.get(key);
			list.add(data);
		}
		else{
			List<BoData> list=new ArrayList<BoData>();
			list.add(data);
			subMap.put(key, list);
		}
		
	}
	
	/**
	 * 设置子表数据。
	 * @param key
	 * @param list
	 */
	public void setSubList(String key,List<BoData> list){
		subMap.put(key, list);
	}
	
	/**
	 * 根据子表key值获取数据。
	 * @param subKey
	 * @return
	 */
	public  List<BoData> getSubByKey(String subKey){
		return subMap.get(subKey);
	}
	
	
	/**
	 * 根据子表key删除数据。
	 * @param key
	 */
	public void removeSub(String key){
		subMap.remove(key);
	}
	
	/**
	 * 获取子实例map数据。
	 * @return
	 */
	public Map<String, List<BoData>> getSubMap(){
		return subMap;
	}

	/**
	 * 获取初始化对象Map。
	 * @return
	 */
	public Map<String, Map<String, Object>> getInitDataMap() {
		return initDataMap;
	}

	/**
	 * 设置自对象初始化值。
	 * @param initDataMap
	 */
	public void setInitDataMap(Map<String, Map<String, Object>> initDataMap) {
		this.initDataMap = initDataMap;
	}
	
	/**
	 * 添加子表初始化行数据。
	 * @param key
	 * @param initRow
	 */
	public void addInitDataMap(String key,Map<String, Object> initRow){
		this.initDataMap.put(key, initRow);
	}
	
	/**
	 * 单个设置初始化数据。
	 * @param key			表名
	 * @param fieldName		字段名
	 * @param val			表单
	 */
	public void setInitData(String key,String fieldName,Object val){
		if(this.initDataMap.containsKey(key)){
			Map<String, Object> row=this.initDataMap.get(key);
			row.put(fieldName, val);
		}
		else{
			Map<String, Object> row=new HashMap<String, Object>();
			row.put(fieldName,val);
			this.initDataMap.put(key, row);
		}
	}
	

	/**
	 * 获取当前的BO对象。
	 * @return
	 */
	@JSONField(serialize=false)
	public BaseBoDef getBoDef() {
		return boDef;
	}

	public void setBoDef(BaseBoDef boDef) {
		this.boDef = boDef;
	}

	@Override
	public String toString() {
		return BoUtil.toJSONObject(this,true).toJSONString();
	}



	public static void main(String[] args) {
		
		Map<String,Object> row=new HashMap<String, Object>();
		row.put("name", "ray");
		
		BoData bdo = new BoData();
		bdo.set ("id", "1");
		bdo.set("name", "洛亚");

		BoData bdo1 = new BoData();
		bdo1.set("name", "奥丽娜");

		BoData bdo21 = new BoData();
		bdo21.set("name", "雷克斯");
		bdo21.set("sex", "男");
		BoData bdo22 = new BoData();
		bdo22.set("name", "尤雅");
		bdo21.addSubRow("lover", bdo22);
		bdo21.addInitDataMap("lover", row);
		
		
		bdo.addSubRow("lover", bdo1);
		
		bdo.addInitDataMap("lover",row);
		bdo.addSubRow("friends", bdo21);
		bdo.addInitDataMap("friends", row);
		bdo.addSubRow("friends", bdo22);
		bdo.addSubRow("friends", bdo1);

		System.out.println(bdo);
		
		// System.out.println(transJSON(bdo.toJSONObject()));
	}
}

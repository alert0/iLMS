package com.hotent.org.persistence.manager.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.encrypt.EncryptUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.PinyinUtil;
import com.hotent.base.core.util.ThreadMsgUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.org.api.model.IUser;
import com.hotent.org.persistence.dao.OrgRelDao;
import com.hotent.org.persistence.dao.OrgUserDao;
import com.hotent.org.persistence.dao.SysUserParamsDao;
import com.hotent.org.persistence.dao.UserDao;
import com.hotent.org.persistence.dao.UserRoleDao;
import com.hotent.org.persistence.manager.OrgManager;
import com.hotent.org.persistence.manager.OrgRelManager;
import com.hotent.org.persistence.manager.OrgReldefManager;
import com.hotent.org.persistence.manager.OrgUserManager;
import com.hotent.org.persistence.manager.SysDemensionManager;
import com.hotent.org.persistence.manager.UserManager;
import com.hotent.org.persistence.manager.UserRoleManager;
import com.hotent.org.persistence.model.Org;
import com.hotent.org.persistence.model.OrgRel;
import com.hotent.org.persistence.model.OrgReldef;
import com.hotent.org.persistence.model.OrgUser;
import com.hotent.org.persistence.model.SysDemension;
import com.hotent.org.persistence.model.SysUser;
import com.hotent.org.persistence.model.User;
import com.hotent.org.persistence.model.UserRole;

/**
 * 
 * <pre> 
 * 描述：用户表 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-30 10:26:50
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("userManager")
public class UserManagerImpl extends AbstractManagerImpl<String, User> implements UserManager{
	protected static Logger logger = LoggerFactory.getLogger(UserManagerImpl.class);
	public final static int STATUS_NORMAL = 1;
	public final static int STATUS_DISABLED = 0;
	public final static int STATUS_NOT_ACTIVE = -1;
	public final static int STATUS_LEAVE = -2;
	@Resource
	UserDao userDao;
	@Resource
	UserRoleDao userRoleDao;
	@Resource
	OrgUserDao orgUserDao;
	@Resource
	SysUserParamsDao sysUserParamsDao;
	@Resource
	OrgManager orgManager;
	@Resource
	OrgRelManager orgRelManager;
	@Resource
	OrgReldefManager orgReldefManager;
	@Resource
	OrgRelDao orgRelDao;
	@Resource
	SysDemensionManager sysDemensionManager;
	@Resource
	UserRoleManager userRoleManager;
	@Resource
	OrgUserManager orgUserManager;
	@Override
	protected Dao<String, User> getDao() {
		return userDao;
	}
	public User getByAccount(String account) {
		SysUser user= (SysUser) this.userDao.getByAccount(account);
	    return user;
	}
	/**
	 * 不含组织用户关系数据
	 */
	public List<User> queryOrgUser(QueryFilter queryFilter) {
		return userDao.queryOrgUser(queryFilter);
	}
	/**
	 * 不含组织用户关系数据
	 */
	public List<User> getUserListByOrgId(String orgId) {
		return userDao.getUserListByOrgId(orgId);
 
	}
	

	/**
	 * 含组织用户关系数据
	 */
	public List<User> getOrgUserRelByOrgId(String orgId) {
		return userDao.getOrgUserRelByOrgId(orgId);
	}
	/**
	 * 含组织用户关系数据
	 */
	public List queryOrgUserRel(QueryFilter queryFilter) {
		return userDao.queryOrgUserRel(queryFilter);
	}
	
	public List<User> getListByRelCode(String relCode) {
		return userDao.getListByRelCode(relCode);
	}
	
	public List<User> getListByRelId(String relId) {
		return userDao.getListByRelId(relId);
	}
	public List<User> getUserListByRoleId(String roleId) {
		return userDao.getUserListByRoleId(roleId);
	}
	public List<User> getUserListByRoleCode(String roleCode) {
		return userDao.getUserListByRoleCode(roleCode);
	}
	@Override
	public boolean isUserExist(User user) {
		return userDao.isUserExist(user);
	}
	
	@Override
	public void removeByIds(String... ids) {
		IUser user = userDao.getByAccount("admin");
		for (String id : ids) {
			if(id.equals(user.getUserId())){
				ThreadMsgUtil.addMsg(ids.length>=2?"亲，admin账号惹不起的,其它用户删除成功。":"亲，admin账号惹不起的。");
				break;
			}
		}
		if(StringUtil.isEmpty(ThreadMsgUtil.getMessage(false))){
			ThreadMsgUtil.addMsg("用户删除成功。");
		}
		super.removeByIds(ids);
		orgUserDao.removeByUserIds(ids);
		userRoleDao.removeByUserIds(ids);
		sysUserParamsDao.removeByUserIds(ids);
	}
	
	@Override
	public List<User> getByUserEmail(String email) {
		return userDao.getByUserEmail(email);
	}
	@Override
	public Map<String, Object> importUser(MultipartFile file, String demId)
			throws Exception {

		Map<String,Object> rtnMap = new HashMap<String, Object>();
		Boolean result = true;
		String preCode = "";
		String console = "";
		SysDemension sysDemension = sysDemensionManager.get(demId);
		if(BeanUtils.isEmpty(sysDemension)){
			console = "未选择导入维度";
			throw new RuntimeException(console);
		}
		preCode = sysDemension.getDemCode();
		JSONArray logArray = new JSONArray();
		if(file == null || file.isEmpty()){
			result = false;
			console = "文件为空！";
			throw new RuntimeException(console);
		}
		
		String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
		if(!fileExt.toUpperCase().equals(".XLS") && !fileExt.toUpperCase().equals(".XLSX")){
			result = false;
			console = "上传文件不是excel类型！";
			throw new RuntimeException(console);
		}
		
		Boolean isExcel2003 = true;
		if(fileExt.toUpperCase().equals(".XLSX")){
			isExcel2003 = false;
		}
		Workbook wb = null;
		if(isExcel2003){
			 wb = new HSSFWorkbook(file.getInputStream());
		}else{
			wb = new XSSFWorkbook(file.getInputStream());
		}
		
		
		
		//新增用户、组织列表
		List<Org> orgList = new ArrayList<Org>();
		List<User> userList = new ArrayList<User>();
		Map<String,User> updUserList = new HashMap<String,User>();//更新用户组
		List<OrgUser> orgUserList = new ArrayList<OrgUser>();//用户组织关系
		List<OrgRel> orgPostList = new ArrayList<OrgRel>();//岗位列表
		List<OrgReldef> orgJobList = new ArrayList<OrgReldef>();//职务列表
		Map<String,String> orgMap = new HashMap<String,String>();
		Map<String,String> orgPostAddMap = new HashMap<String,String>();
		Map<String,OrgRel> orgPostMapList = new HashMap<String,OrgRel>();//从待添加的数据中获取用
		Map<String,String> userMap = new HashMap<String,String>();
		Map<String,String> isMasterMap = new HashMap<String,String>();
		
		int sheets = wb.getNumberOfSheets();
		logger.error("开始导入");
		for(int s=0;s<sheets;s++){
			try {
				Sheet sheet = wb.getSheetAt(s);
				if(sheet.getPhysicalNumberOfRows() < 2){
					logger.error("sheet"+(s+1)+"没有填写数据!");
					System.out.println("sheet"+(s+1)+"没有填写数据!");
					continue;
				}
				Map<String,Integer> headMap = new HashMap<String,Integer>();
				for(int i=0;i<sheet.getRow(0).getLastCellNum();i++){
					Row row = sheet.getRow(0);
					headMap.put(row.getCell(i).getStringCellValue().trim(), i);
				}
				int begin = sheet.getFirstRowNum();  
				int end = sheet.getLastRowNum();  
				//处理excel表中的数据
				for(int i=begin+1; i<=end; i++){// 从第二行开始读取数据
					try {
						Row row = sheet.getRow(i);
						if(BeanUtils.isEmpty(row)){
							logger.error("Excel表格sheet"+(s+1)+"第"+(i+1)+"行数据为空，未导入");
							recordLog(logArray, "Excel表格sheet"+(s+1)+"第"+(i+1)+"行数据为空，未导入");
							continue;
						}
						Org currentOrg = null;//当前行所指向的组织
						
						//用户信息
						if(BeanUtils.isEmpty(headMap.get("帐号"))){
							logger.error("Excel表格sheet"+(s+1)+"第"+(i+1)+"行帐号为空，未导入");
							recordLog(logArray,"Excel表格sheet"+(s+1)+"第"+(i+1)+"行帐号为空，未导入");
							continue;
						}
						Cell accountCell = row.getCell(headMap.get("帐号"));
						if(BeanUtils.isEmpty(accountCell)||StringUtil.isEmpty(accountCell.getStringCellValue())){
							logger.error("Excel表格sheet"+(s+1)+"第"+(i+1)+"行帐号为空，未导入");
							recordLog(logArray,"Excel表格sheet"+(s+1)+"第"+(i+1)+"行帐号为空，未导入");
							continue;
						}
						String userAccount = accountCell.getStringCellValue().trim().toLowerCase();
						if(userAccount.trim().length()>30){
							logger.error("Excel表格sheet"+(s+1)+"第"+(i+1)+"行帐号长度大于30，未导入");
							recordLog(logArray,"Excel表格sheet"+(s+1)+"第"+(i+1)+"行帐号长度大于30，未导入");
							continue;
						}
						String userName = "";//姓名
						if(headMap.get("姓名")!=null&&BeanUtils.isNotEmpty(row.getCell(headMap.get("姓名")))
								&&StringUtil.isNotEmpty(row.getCell(headMap.get("姓名")).getStringCellValue())){
							userName = row.getCell(headMap.get("姓名")).getStringCellValue().trim();
						}else{
							logger.error("Excel表格sheet"+(s+1)+"第"+(i+1)+"行姓名为空，未导入");
							recordLog(logArray,"Excel表格sheet"+(s+1)+"第"+(i+1)+"行姓名为空，未导入");
							continue;
						}
						if(userName.trim().length()>30){
							logger.error("Excel表格sheet"+(s+1)+"第"+(i+1)+"行姓名长度大于30，未导入");
							recordLog(logArray,"Excel表格sheet"+(s+1)+"第"+(i+1)+"行姓名长度大于30，未导入");
							continue;
						}
						
						if(!checkSameUser(userAccount,userName,userMap)){
							logger.error("Excel表格sheet"+(s+1)+"第"+(i+1)+"行帐号已存在。");
							recordLog(logArray,"Excel表格sheet"+(s+1)+"第"+(i+1)+"行帐号已存在。");
							continue;
						}
						
						if(BeanUtils.isNotEmpty(headMap.get("组织单元名称"))&&BeanUtils.isNotEmpty(row.getCell(headMap.get("组织单元名称")))){
							String orgInfo = row.getCell(headMap.get("组织单元名称")).getStringCellValue().trim();//组织单元名
							int subIndex = 1;
							if(!orgInfo.startsWith("/")){
								subIndex = 0;
							}
							String[] orgNameArr = orgInfo.substring(subIndex, orgInfo.length()).split("/");
							Map<String,String> pathNameMap = new HashMap<String,String>();
							for(int j = 0;j<=orgNameArr.length-1;j++){
								if(j == 0){
									pathNameMap.put("父级路径", "");
									pathNameMap.put("子级路径", "/"+orgNameArr[j]);
								}else{
									pathNameMap.put("父级路径", pathNameMap.get("子级路径"));
									pathNameMap.put("子级路径", pathNameMap.get("子级路径")+"/"+orgNameArr[j]);
								}
								List<Org> selectOrgList = orgManager.getByPathName(pathNameMap.get("子级路径"));
								Org nowOrg = null;
								if(BeanUtils.isNotEmpty(selectOrgList)){
									for (Org org : selectOrgList) {
										if(demId.equals(org.getDemId())){
											nowOrg = org;
											break;
										}
									}
								}
								if(BeanUtils.isEmpty(nowOrg)){//为空，考虑新增组织
									if(j==0){
										Org org = new Org();
										org.setId(UniqueIdUtil.getSuid());
										org.setName(orgNameArr[j]);
										org.setDemId(demId);
										org.setParentId("0");
										org.setPathName(pathNameMap.get("子级路径"));
										org.setPath(demId+"."+org.getId()+".");
										org.setCode(preCode+"_"+PinyinUtil.getPinYinHeadChar(orgNameArr[j]).replace("-", "_"));
										currentOrg = dealOrg(org,orgList,preCode,orgMap);
									}else{
										currentOrg = dealOrgUnder(pathNameMap.get("父级路径"),pathNameMap.get("子级路径"),orgList,demId,preCode,orgMap);
									}
								}else{
									currentOrg = nowOrg;
								}
							}
						}
						
						String sex = "";
						if(headMap.get("性别")!=null&&BeanUtils.isNotEmpty(row.getCell(headMap.get("性别")))
								&&StringUtil.isNotEmpty(row.getCell(headMap.get("性别")).getStringCellValue())){
							sex = row.getCell(headMap.get("性别")).getStringCellValue().trim();
							if(StringUtil.isNotEmpty(sex)&&!"男".equals(sex)&&!"女".equals(sex)&&!"未知".equals(sex)){
								sex = "未知";
							}
						}
						String userStatus = "";//员工状态
						if(headMap.get("员工状态")!=null&&BeanUtils.isNotEmpty(row.getCell(headMap.get("员工状态")))){
							row.getCell(headMap.get("员工状态")).setCellType(Cell.CELL_TYPE_STRING);
							userStatus = row.getCell(headMap.get("员工状态")).getStringCellValue().trim();
						}
						
						
						String email = "";
						if(headMap.get("邮箱")!=null&&row.getCell(headMap.get("邮箱")) != null){
							row.getCell(headMap.get("邮箱")).setCellType(Cell.CELL_TYPE_STRING);
							email = row.getCell(headMap.get("邮箱")).getStringCellValue();
							if(StringUtil.isNotEmpty(email)){
								email = email.toLowerCase();
							}
							if(!checkEmail(email)){
								logger.error("Excel表格sheet"+(s+1)+"第"+(i+1)+"行邮箱格式不正确，未导入邮箱："+email);
								recordLog(logArray,"Excel表格sheet"+(s+1)+"第"+(i+1)+"行邮箱格式不正确，未导入邮箱："+email);
								email = "";
							}
						}
						
						String mobile = "";
						
						if(headMap.get("手机号码")!=null&&row.getCell(headMap.get("手机号码")) != null){
							row.getCell(headMap.get("手机号码")).setCellType(Cell.CELL_TYPE_STRING);
							mobile = row.getCell(headMap.get("手机号码")).getStringCellValue();
							if(StringUtil.isNotEmpty(mobile))
							if(!(org.apache.commons.lang.StringUtils.isNumeric(mobile)&&mobile.trim().length()==11)){
								logger.error("Excel表格sheet"+(s+1)+"第"+(i+1)+"行手机号码格式不正确，未导入手机号码",mobile);
								recordLog(logArray,"Excel表格sheet"+(s+1)+"第"+(i+1)+"行手机号码格式不正确，未导入手机号码");
								mobile = "";
							}
						}
						
						String isMaster = "";//主组织 ：“1”或者“是”为主组织 其他为非主组织
						if(headMap.get("是否主组织")!=null&&row.getCell(headMap.get("是否主组织")) != null){
							row.getCell(headMap.get("是否主组织")).setCellType(Cell.CELL_TYPE_STRING);
							isMaster = row.getCell(headMap.get("是否主组织")).getStringCellValue();
						}
						
						String isCharge = "";//负责人：0或空为非负责人；“1”为负责人；“2”为主负责人
						if(headMap.get("部门负责人")!=null&&row.getCell(headMap.get("部门负责人")) != null){
							row.getCell(headMap.get("部门负责人")).setCellType(Cell.CELL_TYPE_STRING);
							isCharge = row.getCell(headMap.get("部门负责人")).getStringCellValue();
						}
						
						String address = "";
						if(headMap.get("地址")!=null&&row.getCell(headMap.get("地址")) != null){
							address = row.getCell(headMap.get("地址")).getStringCellValue();
						}
						
						
						
						//岗位和职务导入规则：一一对应导入，多个用;号隔开，如果没有职务，则不导入岗位，如果有职务，则对应顺序以岗位为准（第一个岗位对应第一个职务，以此类推）(如果岗位有多个，职务只有一个时，导入同一个职务中)
						//职务  如果此行数据存在岗位，那么职务也一定是存在的，因为岗位必须与职务关联，若只有岗位而无职务，那必是人为破坏
						List<OrgReldef> currentReldefs = null;
						if(currentOrg!=null&&headMap.get("职务")!=null&&row.getCell(headMap.get("职务")) != null){
							currentReldefs = dealOrgJob(row.getCell(headMap.get("职务")).getStringCellValue(),orgJobList,preCode);
						}
						
						//岗位   
						List<OrgRel> rels = new ArrayList<OrgRel>();
						if(currentOrg!=null&&headMap.get("岗位")!=null&&row.getCell(headMap.get("岗位")) != null && BeanUtils.isNotEmpty(currentReldefs) && StringUtil.isNotEmpty(row.getCell(headMap.get("岗位")).getStringCellValue())){//在职务存在时，才处理岗位
							String relNameStr = row.getCell(headMap.get("岗位")).getStringCellValue();
							String[] relNames = relNameStr.split(";");
							boolean isOneRelDef = currentReldefs.size()==1?true:false;
							for (int j = 0; j < relNames.length; j++) {
								String relId = UniqueIdUtil.getSuid();
								String relCode = PinyinUtil.getPinYinHeadChar(relNames[j]);
								OrgRel rel = orgRelManager.getByCode(currentOrg.getCode()+"_"+relCode);
								if(BeanUtils.isNotEmpty(rel)&&!rel.getRelName().equals(relNames[j])){
									rel = getByRelNameAndCode(relNames[j],currentOrg.getCode()+"_"+relCode);
									if(BeanUtils.isEmpty(rel)){
										relCode += "_"+relId.substring(relId.length()-3);
									}
								}
								OrgReldef orgJob = isOneRelDef?currentReldefs.get(0):currentReldefs.get(j);
								if(isOneRelDef||(j<currentReldefs.size()&&BeanUtils.isNotEmpty(orgJob))){
									if(BeanUtils.isEmpty(rel)){
										rel = new OrgRel();
										rel.setId(relId);
										rel.setRelDefId(orgJob.getId());
										rel.setOrgId(currentOrg.getId());
										rel.setRelCode(relCode);
										rel.setRelName(relNames[j]);
										String code = currentOrg.getCode()+"_"+rel.getRelCode();
										OrgRel rel2 = orgPostMapList.get(code);//从待添加的数据中获取
										if(BeanUtils.isEmpty(rel2)){
											rels.add(rel);
											orgPostAddMap.put(rel.getId(), code);
											orgPostMapList.put(code, rel);
										}else{
											if(!rel.getName().equals(rel2.getName())){
												relCode += "_"+relId.substring(relId.length()-3);
												rel.setRelCode(relCode);
												rels.add(rel);
												orgPostAddMap.put(rel.getId(), code);
												orgPostMapList.put(code, rel);
											}else{
												rel2.setRelCode(code);
												rels.add(rel2);
											}
										}
									}else{
										rels.add(rel);
									}
								}
							}
						}
						
						User user = userDao.getByAccount(userAccount);
						if(BeanUtils.isEmpty(user)){
							user = findUserFromNews(userList, userAccount);
							if(BeanUtils.isEmpty(user)){
								user = new User();
								user.setAccount(userAccount.toLowerCase());
								user.setFullname(userName);
								user.setAddress(address);
								user.setSex(sex);
								//user.setStaffStatus(userStatus);
								if("在职".equals(userStatus)||StringUtil.isEmpty(userStatus)){
									user.setStatus(STATUS_NORMAL);
								}else if("离职".equals(userStatus)){
									user.setStatus(STATUS_LEAVE);
								}else if("未激活".equals(userStatus)){
									user.setStatus(STATUS_NOT_ACTIVE);
								}else{
									user.setStatus(STATUS_DISABLED);
								}
								user.setEmail(email);
								user.setMobile(mobile);
								user.setPassword(EncryptUtil.encryptSha256("123456"));
								user.setCreateTime(new Date());
							}
						}else{
							if(!user.getFullname().equals(userName)){
								logger.error("Excel表格sheet"+(s+1)+"第"+(i+1)+"行帐号已存在。");
								recordLog(logArray,"Excel表格sheet"+(s+1)+"第"+(i+1)+"行帐号已存在。");
							}else{//更新用户
								user.setFullname(userName);
								user.setAddress(address);
								user.setSex(sex);
								if("在职".equals(userStatus)||StringUtil.isEmpty(userStatus)){
									user.setStatus(1);
								}else if("离职".equals(userStatus)){
									user.setStatus(STATUS_LEAVE);
								}else if("未激活".equals(userStatus)){
									user.setStatus(STATUS_NOT_ACTIVE);
								}else{
									user.setStatus(STATUS_DISABLED);
								}
								user.setEmail(email);
								user.setMobile(mobile);
								updUserList.put(user.getAccount(), user);
							}
						}
						try {
							user = dealUser(user,userList);
							userMap.put(user.getAccount(), user.getFullname());
						} catch (Exception e) {e.printStackTrace();}
						
						//用户、组织、岗位关系处理
						try {
							dealUserOrgPost(user,currentOrg,rels,orgUserList,orgPostList,orgPostAddMap,isMaster,isCharge,isMasterMap,sysDemension.getId());
						} catch (Exception e) {}
					} catch (Exception e) {
						System.out.println(e);
					}
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		orgPostMapList = null;
		orgMap = null;
		//记录新增失败的组织编号
		Set<String> failedOrgs = new HashSet<String>();
		//新增用户
		for(User u : userList){
			try {
				u.setCreateTime(new Date());
				this.create(u);
				//给新增用户添加默认角色
				bindRole(u);
				logger.error("导入人员【"+u.getAccount()+"】成功！");
			} catch (Exception e) {
				logger.error("导入人员【"+u.getAccount()+"】失败："+e.getMessage());
				recordLog(logArray,"导入人员【"+u.getAccount()+"】失败："+e.getMessage());
			}
			
		}
		//新增组织
		for(Org o : orgList){
			try {
				if(BeanUtils.isEmpty(orgManager.getByCode(o.getCode()))){
					if(!failedOrgs.contains(o.getParentId())){
						orgManager.create(o);
						logger.error("导入组织【"+o.getName()+"】成功！");
					}else{
						failedOrgs.add(o.getId());
						logger.error("导入组织【"+o.getName()+"】失败（该组织的父组织导入失败）。");
						recordLog(logArray,"导入组织【"+o.getName()+"】失败（该组织的父组织导入失败）。");
					}
				}else{
					failedOrgs.add(o.getId());
					logger.error("导入组织【"+o.getName()+"】失败，该组织编号【"+o.getCode()+"】已存在！");
					recordLog(logArray,"导入组织【"+o.getName()+"】失败，该组织编号【"+o.getCode()+"】已存在！");
				}
			} catch (Exception e) {
				failedOrgs.add(o.getId());
				logger.error("导入组织【"+o.getName()+"："+JSONObject.toJSONString(o)+"】失败："+e.getMessage());
				recordLog(logArray,"导入组织【"+o.getName()+"："+JSONObject.toJSONString(o)+"】失败。");
			}
		}
		
		//更新用户
		for(User u : updUserList.values()){
			try {
				this.update(u);
				// 绑定默认角色
	            bindRole(u);
				logger.error("更新人员【"+u.getAccount()+"】成功！");
			} catch (Exception e) {
				logger.error("更新用户【"+u.getAccount()+"】失败："+e.getMessage());
				recordLog(logArray,"更新用户【"+u.getAccount()+"】失败。");
			}
		}
		
		//新增岗位
		for(OrgRel rel :orgPostList){
			try {
				if(BeanUtils.isEmpty(orgRelManager.getByCode(rel.getRelCode()))){
					if(!failedOrgs.contains(rel.getOrgId())){
						orgRelManager.create(rel);
						logger.error("导入岗位【"+rel.getName()+"】成功！");
					}else{
						logger.error("导入岗位【"+rel.getName()+"】失败（该岗位所在组织导入失败）！");
						recordLog(logArray,"导入岗位【"+rel.getName()+"】失败（该岗位所在组织导入失败）！");
					}
				}else{
					logger.error("新增岗位【"+rel.getName()+"】失败，该岗位编号【"+rel.getRelCode()+"】已存在！");
					recordLog(logArray,"新增岗位【"+rel.getName()+"】失败，该岗位编号【"+rel.getRelCode()+"】已存在！");
				}
			} catch (Exception e) {
				logger.error("新增岗位【"+rel.getName()+"："+JSONObject.toJSONString(rel)+"】失败："+e.getMessage());
				recordLog(logArray,"新增岗位【"+rel.getName()+"】失败。");
			}
		}
		
		//新增职务
		for(OrgReldef reldef:orgJobList){
			try {
				orgReldefManager.create(reldef);
				logger.error("导入职务【"+reldef.getName()+"】成功！");
			} catch (Exception e) {
				logger.error("新增职务【"+reldef.getName()+"："+JSONObject.toJSONString(reldef)+"】失败："+e.getMessage());
				recordLog(logArray,"新增职务【"+reldef.getName()+"】失败。");
			}
		}
		
		//用户组织关系
		for(OrgUser orgUser :orgUserList){
			try {
				if(!failedOrgs.contains(orgUser.getOrgId())){
					orgUserManager.create(orgUser);
					logger.error("新增用户组织关系【"+JSONObject.toJSONString(orgUser)+"】成功！");
				}else{
					logger.error("新增用户组织关系【"+JSONObject.toJSONString(orgUser)+"】失败（组织导入失败）！");
				}
			} catch (Exception e) {
				logger.error("新增用户组织关系【"+JSONObject.toJSONString(orgUser)+"】失败："+e.getMessage());
				recordLog(logArray,"新增用户组织关系【"+JSONObject.toJSONString(orgUser)+"】失败。");
			}
		}
		if(BeanUtils.isEmpty(userList)&&BeanUtils.isEmpty(orgList)&&BeanUtils.isEmpty(updUserList)
			&&BeanUtils.isEmpty(orgPostList)&&BeanUtils.isEmpty(orgJobList)&&BeanUtils.isEmpty(orgUserList)){
			result = false;
			console = "未导入或更新任何用户、组织信息！";
			recordLog(logArray,"未导入或更新任何用户、组织信息！");
		}
		logger.error("导入完成");
		rtnMap.put("result", result);
		rtnMap.put("console", console);
		rtnMap.put("log", logArray);
		return rtnMap;
	}
	
	/**
	 * 处理excel中的职务
	 * @param reldefName 职务名称
	 * @param orgJobList 新增职务列表
	 * @return
	 */
	public List<OrgReldef> dealOrgJob(String reldefNameStr,List<OrgReldef> orgJobList,String preCode){
		if(StringUtil.isEmpty(reldefNameStr)) return null;
		String[] reldefNames = reldefNameStr.split(";");
		List<OrgReldef> rtn = new ArrayList<OrgReldef>();
		for (String reldefName : reldefNames) {
			OrgReldef reldef = null;
			List<OrgReldef> selectList = orgReldefManager.getByName(reldefName);
			if(BeanUtils.isNotEmpty(selectList)){
				reldef =  selectList.get(0);
			}else{
				Boolean flag = true;
				reldef = new OrgReldef();
				reldef.setId(UniqueIdUtil.getSuid());
				reldef.setCode(PinyinUtil.getPinYinHeadChar(reldefName));
				reldef.setName(reldefName);
				OrgReldef sysOrgJob = orgReldefManager.getByCode(reldef.getCode());
				if(BeanUtils.isNotEmpty(sysOrgJob)){
					reldef.setCode(reldef.getCode()+reldef.getId());
				}
				if(BeanUtils.isEmpty(orgJobList)){
					orgJobList.add(reldef);
				}else{
					for(int i=0;i<orgJobList.size();i++){
						if(orgJobList.get(i).getName().equals(reldefName)){
							flag = false;
							reldef = orgJobList.get(i);
						}
						if(i == (orgJobList.size()-1) && flag){
							//新增
							orgJobList.add(reldef);
						}
					}
				}
			}
			if(BeanUtils.isNotEmpty(reldef)){
				rtn.add(reldef);
			}
		}
		
		
		return rtn;
	}
	
	/**
	 * 处理用、组织、岗位关系
	 * @param user
	 * @param org
	 * @param orgPost
	 * @param orgUserList
	 * @param orgPostList
	 * @throws SQLException 
	 */
	public void dealUserOrgPost(User user,Org org,List<OrgRel> orgPosts,List<OrgUser> orgUserList,List<OrgRel> orgPostList,Map<String,String> orgPostAddMap,
			String isMaster,String isCharge,Map<String,String> isMasterMap,String demId) throws SQLException{
		if(BeanUtils.isEmpty(org)) return;
		if(BeanUtils.isNotEmpty(orgPosts)){
			for (OrgRel orgPost : orgPosts) {
				OrgUser orgUser = new OrgUser();
				if(StringUtil.isNotEmpty(orgPostAddMap.get(orgPost.getId()))){//在数据库中没有存在，考虑是否加入新增岗位列表中
					Boolean flag = true;
					if(BeanUtils.isEmpty(orgPostList)){//如果需要新增的岗位列表为空，则直接加入进去
						orgPost.setRelCode(org.getCode()+"_"+orgPost.getRelCode());
						orgPostList.add(orgPost);
					}else{
						//不为空则判断，需要增加的岗位。是否已经存在于需要增加的岗位列表里。以免重复新增
						for(int i=0;i<orgPostList.size();i++){
							if(orgPostList.get(i).getOrgId().equals(orgPost.getOrgId()) && orgPostList.get(i).getName().equals(orgPost.getName())){
								orgPost = orgPostList.get(i);
								flag = false;
							}
							//列表循环到最后一条数据，还不存在，则新增进去
							if(flag && i == (orgPostList.size()-1)){
								orgPost.setRelCode(org.getCode()+"_"+orgPost.getRelCode());
								orgPostList.add(orgPost);
							}
						}
					}
				}
				QueryFilter filter = new DefaultQueryFilter();
				filter.addFilter("org_Id_", orgPost.getOrgId(), QueryOP.EQUAL);
				filter.addFilter("user_Id_", user.getId(), QueryOP.EQUAL);
				filter.addFilter("rel_Id_", orgPost.getId(), QueryOP.EQUAL);
				//查询用户在该组织下，是否已存在该岗位。
				List<OrgUser> sysOrgUserList = orgUserManager.query(filter);
				if(BeanUtils.isEmpty(sysOrgUserList)){//不存在则添组织岗位信息
					orgUser.setId(UniqueIdUtil.getSuid());
					orgUser.setOrgId(orgPost.getOrgId());//组织id
					orgUser.setRelId(orgPost.getId());//岗位编号
					orgUser.setUserId(user.getId());
					dealMasterAndCharge(isMaster, isCharge, orgUser,isMasterMap,demId);
					orgUserList.add(orgUser);
				}
				//判断用户与组织是否已经存在关系（即还没挂上岗位的记录）
		/*		Map<String,Object> params = new HashMap<String,Object>();
				params.put("orgId", orgPost.getOrgId());
				params.put("userId", user.getId());
				params.put("relIdNull", "1");
				List<OrgUser> sysOrgUserList1 = orgUserService.getByParms(params);
				if(BeanUtils.isEmpty(sysOrgUserList) && BeanUtils.isEmpty(sysOrgUserList1)){//不存在则添加组织用户关系
					orgUser.setId(UniqueIdUtil.getSuid());
					orgUser.setOrgId(orgPost.getOrgId());//组织id
					orgUser.setRelId(orgPost.getId());//岗位编号
					orgUser.setUserId(user.getId());
					dealMasterAndCharge(isMaster, isCharge, orgUser,isMasterMap,demId);
					orgUserList.add(orgUser);
				}else if(BeanUtils.isNotEmpty(sysOrgUserList1)){//用户与组织已存在关系，只是还没关联具体的岗位，做更新操作
					orgUser = sysOrgUserList1.get(0);
					orgUser.setRelId(orgPost.getId());
					orgUserService.update(orgUser);
				}*/
			}
		}
		//处理用户组织关系
		OrgUser orgUser = new OrgUser();
		QueryFilter filter = new DefaultQueryFilter();
		filter.addFilter("user_Id_", user.getId(), QueryOP.EQUAL);
		filter.addFilter("org_Id_", org.getId(), QueryOP.EQUAL);
		//查询当前用户和当前组织关系在数据库中是否存在
		List<OrgUser> sysOrgUserList = orgUserManager.query(filter);
		boolean isExitOrg=false;
		 for (OrgUser orgUser2 : sysOrgUserList) {
			 //遍历结果集，如果只有存在不含岗位的记录，则表明该组织已有改用户
			if (BeanUtils.isEmpty(orgUser2.getRelId())) isExitOrg=true;
		 }
		if(!isExitOrg){//不存在则添加组织用户关系
			orgUser.setId(UniqueIdUtil.getSuid());
			orgUser.setOrgId(org.getId());//组织id
			orgUser.setUserId(user.getId());
			dealMasterAndCharge(isMaster, isCharge, orgUser,isMasterMap,demId);
			orgUserList.add(orgUser);
		}
	}
	
	/**
	 * 处理负责人和主组织
	 */
	private void dealMasterAndCharge(String isMaster,String isCharge,OrgUser orgUser,Map<String,String> isMasterMap,String demId){
		if(StringUtil.isNotEmpty(isMaster)&&(isMaster.equals("1")||isMaster.equals("是"))
				&&hasMaster(orgUser.getUserId(), orgUser.getOrgId(),demId)&&StringUtil.isEmpty(isMasterMap.get(orgUser.getUserId()+"_"+demId))){
			orgUser.setIsMaster(1);
			isMasterMap.put(orgUser.getUserId()+"_"+demId, "1");
		}else{
			orgUser.setIsMaster(0);
		}
		if(StringUtil.isNotEmpty(isCharge)){
			//1：负责人；2：主负责
			if(isCharge.equals("1")){
				orgUser.setIsCharge(1);
			}else if(isCharge.equals("2")){
				if(BeanUtils.isEmpty(orgUserDao.getChargesByOrgId(orgUser.getOrgId(), true))){
					orgUser.setIsCharge(2);
				}
			}
		}else{
			orgUser.setIsCharge(0);
		}
	}
	
	private boolean hasMaster(String userId,String orgId,String demId){
		List<OrgUser> orgUsers = orgUserDao.getByUserId(userId);
		if(BeanUtils.isNotEmpty(orgUsers)){
			for (OrgUser orgUser : orgUsers) {
				if(!orgUser.getOrgId().equals(orgId)&&orgUser.getIsMaster() == 1){
					Org org = orgManager.get(orgUser.getOrgId());
					if(BeanUtils.isNotEmpty(org)&&org.getDemId().equals(demId)){
						return false;
					}
				}
			}
		}
		return true;
	}
	
	
	// 给AD同步用户，和导入用户绑定一个默认角色
    private void bindRole(User user){
        if(BeanUtils.isNotEmpty(user)){
            String userId = user.getId();
            if (userRoleManager.getByRoleIdUserId("1", userId) != null) return;
            UserRole userRole = new UserRole();
            userRole.setId(UniqueIdUtil.getSuid());
            userRole.setUserId(userId);
            userRole.setRoleId("1");
            userRoleManager.create(userRole);
        }
    }
	
	/**
	 * 检测数据库中和待导入的数据中是否存在账号相同姓名不同的用户，如果存在，则不导入用户和用户的其他信息
	 * @param account
	 * @param name
	 * @param userMap
	 * @return
	 */
	private boolean checkSameUser(String account,String name,Map<String,String> userMap){
		String mapName = userMap.get(account);
		if(StringUtil.isNotEmpty(mapName)&&!mapName.equals(name)){
			return false;
		}
		User user = userDao.getByAccount(account);
		if(BeanUtils.isNotEmpty(user)&&!user.getFullname().equals(name)){
			return false;
		}
		return true;
	}
	
	/**
	 * 查找当前待导入用户中是否已有相同账号人员
	 * @param users
	 * @param account
	 * @return
	 */
	private User findUserFromNews(List<User> users,String account){
		for (User user : users) {
			if(user.getAccount().equals(account)){
				return user;
			}
		}
		return null;
	}
	
	private void recordLog(JSONArray logArray,String msg){
		logArray.add(logArray.size()+1+"、"+msg);
	}
	
	private boolean checkEmail(String email){
		try {
			String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		   Pattern regex = Pattern.compile(check);
		   Matcher matcher = regex.matcher(email);
		   return matcher.matches();
		} catch (Exception e) {}
		return false;
	}
	
	/**
	 * 添加需要新增的组织
	 * @param org
	 * @param orgList
	 * @return
	 */
	public Org dealOrg(Org org,List<Org> orgList,String preCode,Map<String,String> orgMap){
		Org rtn = null;
		if(BeanUtils.isEmpty(orgList)){
			if(BeanUtils.isNotEmpty(orgMap.get(org.getCode()))){
				org.setCode(org.getCode()+"_"+org.getId().substring(org.getId().length()-3));
			}
			orgList.add(org);
			orgMap.put(org.getCode(), org.getId());
			rtn = org;
		}else{
			Boolean flag = true; 
			for(int i=0;i<orgList.size();i++){
				if(orgList.get(i).getPathName().equals(org.getPathName()) ){
					flag = false;
					rtn = orgList.get(i);
				}
				if(orgList.get(i).getName().equals(org.getName()) &&  orgList.get(i) != org){//新增数据中，组织同名的情况
					org.setCode(preCode+"_"+PinyinUtil.getPinYinHeadChar(org.getName())+(i+1));
				}
				Org systemOrg = orgManager.getByCode(org.getCode());//判断是否与数据库中组织代码重复
				if(BeanUtils.isNotEmpty(systemOrg)){
					org.setCode(preCode+"_"+PinyinUtil.getPinYinHeadChar(org.getName())+(i+1)+(i+1));
				}
				if(i == (orgList.size()-1) && flag ){
					if(BeanUtils.isNotEmpty(orgMap.get(org.getCode()))){
						org.setCode(org.getCode()+"_"+org.getId().substring(org.getId().length()-3));
					}
					orgList.add(org);
					orgMap.put(org.getCode(), org.getId());
					rtn = org;
				}
			}
		}
		return rtn;
	}
	
	/**
	 * 处理组织的上下级关系
	 * @param supperPathName  父级路径名
	 * @param underPathName  子级路径名
	 * @param demId 对应维度id
	 * @param orgList
	 * @return
	 */
	public Org dealOrgUnder(String supperPathName,String underPathName,List<Org> orgList,String demId,String preCode,Map<String,String> orgMap){
		String underName = underPathName.substring(underPathName.lastIndexOf("/")+1, underPathName.length());
		List<Org> chirList = orgManager.getByPathName(underPathName);
		if(BeanUtils.isNotEmpty(chirList)){//子组织不为空
			for (Org org : chirList) {
				if(demId.equals(org.getDemId())){
					return org;
				}
			}
		}
		//子组织为空的情况
		List<Org> pList = orgManager.getByPathName(supperPathName);
		List<Org> parentList = new ArrayList<Org>();
		for (Org org : pList) {
			if(demId.equals(org.getDemId())){
				parentList.add(org);
			}
		}
		Org chird = new Org();
		if(BeanUtils.isEmpty(parentList)){//父组织为空，直接从orgList中寻找父级组织
			for(int i=0;i<orgList.size();i++){
				if(orgList.get(i).getPathName().equals(supperPathName)){
					chird.setId(UniqueIdUtil.getSuid());
					chird.setParentId(orgList.get(i).getId());
					chird.setDemId(demId);
					chird.setName(underName);
					chird.setPathName(underPathName);
					chird.setPath(orgList.get(i).getPath()+chird.getId()+".");
					chird.setCode(preCode+"_"+PinyinUtil.getPinYinHeadChar(underName));
					//chird.setOrgType("实体");
				}
			}
		}else{//父组织不为空，从数据库中拿父组织
			chird.setId(UniqueIdUtil.getSuid());
			chird.setParentId(parentList.get(0).getId());
			chird.setDemId(demId);
			chird.setName(underName);
			chird.setCode(preCode+"_"+PinyinUtil.getPinYinHeadChar(underName));
			chird.setPathName(underPathName);
			chird.setPath(parentList.get(0).getPath()+chird.getId()+".");
			//chird.setOrgType("实体");
		}
		
		return dealOrg(chird,orgList,preCode,orgMap);
	}
	
	/**
	 * 处理excel中的职务
	 * @param reldefName 职务名称
	 * @param orgReldefList 新增职务列表
	 * @return
	 */
	public List<OrgReldef> dealOrgReldef(String reldefNameStr,List<OrgReldef> orgReldefList,String preCode){
		if(StringUtil.isEmpty(reldefNameStr)) return null;
		String[] reldefNames = reldefNameStr.split(";");
		List<OrgReldef> rtn = new ArrayList<OrgReldef>();
		for (String reldefName : reldefNames) {
			OrgReldef reldef = null;
			List<OrgReldef> selectList = orgReldefManager.getByName(reldefName);
			if(BeanUtils.isNotEmpty(selectList)){
				reldef =  selectList.get(0);
			}else{
				Boolean flag = true;
				reldef = new OrgReldef();
				reldef.setId(UniqueIdUtil.getSuid());
				reldef.setCode(PinyinUtil.getPinYinHeadChar(reldefName));
				reldef.setName(reldefName);
				OrgReldef sysOrgReldef = orgReldefManager.getByCode(reldef.getCode());
				if(BeanUtils.isNotEmpty(sysOrgReldef)){
					reldef.setCode(reldef.getCode()+reldef.getId());
				}
				if(BeanUtils.isEmpty(orgReldefList)){
					orgReldefList.add(reldef);
				}else{
					for(int i=0;i<orgReldefList.size();i++){
						if(orgReldefList.get(i).getName().equals(reldefName)){
							flag = false;
							reldef = orgReldefList.get(i);
						}
						if(i == (orgReldefList.size()-1) && flag){
							//新增
							orgReldefList.add(reldef);
						}
					}
				}
			}
			if(BeanUtils.isNotEmpty(reldef)){
				rtn.add(reldef);
			}
		}
		
		
		return rtn;
	}
	
	/**
	 * 处理新增用户列表
	 * @param user
	 * @param userList
	 * @return
	 */
	public User dealUser(User user,List<User> userList) throws Exception{
		User rtn = new User();
		if(StringUtil.isNotEmpty(user.getId())){
			rtn = user;
		}else{
			Boolean flag = true;
			if(BeanUtils.isEmpty(userList)){
				user.setId(UniqueIdUtil.getSuid());
				rtn = user;
				userList.add(user);
			}else{
				for(int i=0;i<userList.size();i++){
					if(userList.get(i).getAccount().equals(user.getAccount()) && userList.get(i) != user){
						logger.error("Excel表格中用户帐号重复："+userList.get(i).getAccount());
					}
					if(userList.get(i).getAccount().equals(user.getAccount()) && userList.get(i) == user){
						flag = false;
						rtn = userList.get(i);
					}
					if(i == (userList.size()-1) && flag){
						user.setId(UniqueIdUtil.getSuid());
						rtn = user;
						userList.add(user);
					}
				}
			}
		}
		return rtn;
	}
	
	/**
	 * 处理用、组织、岗位关系
	 * @param user
	 * @param org
	 * @param orgRel
	 * @param orgUserList
	 * @param orgRelList
	 */
	public void dealUserOrgRel(User user,Org org,List<OrgRel> orgRels,List<OrgUser> orgUserList,List<OrgRel> orgRelList,Map<String,String> orgRelAddMap,
			String isMaster,String isCharge,Map<String,String> isMasterMap){
		if(BeanUtils.isEmpty(org)) return;
		if(BeanUtils.isNotEmpty(orgRels)){
			for (OrgRel orgRel : orgRels) {
				OrgUser orgUser = new OrgUser();
				if(StringUtil.isNotEmpty(orgRelAddMap.get(orgRel.getId()))){//在数据库中没有存在，考虑是否加入新增岗位列表中
					Boolean flag = true;
					if(BeanUtils.isEmpty(orgRelList)){//新增岗位列表
						orgRel.setRelCode(org.getCode()+"_"+orgRel.getRelCode());
						orgRelList.add(orgRel);
					}else{
						for(int i=0;i<orgRelList.size();i++){
							if(orgRelList.get(i).getOrgId().equals(orgRel.getOrgId()) && orgRelList.get(i).getName().equals(orgRel.getName())){
								orgRel = orgRelList.get(i);
								flag = false;
							}
							if(flag && i == (orgRelList.size()-1)){
								orgRel.setRelCode(org.getCode()+"_"+orgRel.getRelCode());
								orgRelList.add(orgRel);
							}
						}
					}
				}
				DefaultQueryFilter filter = new DefaultQueryFilter();
				filter.addFilter("org_id_", orgRel.getOrgId(), QueryOP.EQUAL);
				filter.addFilter("user_id_", user.getId(), QueryOP.EQUAL);
				filter.addFilter("rel_id_", orgRel.getId(), QueryOP.EQUAL);
				List<OrgUser> sysOrgUserList = orgUserDao.query(filter);
				if(BeanUtils.isEmpty(sysOrgUserList)){//不存在则添加组织用户关系
					orgUser.setId(UniqueIdUtil.getSuid());
					orgUser.setOrgId(orgRel.getOrgId());//组织id
					orgUser.setRelId(orgRel.getId());//岗位编号
					orgUser.setUserId(user.getId());
					dealMasterAndCharge(isMaster, isCharge, orgUser,isMasterMap);
					orgUserList.add(orgUser);
				}
			}
		}else{
			OrgUser orgUser = new OrgUser();
			DefaultQueryFilter filter = new DefaultQueryFilter();
			filter.addFilter("user_id_", user.getId(), QueryOP.EQUAL);
			List<OrgUser> sysOrgUserList = orgUserDao.query(filter);
			if(BeanUtils.isEmpty(sysOrgUserList)){//不存在则添加组织用户关系
				orgUser.setId(UniqueIdUtil.getSuid());
				orgUser.setOrgId(org.getId());//组织id
				orgUser.setUserId(user.getId());
				dealMasterAndCharge(isMaster, isCharge, orgUser,isMasterMap);
				orgUserList.add(orgUser);
			}
		}
	}
	
	/**
	 * 处理负责人和主组织
	 */
	private void dealMasterAndCharge(String isMaster,String isCharge,OrgUser orgUser,Map<String,String> isMasterMap){
		if(StringUtil.isNotEmpty(isMaster)&&(isMaster.equals("1")||isMaster.equals("是"))
				&&hasMaster(orgUser.getUserId(), orgUser.getOrgId())&&StringUtil.isEmpty(isMasterMap.get(orgUser.getUserId()))){
			orgUser.setIsMaster(1);
			isMasterMap.put(orgUser.getUserId(), "1");
		}else{
			orgUser.setIsMaster(0);
		}
		if(StringUtil.isNotEmpty(isCharge)){
			//1：负责人；2：主负责
			if(isCharge.equals("1")){
				orgUser.setIsCharge(1);
			}else if(isCharge.equals("2")){
				if(BeanUtils.isEmpty(orgUserDao.getChargesByOrgId(orgUser.getOrgId(), true))){
					orgUser.setIsCharge(2);
				}
			}
		}else{
			orgUser.setIsCharge(0);
		}
	}
	
	private boolean hasMaster(String userId,String orgId){
		List<OrgUser> orgUsers = orgUserDao.getByUserId(userId);
		if(BeanUtils.isNotEmpty(orgUsers)){
			for (OrgUser orgUser : orgUsers) {
				if(!orgUser.getOrgId().equals(orgId)&&orgUser.getIsMaster() == 1){
					return false;
				}
			}
		}
		return true;
	}
	
	private OrgRel getByRelNameAndCode(String name,String code){
		QueryFilter queryFilter = new DefaultQueryFilter();
		queryFilter.addFilter("REL_NAME_", name, QueryOP.EQUAL);
		List<OrgRel> orgRels = orgRelManager.query(queryFilter);
		if(BeanUtils.isNotEmpty(orgRels)){
			for (OrgRel orgRel : orgRels) {
				if(orgRel.getRelCode().startsWith(code)){
					return orgRel;
				}
			}
		}
		return null;
	}
	
	@Override
	public List<User> getUpUsersByUserId(String underUserId) {
		return userDao.getUpUsersByUserId(underUserId);
	}
	@Override
	public User getUpUserByUserIdAndOrgId(String account,String orgCode) {
		User u = this.getByAccount(account);
		Org o = orgManager.getByCode(orgCode);
		Map<String,String> map = new HashMap<String,String>();
		map.put("underUserId", u.getId());
		map.put("orgId", o.getId());
		return userDao.getUpUserByUserIdAndOrgId(map);
	}
	@Override
	public List<User> getUnderUsersByUserId(String upUserId) {
		return userDao.getUnderUsersByUserId(upUserId);
	}
	@Override
	public List<User> getUnderUserByUserIdAndOrgId(String account,String orgCode) {
		User u = this.getByAccount(account);
		Org o = orgManager.getByCode(orgCode);
		Map<String,String> map = new HashMap<String,String>();
		map.put("upUserId", u.getId());
		map.put("orgId", o.getId());
		return userDao.getUnderUserByUserIdAndOrgId(map);
	}
	
	@Override
	public List<User> getListByJobId(String jobId) {
		List<User> list = new ArrayList<User>();
		List<OrgRel> orgRel = orgRelDao.getByReldefId(jobId);
		if(BeanUtils.isEmpty(orgRel)) return list;
		for (OrgRel rel : orgRel) {
			List<User> positionUser = getListByRelId(rel.getId());
			if(BeanUtils.isNotEmpty(positionUser)){
				list.addAll(positionUser);
			}
		}
		BeanUtils.removeDuplicate(list);
		return list;
	}
	@Override
	public User getLoginUser(String account) {
		return userDao.getLoginUser(account);
	}
	
	/**
	 * @Description:  记录日志
	 * @param: @param logVO
	 * @param: @param pkColumnVO    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年6月5日
	 */
	@Override
	public void logOpeTableData(TableOpeLogVO logVO, TableColumnVO pkColumnVO) {
		
	}
}
